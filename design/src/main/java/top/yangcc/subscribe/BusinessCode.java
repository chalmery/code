package top.yangcc.subscribe;

import top.yangcc.subscribe.data.AddEntity;
import top.yangcc.subscribe.data.EventEnum;
import top.yangcc.subscribe.listener.SendMqEventListener;
import top.yangcc.subscribe.listener.SendSlsEventListener;

/**
 * 业务代码
 * @author: ycc
 * @date: 2023/7/12 下午11:09
 */
public class BusinessCode {

    public static void main(String[] args) {

        //正确应该在类初始化时，创建订阅：
        EventManager.subscribe(EventEnum.ADD, new SendMqEventListener());
        EventManager.subscribe(EventEnum.ADD, new SendSlsEventListener());

        //省略业务代码
        //做完事情之后，发送通知
        AddEntity entity = new AddEntity("name","code","desc");

        EventManager.notify(EventEnum.ADD,entity);
    }
}
