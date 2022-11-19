package top.yangcc.metadata.enums;

import lombok.Getter;

@Getter
public enum LocalCacheDataType {


    BIZ_FIELD("biz_field","业务枚举");


    private final String type;

    private final String name;

    LocalCacheDataType(String type, String name) {
        this.type = type;
        this.name = name;
    }

}
