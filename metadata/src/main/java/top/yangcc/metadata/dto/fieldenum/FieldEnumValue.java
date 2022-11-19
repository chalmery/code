package top.yangcc.metadata.dto.fieldenum;

import lombok.Data;

import java.io.Serializable;

@Data
public class FieldEnumValue implements Serializable {

    private String label;

    private Object value;

}
