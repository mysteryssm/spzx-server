package com.spzx.model.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "请求参数实体类")
public class OperateLogDto {

    @Schema(description = "模块名称")
    private String title;

    @Schema(description = "操作用户名")
    private String operName;

    @Schema(description = "开始时间")
    private String createTimeBegin;

    @Schema(description = "结束时间")
    private String createTimeEnd;

}
