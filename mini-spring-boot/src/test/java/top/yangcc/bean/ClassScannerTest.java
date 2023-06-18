package top.yangcc.bean;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ClassScannerTest {


    @Test
    public void doScanPackageClasses() throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        ClassScanner.doScanPackageClasses(classes,
                "top.yangcc",
                "/home/ycc/code/java/code/mini-spring-boot/target/classes/top/yangcc");
        assert classes.size()>0;
    }
}