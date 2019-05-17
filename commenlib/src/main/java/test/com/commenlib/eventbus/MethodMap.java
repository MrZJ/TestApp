package test.com.commenlib.eventbus;

import java.lang.reflect.Method;

/**
 * Created by zhangjian on 2019/5/17 13:59
 */
class MethodMap {
    public Method method;
    public Class params;
    public ThreadMode threadMode;

    public MethodMap(Method method, Class params, ThreadMode threadMode) {
        this.method = method;
        this.params = params;
        this.threadMode = threadMode;
    }

}
