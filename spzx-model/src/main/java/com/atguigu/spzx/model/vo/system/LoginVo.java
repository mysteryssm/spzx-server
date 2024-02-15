package com.atguigu.spzx.model.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录成功响应结果实体类")
public class LoginVo {

    @Schema(description = "token")
    private String token ;

    @Schema(description = "刷新token可以为空")
    private String refresh_token ;

}
