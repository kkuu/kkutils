package utils.kkutils.ui.textview;

import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;

import utils.kkutils.AppTool;
import utils.kkutils.R;
import utils.kkutils.common.LogTool;

public class KKTextToSpeech {

    public static void textToSpeech(String text){
        new KKTextToSpeech(text);
    }

    /***
     * 先播放金币声音
     * @param text
     */
    public static void textToSpeech_after_jinbi(String text){
        MediaPlayer mediaPlayer = MediaPlayer.create(AppTool.getApplication(), R.raw.jinbi_zhifubao);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                new KKTextToSpeech(text);
            }
        });
        mediaPlayer.start();

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