package top.yangcc.common.result.module;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchData<T> {

    private Page page;

    private Long count;

    private List<T> data;
}
