package com.testings_shit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wolfram.alpha.WAEngine;
import com.wolfram.alpha.WAException;
import com.wolfram.alpha.WAPlainText;
import com.wolfram.alpha.WAPod;
import com.wolfram.alpha.WAQuery;
import com.wolfram.alpha.WAQueryResult;
import com.wolfram.alpha.WASubpod;

/**
 * Created by tomer on 06/08/14.
 */
public class wolfram_test extends Activity {
    private static String appid = "6J9QXJ-GJ7U63J229";
    WAQueryResult queryResult;
    String WolframInput;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wolfram("who is obama");
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class WolframAlphaTask extends AsyncTask<WAQueryResult, Void, WAQueryResult> {
        ProgressDialog pd;

        protected void onPreExecute() {
            if (isNetworkAvailable()) {
                pd = new ProgressDialog(wolfram_test.this);
                pd.setTitle("Thinking");
                pd.show();
            } else {
                Log.d("NO CONNECTION","NO INTERNET CONNECTION");
                Toast.makeText(getApplicationContext(), "NO NETWORK CONNECTION ERROR", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected WAQueryResult doInBackground(WAQueryResult... urls) {
            if (isNetworkAvailable()) {
                // The WAEngine is a factory for creating WAQuery objects,
                // and it also used to perform those queries. You can set properties of
                // the WAEngine (such as the desired API output format types) that will
                // be inherited by all WAQuery objects created from it. Most applications
                // will only need to crete one WAEngine object, which is used throughout
                // the life of the application.
                WAEngine engine = new WAEngine();

                // These properties will be set in all the WAQuery objects created from this WAEngine.
                engine.setAppID(appid);
                engine.addFormat("plaintext");

                // Create the query.
                WAQuery query = engine.createQuery();

                // Set properties of the query.
                query.setInput(WolframInput);
                queryResult = null;
                try {
                    queryResult = engine.performQuery(query);
                } catch (WAException e) {
                    e.printStackTrace();
                }
                return queryResult;
            }
            return null;
        }

        @Override
        protected void onPostExecute(WAQueryResult response) {
            if (isNetworkAvailable()) {
                if (queryResult.isError()) {
                    System.out.println("Query error");
                    System.out.println("  error code: " + queryResult.getErrorCode());
                    System.out.println("  error message: " + queryResult.getErrorMessage());

                } else if (!queryResult.isSuccess()) {
                    System.out.println("Query was not understood; no results available.");

                } else {
                    String toAnswer = null;
                    String tempText = "";
                    // Got a result.
                    System.out.println("Successful query. Pods follow:\n");
                    for (final WAPod pod : queryResult.getPods()) {
                        if (!pod.isError()) {
                            if (pod.getTitle().equals("Result")) {
                                System.out.println(pod.getTitle());
                                for (WASubpod subpod : pod.getSubpods()) {
                                    for (Object element : subpod.getContents()) {
                                        if (element instanceof WAPlainText) {
                                            System.out.println(((WAPlainText) element).getText());
                                            try {
                                                if (!((WAPlainText) element).getText().equals("(data not available)") && !toAnswer.contains(((WAPlainText) element).getText())) {
                                                    toAnswer = ((WAPlainText) element).getText();
                                                    Log.d("ANSWER", toAnswer);
                                                } else {
                                                    Log.d("ANSWER", "I don't know this one.. Sorry i disappointed you");
                                                }
                                            } catch (NullPointerException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (toAnswer == (null)) {
                        for (final WAPod podd : queryResult.getPods()) {
                            if (!podd.isError()) {
                                if (podd.getTitle().equals("Basic information")) {
                                    System.out.println(podd.getTitle());
                                    for (WASubpod subpod : podd.getSubpods()) {
                                        for (Object element : subpod.getContents()) {
                                            if (element instanceof WAPlainText) {
                                                System.out.println(((WAPlainText) element).getText());
                                                if (!((WAPlainText) element).getText().equals("(data not available)")) {
                                                    tempText = tempText + ((WAPlainText) element).getText();
                                                } else {
                                                    if (tempText == null) {

                                                        Log.d("ANSWER", "I don't know this one.. Sorry i disappointed you");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Log.d("ANSWER", tempText);
                    } else {

                    }
                }
                pd.dismiss();
            }
        }
    }
    void wolfram(String input) {
        WolframInput = input;
        try {
            new WolframAlphaTask().execute();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
