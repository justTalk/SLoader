package com.scam.sloader.out;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.scam.sloader.api.EmptyServiceImpl;
import com.scam.sloader.api.IService;
import com.scam.sloader.utils.ClassUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import dalvik.system.DexFile;

/**
 * Date: 2019-09-26
 * Version: 1.0
 * Author: Andy
 * Description:
 */
public class SLoader {

    private final Object mLock = new Object();

    static HashMap<String, String> serviceNameMap;
    static HashMap<String, IService> serviceMap = new HashMap<>();

    public static IService findServiceImplByName(Context context, String name){
        IService service = serviceMap.get(name);
        if (service == null) {
            service = createService(context, name);
            if (service == null) {
                service = new EmptyServiceImpl();
            }
            service.onCreate(context);
            serviceMap.put(name, service);
        }
        return service;
    }

    private static IService createService(Context context, String name){
        String serviceName = serviceNameMap.get(name);
        if (TextUtils.isEmpty(serviceName)) {
            serviceName = EmptyServiceImpl.class.getCanonicalName();
        }
        IService service = null;
        try {
            Object o = ClassUtils.newInstance(serviceName);
            if (o instanceof IService) {
                service = (IService) o;
            }else {
                throw new ClassCastException("must implements IService interface current Class is " + o.getClass());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return service;
    }

    /**
    * @description:初始化SLoader，主要从apt生成的文件中读取注册的各个Service的实现类全名
    * @author:mingming.liu
    * @Date:2019-09-27 16:54
    * @warn:
    */
    public static void init(){
        String className = "com.scam.sloader.config.ConfigCenter";
        try {
            Class clazz = Class.forName(className);
            Constructor constructor = clazz.getConstructor();
            Object o = constructor.newInstance();
            Method method = clazz.getMethod("loadServiceName");
            method.setAccessible(true);
            serviceNameMap = (HashMap<String, String>) method.invoke(o);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void findAllConfigFile(Context context){
        try {
            Set<String> fileNames =  ClassUtils.getFileNameByPackageName(context, "com.scam.sloader.config");
            Log.d("SLoader", fileNames.toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
