package com.scam.sloader.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2019-09-26
 * Version: 1.0
 * Author: Andy
 * Description:
 */
public class ConfigCenter {

    public static final String SLOADER_SERIVICE_NAME = "ConfigCenter";
    public static final String SLOADER_SERIVICE_PACKAGE = "com.scam.sloader.config";

    public static Map<String, String> loadServiceName(){
        Map<String, String> map = new HashMap<>();
        map.put("", "");

        return map;
    }



}
