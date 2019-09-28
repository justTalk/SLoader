package com.scam.core.api;

/**
 * Date: 2019-09-26
 * Version: 1.0
 * Author: Andy
 * Description: service加载器
 */
public interface ServiceLoader{

    /**
    * @description:创建时调用
    * @author:mingming.liu
    * @Date:2019-09-26 19:53
    * @warn:
    */
    void onCreate();

    /**
    * @description:加载某个接口的实现者
    * @author:mingming.liu
    * @Date:2019-09-26 19:47
    * @warn:
    */
    <T>T load(Class<T> c);

    /**
    * @description:卸载某个接口的实现者
    * @author:mingming.liu
    * @Date:2019-09-26 19:52
    * @warn:
    */
    void unload(Class c);

    /**
    * @description:销毁加载器，需要清除该加载器中已创建的服务实例
    * @author:mingming.liu
    * @Date:2019-09-26 19:54
    * @warn:
    */
    void onDestory();
}
