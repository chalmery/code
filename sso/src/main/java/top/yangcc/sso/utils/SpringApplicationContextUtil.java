package top.yangcc.sso.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class SpringApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    public ApplicationContext getApplicationContext() {
        return SpringApplicationContextUtil.applicationContext;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationContextUtil.applicationContext = applicationContext;
    }
    /**
     * 通过工厂类获取对应的服务
     * @return 服务类
     */
    public static Object getBean(String name){
        Object object=null;
        try{
            object = SpringApplicationContextUtil.applicationContext.getBean(name);
        }catch(Exception e){
            e.printStackTrace();
        }
        return object;
    }
    /**
     * 通过工厂类获取对应的服务
     * @param clazz 对应的class
     * @return 服务类
     */
    public static <T> T getBean(Class<T> clazz){
        T object=null;
        try{
            object = SpringApplicationContextUtil.applicationContext.getBean(clazz);
        }catch(Exception ignored){
        }
        return object;
    }
    /**
     * 获取指定class类型的服务类
     * @param name 名称
     * @param requiredType class对象
     * @return 服务类
     */
    public static <T> T getBean(String name,Class<T> requiredType){
        T object=null;
        try{
            object=SpringApplicationContextUtil.applicationContext.getBean(name,requiredType);
        }catch(Exception ignored){
        }
        return object;
    }
    /**
     * 是否存在执行名称的服务类
     * @param name 名称
     * @return true 存在  flase 不存在
     */
    public static boolean containsBean(String name){
        return SpringApplicationContextUtil.applicationContext.containsBean(name);
    }

    /**
     * 获取注册class类型
     */
    public static String[] getAliases(String name){
        String[] s=null;
        try{
            s=SpringApplicationContextUtil.applicationContext.getAliases(name);
        }catch(Exception ignored){
        }
        return s;
    }
}
