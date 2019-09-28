package com.scam.sloader;

import android.content.Context;
import android.util.Log;

import com.scam.annotation.ExportService;
import com.scam.sloader.api.IService;

/**
 * Date: 2019-09-26
 * Version: 1.0
 * Author: Andy
 * Description:
 */
@ExportService("TestService")
public class TestServiceLoader implements IService {

    @Override
    public void onCreate(Context context) {
        Log.d("TestService", "init");
    }

    @Override
    public void onDestory(Context context) {
        Log.d("TestService", "onDestory");
    }
}
