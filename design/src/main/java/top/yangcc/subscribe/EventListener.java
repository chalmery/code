package top.yangcc.subscribe;

import top.yangcc.subscribe.data.EventData;
import top.yangcc.subscribe.data.EventEnum;

/**
 * @author: ycc
 * @date: 2023/7/12 下午10:30
 */
public interface EventListener<T extends EventData> {

    void execute(EventEnum eventType, T data);
}
