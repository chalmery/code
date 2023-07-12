package top.yangcc.subscribe.data;

import lombok.Getter;

/**
 * @author: ycc
 * @date: 2023/7/12 下午10:41
 */
@SuppressWarnings("unused")
@Getter
public enum EventEnum {

    ADD("add","新增事件"),

    ;


    private final String code;

    private final String desc;


    EventEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static EventEnum bgCode(String code){
        if (code ==null){
            return null;
        }
        for (EventEnum value : EventEnum.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

}
