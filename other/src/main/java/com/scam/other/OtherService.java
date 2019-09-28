package com.scam.other;

import android.content.Context;
import android.util.Log;

import com.scam.annotation.ExportService;
import com.scam.sloader.api.IService;

/**
 * Date: 2019-09-27
 * Version: 1.0
 * Author: Andy
 * Description:
 */
@ExportService(value = "OtherService")
public class OtherService implements IService {
    @Override
    public void onCreate(Context context) {
        Log.d("TestService", "OtherService init ");
    }

    @Override
    public void onDestory(Context context) {

    }
}
