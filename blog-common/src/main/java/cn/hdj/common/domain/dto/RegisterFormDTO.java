package cn.hdj.common.domain.dto;

import java.io.Serializable;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/8 下午9:30
 */
@Getter
@Setter
public class RegisterFormDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Email
    private String email;
    @NotBlank
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码为空")
    private String validCode;
}
