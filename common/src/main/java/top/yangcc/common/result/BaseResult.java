package top.yangcc.common.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResult implements Serializable {

    private boolean success;

    private Integer code;

    private String message;

    private String requestId;

}
