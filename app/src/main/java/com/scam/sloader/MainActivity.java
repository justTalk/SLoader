package com.scam.sloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scam.sloader.out.SLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SLoader.init();
        SLoader.findServiceImplByName(this, "TestService");
        SLoader.findServiceImplByName(this, "OtherService");
    }
}
