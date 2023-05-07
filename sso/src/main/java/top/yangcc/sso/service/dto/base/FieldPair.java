package top.yangcc.sso.service.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldPair implements Serializable {

    private String label;

    private Object value;
}
