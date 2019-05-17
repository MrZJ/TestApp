package test.com.commenlib.eventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by zhangjian on 2019/5/17 11:51
 */
public class EventBus {
    private static EventBus eventBus;
    private Map<Object, List<MethodMap>> cacheMap;
    private Handler handler;
    ThreadPoolExecutor defaultExecutor;

    public static EventBus getInstance() {
        if (eventBus == null) {
            synchronized (EventBus.class) {
                eventBus = new EventBus();
            }
        }
        return eventBus;
    }

    private EventBus() {
        cacheMap = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        defaultExecutor =
                new ThreadPoolExecutor(3, 3,
                        600, MILLISECONDS,
                        new ArrayBlockingQueue<Runnable>(10));
    }

    public void regist(Object o) {
        //获取o中所有被标记的方法存在缓存中
        addInfoToMap(o);
    }

    private void addInfoToMap(final Object o) {
        defaultExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Class clazz = o.getClass();
                while (clazz != null) {
                    Method[] declaredMethods = clazz.getDeclaredMethods();
                    //讲类中标记的方法放入map中保存
                    List<MethodMap> list = chekAnotation(declaredMethods);
                    if (list.size() > 0) {
                        cacheMap.put(o, list);
                    }
                    clazz = clazz.getSuperclass();
                }
            }
        });
    }

    private List<MethodMap> chekAnotation(Method[] declaredMethods) {
        List<MethodMap> list = new ArrayList<>();
        for (Method method : declaredMethods) {
            Receive annotation = method.getAnnotation(Receive.class);
            if (annotation != null) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                ThreadMode threadMode = annotation.getThreadMode();
                if (parameterTypes.length == 1) {
                    list.add(new MethodMap(method, parameterTypes[0], threadMode));
                }
            }
        }
        return list;
    }

    /**
     * 发送事件
     */
    public void postEvent(Object o) {
        solveEvent(o);
    }

    private void solveEvent(Object o) {
        Set<Object> objects = cacheMap.keySet();
        Iterator<Object> iterator = objects.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            List<MethodMap> list = cacheMap.get(next);
            try {
                checkPostEvent(o, list, next);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkPostEvent(Object o, List<MethodMap> list, Object next) throws InvocationTargetException, IllegalAccessException {
        for (MethodMap methodMap : list) {
            Class<?> aClass1 = o.getClass();
            if (methodMap.params == aClass1) {
                invokeMethod(next, methodMap.method, o, methodMap.threadMode);
            }
        }
    }

    /**
     * 指定方法调用的线程
     *
     * @param owner
     * @param method
     * @param event
     * @param threadMode
     */
    private void invokeMethod(final Object owner, final Method method, final Object event, ThreadMode threadMode) {
        switch (threadMode) {
            case THREAD_MAIN:
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            method.invoke(owner, event);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case THREAD_BACK:
                defaultExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            method.invoke(owner, event);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }


    /**
     * 一定要调用不然会内存泄漏
     *
     * @param o
     */
    public void unRegist(Object o) {
        //移除o中所有被标记的方法
        cacheMap.remove(o);
    }
}
