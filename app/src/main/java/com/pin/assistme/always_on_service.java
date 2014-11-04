package com.pin.assistme;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

/**
 * Created by tomer on 26/05/14.
 */
public class always_on_service extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        always_on_thread alwaysOnThread = new always_on_thread();
        alwaysOnThread.run();
        Toast.makeText(getApplicationContext(),"service is running",Toast.LENGTH_SHORT).show();
    }

    class always_on_thread extends Thread {
        SpeechRecognizer sr;
        public String result;

        @Override
        public void run() {
            super.run();
            start_voice_recog();
        }

        public void start_voice_recog() {
            sr = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
            voice_recognition listener = new voice_recognition();
            sr.setRecognitionListener(listener);
            sr.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
        }


        class voice_recognition implements RecognitionListener {
            String result;

            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {
                System.out.println("rms change");
                if(rmsdB>5){
                    System.out.println("rms>5");
                }
            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                start_voice_recog();
            }

            @Override
            public void onError(int error) {
                System.out.println("error");
                 start_voice_recog();
            }

            @Override
            public void onResults(Bundle results) {
                System.out.println("result");

                result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                if (result.toLowerCase().contains("hello")) {
                    Intent start = new Intent(getApplicationContext(),Main1.class);
                    start.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(start);
                }
                else{
                    start_voice_recog();
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        }
    }
    @Override
    public void onDestroy() {
        Intent in = new Intent();
        in.setAction("YouWillNeverKillMe");
        sendBroadcast(in);
    }
}

