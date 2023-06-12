package top.yangcc.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Bean工厂
 */
public class BeanFactory {

    /**
     * 存放 bean
     */
    private Map<String, Object> beanMap = new HashMap<>();


    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }

    public void registerBean(String name, Object bean) {
        beanMap.put(name, bean);
    }
}
