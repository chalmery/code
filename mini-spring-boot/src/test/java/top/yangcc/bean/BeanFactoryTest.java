package top.yangcc.bean;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BeanFactoryTest {


    @Test
    public void filterClass() throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        ClassScanner.doScanPackageClasses(classes,
                "top.yangcc",
                "/home/ycc/code/java/code/mini-spring-boot/target/classes/top/yangcc");
        BeanFactory.filterClassAndEarlyInstance(classes);
    }


}