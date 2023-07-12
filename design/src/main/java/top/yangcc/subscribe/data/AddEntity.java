package top.yangcc.subscribe.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ycc
 * @date: 2023/7/12 下午10:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddEntity extends EventData {

    private String name;

    private String code;

    private String desc;
}
