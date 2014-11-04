package com.pin.assistme;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

/**
 * Created by tomer on 23/05/14.
 */
public class SimpleVoiceService extends RecognitionService {

    private SpeechRecognizer m_EngineSR;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("SimpleVoiceService", "Service started");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SimpleVoiceService", "Service stopped");
    }

    @Override
    protected void onCancel(Callback listener) {
        m_EngineSR.cancel();
    }

    @Override
    protected void onStartListening(Intent recognizerIntent, Callback listener) {
        m_EngineSR.setRecognitionListener(new VoiceResultsListener(listener));
        m_EngineSR.startListening(recognizerIntent);
    }

    @Override
    protected void onStopListening(Callback listener) {
        m_EngineSR.stopListening();
    }


    /**
     *
     */
    private class VoiceResultsListener implements RecognitionListener {

        private Callback m_UserSpecifiedListener;

        /**
         *
         * @param userSpecifiedListener
         */
        public VoiceResultsListener(Callback userSpecifiedListener) {
            m_UserSpecifiedListener = userSpecifiedListener;
        }

        @Override
        public void onBeginningOfSpeech() {
            try {
                m_UserSpecifiedListener.beginningOfSpeech();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            try {
                m_UserSpecifiedListener.bufferReceived(buffer);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onEndOfSpeech() {
            try {
                m_UserSpecifiedListener.endOfSpeech();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(int error) {
            try {
                m_UserSpecifiedListener.error(error);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onEvent(int eventType, Bundle params) { ; }

        @Override
        public void onPartialResults(Bundle partialResults) {
            try {
                m_UserSpecifiedListener.partialResults(partialResults);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            try {
                m_UserSpecifiedListener.readyForSpeech(params);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onResults(Bundle results) {
            try {
                m_UserSpecifiedListener.results(results);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            try {
                m_UserSpecifiedListener.rmsChanged(rmsdB);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
