package top.yangcc.sso.module;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchData<T> {

    @Schema(description = "分页信息")
    private Page page;

    @Schema(description = "当前条件下总条数")
    private Long count;

    private List<T> data;
}
