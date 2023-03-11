package top.yangcc.common.utils.rule;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 表达式符号枚举
 */
@Getter
public enum OperateEnum {

    AND(1, "&"),

    OR(1, "|"),

    LEFT_PARENTHESIS(99, "(");


    private final Integer priority;

    private final String code;


    OperateEnum(Integer priority, String code) {
        this.priority = priority;
        this.code = code;
    }


    public  static  OperateEnum parse(String code){
        if (StringUtils.isBlank(code)){
            return null;
        }
        for (OperateEnum value : OperateEnum.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
