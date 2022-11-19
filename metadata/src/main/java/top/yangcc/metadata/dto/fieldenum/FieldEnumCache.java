package top.yangcc.metadata.dto.fieldenum;

import lombok.Data;

import java.util.List;

@Data
public class FieldEnumCache {

    private Long fieldId;

    private String fieldName;

    private List<FieldEnumValue> enumValueList;
}
