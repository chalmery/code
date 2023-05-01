package top.yangcc.common.result;

import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;

@Data
@SuppressWarnings("unused")
public class BaseResult implements Serializable {

    private static final String TRACE_ID = "traceId";

    private boolean success;

    private Integer code;

    private String message;

    private String requestId;


    public String getRequestId() {
        return MDC.get(TRACE_ID);
    }
}
