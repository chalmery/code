package top.yangcc.subscribe;

import org.apache.commons.collections4.CollectionUtils;
import top.yangcc.subscribe.data.EventData;
import top.yangcc.subscribe.data.EventEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 事件管理类
 *
 * @author: ycc
 * @date: 2023/7/12 下午10:29
 */
@SuppressWarnings("unused")
public class EventManager {

    private static Map<EventEnum, List<EventListener<? extends EventData>>> eventListenerMap = new ConcurrentHashMap<>();

    /**
     * 创建订阅
     *
     * @param eventType 事件名称
     * @param listener  监听类
     */
    public static <T extends EventData> void subscribe(EventEnum eventType, EventListener<T> listener) {
        List<EventListener<? extends EventData>> eventListeners = eventListenerMap.get(eventType);
        if (CollectionUtils.isEmpty(eventListeners)) {
            List<EventListener<? extends EventData>> list = new ArrayList<>();
            list.add(listener);
            eventListenerMap.put(eventType, list);
        } else {
            eventListeners.add(listener);
        }
    }

    /**
     * 删除订阅
     *
     * @param eventType 事件名称
     * @param listener  监听类
     */
    public static <T extends EventData> void unsubscribe(EventEnum eventType, EventListener<T> listener) {
        List<EventListener<? extends EventData>> eventListeners = eventListenerMap.get(eventType);
        if (CollectionUtils.isNotEmpty(eventListeners)) {
            eventListeners.remove(listener);
        }
    }

    /**
     * 通知监听者
     *
     * @param eventType 事件类型
     * @param data      数据
     * @param <T>       类型
     */
    public static <T extends EventData> void publish(EventEnum eventType, T data) {
        List<EventListener<? extends EventData>> eventListeners = eventListenerMap.get(eventType);
        if (CollectionUtils.isNotEmpty(eventListeners)) {
            for (EventListener<? extends EventData> eventListener : eventListeners) {
                EventListener<T> listener = (EventListener<T>) eventListener;
                CompletableFuture.runAsync(()-> listener.execute(eventType, data));
            }
        }
    }


}


