package top.yangcc.web.anno;

import java.lang.annotation.*;

/**
 * 描述一个Bean的注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited//注解可继承
public @interface Component {
}
