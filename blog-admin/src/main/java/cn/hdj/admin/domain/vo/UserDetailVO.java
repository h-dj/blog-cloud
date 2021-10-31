package cn.hdj.admin.domain.vo;

import cn.hdj.admin.po.UserPO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/30 下午9:30
 */
@Getter
@Setter
@ApiModel
public class UserDetailVO extends UserPO {

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 加密盐
     */
    @JsonIgnore
    private String salt;

    /**
     * token盐
     */
    @JsonIgnore
    private String tokenSalt;
    /**
     * 角色
     */
    private Set<Long> roleIds;

}
