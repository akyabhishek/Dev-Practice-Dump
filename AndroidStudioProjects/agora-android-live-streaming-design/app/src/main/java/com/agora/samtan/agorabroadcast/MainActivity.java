package com.agora.samtan.agorabroadcast;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.agora.rtc.Constants;

public class MainActivity extends AppCompatActivity{
    static String channelMessage;
    static String profileMessage;
    private int channelProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int MY_PERMISSIONS_REQUEST_CAMERA = 0;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_CAMERA);

        }
    }
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.host:
                if (checked) {
                    channelProfile = Constants.CLIENT_ROLE_BROADCASTER;
                }
                break;
            case R.id.audience:
                if (checked) {
                    channelProfile = Constants.CLIENT_ROLE_AUDIENCE;
                }
                break;
        }
    }
    public void onSubmit(View view) {
        EditText channel = (EditText) findViewById(R.id.channel);
        String channelName = channel.getText().toString();
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra(channelMessage, channelName);
        intent.putExtra(profileMessage, channelProfile);
        startActivity(intent);
    }


}
