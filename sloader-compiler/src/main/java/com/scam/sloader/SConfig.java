package com.scam.sloader;

import java.util.HashMap;

/**
 * Date: 2019-09-27
 * Version: 1.0
 * Author: Andy
 * Description:
 */
public class SConfig {

    public static final String SLOADER_SERIVICE_NAME = "ConfigCenter";
    public static final String SLOADER_SERIVICE_PACKAGE = "com.scam.sloader.config";
    public static final String SLOADER_SERIVICE_METHOD = "loadServiceName";
    public static final String SLOADER_MODULE_KEY = "SLOADER_MODULE_NAME";

    private SConfig(){

    }


    public static SConfig getInstance(){
        return Holder.sConfig;
    }

    private static class Holder{
        private static SConfig sConfig = new SConfig();
    }


}
