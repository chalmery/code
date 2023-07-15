package top.yangcc.subscribe.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: ycc
 * @date: 2023/7/12 下午10:43
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class AddEntity extends EventData {

    private String name;

    private String code;

    private String desc;
}
