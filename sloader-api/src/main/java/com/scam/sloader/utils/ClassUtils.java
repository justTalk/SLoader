package com.scam.sloader.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Date: 2019-09-27
 * Version: 1.0
 * Author: Andy
 * Description:
 */
public class ClassUtils {

    public static Object newInstance(String className)
            throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Class.forName(className);
        Constructor constructor = clazz.getConstructor();
        return constructor.newInstance();
    }
}
