package com.spzx.model.dto.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "系统角色请求参数实体类")
public class RoleDto {

    @Schema(description = "角色名称")
    private String roleName;

}
