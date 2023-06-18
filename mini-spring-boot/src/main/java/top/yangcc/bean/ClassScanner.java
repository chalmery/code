package top.yangcc.bean;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 包扫描
 */
public class ClassScanner {


    /**
     * 加载指定包名的类
     */
    public static List<Class<?>> scannerCLasses(String packageName) throws IOException, ClassNotFoundException {
        String path = packageName.replace(".", "/");
        // 线程上下文类加载器默认是应用类加载器，即 ClassLoader.getSystemClassLoader();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        //返回一个 Enumeration<URL> 。这个类是老版本的迭代器，因此可以转为list处理
        ArrayList<URL> list = Collections.list(classLoader.getResources(path));

        List<Class<?>> classList = new ArrayList<>();
        for (URL url : list) {
            doScanPackageClasses(classList,packageName,url.getPath());
        }
        return classList;
    }

    /**
     * 递归读取类文件
     * @param classes 类集合
     * @param packageName 类路径
     * @param packagePath 路径
     */
    public static void doScanPackageClasses(List<Class<?>> classes,String packageName,String packagePath) throws ClassNotFoundException {
        File file  = new File(packagePath);
        if (!file.exists() || !file.isDirectory()){
            return;
        }
        //文件过滤,筛选出目录或者文件夹
        File[] files = file.listFiles((dir, name) -> {
            if (!dir.exists()){
                return false;
            }
            if (dir.isDirectory()) {
                return true;
            }
            return StringUtils.isNoneBlank(name) && name.endsWith(".class");
        });
        if (files == null){
            return;
        }
        for (File f : files) {
            if (f.isDirectory()){
                doScanPackageClasses(classes,packageName + "."+f.getName(),f.getAbsolutePath());
            }else {
                // 用当前类加载器加载 去除 fileName 的 .class 6 位
                String className = f.getName().substring(0, f.getName().length() - 6);
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                Class<?> aClass = classLoader.loadClass(packageName + "."+className);
                classes.add(aClass);
            }
        }
    }



}