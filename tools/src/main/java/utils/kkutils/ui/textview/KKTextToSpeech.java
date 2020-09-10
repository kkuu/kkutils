package utils.kkutils.ui.textview;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import utils.kkutils.AppTool;
import utils.kkutils.common.LogTool;

public class KKTextToSpeech {

    public static void textToSpeech(String text){
        new KKTextToSpeech(text);
    }

    TextToSpeech mTTS;
    public KKTextToSpeech(String text){
         mTTS = new TextToSpeech(AppTool.getApplication(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                speed(text);
            }
        });

    }
    public void speed(String text){
        try {
            mTTS.setSpeechRate(1);
            mTTS.setPitch(1);
            mTTS.speak(text, TextToSpeech.QUEUE_FLUSH,null);
        }catch (Exception e){
            LogTool.ex(e);
        }

    }



}