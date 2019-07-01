package test.com.commenlib.anotation;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zhangjian on 2019/5/17 10:47
 * des:just used in activity
 * 仿照市面上的注解框架写的一个例子
 */
public class InjectManager {
    public static final String TAG = "InjectManager";

    public static void inject(Object o) {
        //注入layout
        injectLayout(o);
        //注入View
        injectView(o);
    }

    //注入layout
    private static void injectLayout(Object o) {
        Class clazz = o.getClass();
        try {
            Method setContentView = clazz.getMethod("setContentView", int.class);
            ContentView annotation = (ContentView) clazz.getAnnotation(ContentView.class);
            if (annotation != null) {
                int layoutId = annotation.getLayoutId();
                setContentView.invoke(o, layoutId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void injectView(Object o) {
        Class clazz = o.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        try {
            Method method = null;
            method = clazz.getMethod("findViewById", int.class);
            for (Field field : declaredFields) {
                actvityInjectVie(field, o, method);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void actvityInjectVie(Field field, Object o, Method method) throws InvocationTargetException, IllegalAccessException {
        InjectView annotation = field.getAnnotation(InjectView.class);
        if (annotation != null) {
            int viewId = annotation.getViewId();
            Class clazz = field.getDeclaringClass();
            field.setAccessible(true);
            Log.e(TAG, clazz.getName() + "," + clazz.getSimpleName() + "," + clazz.getCanonicalName());
            Log.e(TAG, field.toString());
            field.set(o, method.invoke(o, viewId));
        }
    }
}
