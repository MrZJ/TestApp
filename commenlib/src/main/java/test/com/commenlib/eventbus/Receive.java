package test.com.commenlib.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhangjian on 2019/5/17 14:03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Receive {
    ThreadMode getThreadMode() default ThreadMode.THREAD_MAIN;
}
