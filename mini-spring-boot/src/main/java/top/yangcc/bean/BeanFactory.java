package top.yangcc.bean;

import top.yangcc.web.anno.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bean工厂
 */
@SuppressWarnings("unused")
public class BeanFactory {

    /**
     * 存放 bean
     */
    private static Map<String, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 存储，已经完成实例化，但是还未进行属性注入及初始化的对象
     */
    private static Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();



    public static Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    public static void registerBean(String name, Object bean) {
        beanMap.put(name, bean);
    }



    public static void init(List<Class<?>> classList) throws Exception {
        //筛选Bean
        filterClassAndEarlyInstance(classList);

        //TODO 解决属性注入，以及循环依赖问题
        beanMap = earlySingletonObjects;

    }

    /**
     * 筛选Bean,并且初次实例化
     * @param classList 加载到的所有的class
     */
    public static void filterClassAndEarlyInstance(List<Class<?>> classList) throws Exception{
        for (Class<?> aClass : classList) {
            Component annotation = aClass.getAnnotation(Component.class);
            //TODO 未来加入AOP注解
            if (annotation==null){
                continue;
            }
            String name = aClass.getName();
            Object instance = aClass.getDeclaredConstructor().newInstance();
            earlySingletonObjects.put(name,instance);
        }
    }

}
