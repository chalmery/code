package top.yangcc.metadata.dto;

import lombok.Data;

@Data
public class CacheData<T> {

    private Long id;

    private String name;

    private String key;

    private T data;

    public CacheData(String key) {
        this.key = key;
    }
}
