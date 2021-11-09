package cn.hdj.common.domain.dto;
import java.io.Serializable;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author huangjiajian
 * @Description: 登陆Form
 */
@Getter
@Setter
@ApiModel(value = "LoginForm", description = "登陆")
public class LoginFormDTO implements Serializable {
	private static final long serialVersionUID = 1L;

    @NotBlank
    @ApiModelProperty(value = "密码")
    private String password;
    @NotBlank
    @ApiModelProperty(value = "账户")
    private String account;

}
