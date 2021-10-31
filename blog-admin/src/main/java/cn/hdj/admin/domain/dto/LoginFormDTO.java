package cn.hdj.admin.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huangjiajian
 * @Description: 登陆Form
 */
@Data
@ApiModel(value = "LoginForm", description = "登陆")
public class LoginFormDTO {
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "账户")
    private String account;

    @Override
    public String toString() {
        return "LoginForm{" +
                "account='" + account + '\'' +
                '}';
    }
}
