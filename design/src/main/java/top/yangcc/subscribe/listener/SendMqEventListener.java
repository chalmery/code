package top.yangcc.subscribe.listener;

import top.yangcc.subscribe.EventListener;
import top.yangcc.subscribe.data.AddEntity;
import top.yangcc.subscribe.data.EventEnum;

/**
 * @author: ycc
 * @date: 2023/7/12 下午11:04
 */
public class SendMqEventListener implements EventListener<AddEntity> {

    @Override
    public void execute(EventEnum eventType, AddEntity data) {
        System.out.printf("mq send 监听到事件 %s%n",data);
    }
}
