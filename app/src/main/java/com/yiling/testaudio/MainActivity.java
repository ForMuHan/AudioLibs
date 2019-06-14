package com.yiling.testaudio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.syw.audio.widget.AudioRecordButton;

public class MainActivity extends AppCompatActivity {
    AudioRecordButton arb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arb = findViewById(R.id.arb);
        arb.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
            @Override
            public void onFinished(float seconds, String filePath) {
                Log.e("syw","filePath:"+filePath);
            }

            @Override
            public void onNormal() {
                Log.e("syw","onNormal:");
            }

            @Override
            public void onRecord() {
                Log.e("syw","onRecord:");
            }

            @Override
            public void onCancel() {
                Log.e("syw","onCancel:");
            }

            @Override
            public void onShort() {
                Log.e("syw","onShort:");
            }
        });
    }
}
