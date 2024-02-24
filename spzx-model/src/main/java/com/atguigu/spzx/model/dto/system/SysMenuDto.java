package com.atguigu.spzx.model.dto.system;

import com.atguigu.spzx.model.entity.system.SysMenu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @description: SysMenuDto
 * @author: yck
 * @create: 2024-02-24
 */

@Data
@Schema(description = "系统菜单请求参数实体类")
public class SysMenuDto {
    @Schema(description = "节点id")
    private Long id;

    @Schema(description = "父节点id")
    private Long parentId;

    @Schema(description = "节点标题")
    private String title;

    @Schema(description = "组件名称")
    private String component;

    @Schema(description = "排序值")
    private Integer sortValue;

    @Schema(description = "状态(0:禁止,1:正常)")
    private Integer status;
}
