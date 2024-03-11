package com.spzx.model.dto.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "系统用户请求参数实体类")
public class AdministratorDto {

    @Schema(description = "搜索关键字")
    private String keyword ;

    @Schema(description = "开始时间")
    private String createTimeBegin ;

    @Schema(description = "结束时间")
    private String createTimeEnd;

}
