package top.yangcc.web.anno;

import java.lang.annotation.*;

/**
 * 描述一个Bean的注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
