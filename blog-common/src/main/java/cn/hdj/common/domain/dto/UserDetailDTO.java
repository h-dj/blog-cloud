package cn.hdj.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/30 下午8:21
 */
@Setter
@Getter
public class UserDetailDTO  implements java.io.Serializable {

    private static final long serialVersionUID = -2826126367541073780L;
    /**
     * 实体ID
     */
    private Long id;
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态：0：正常，1：禁用
     */
    private Boolean enable;
    /**
     * 删除状态：0：未删除，1：已删除
     */
    private Boolean deleted;
    /**
     * 锁定状态：0：未锁定，1：已锁定
     */
    private Boolean locked;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登陆时间
     */
    private Date loginTime;


    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更改时间
     */
    private Date updateTime;
    /**
     * token盐
     */
    @JsonIgnore
    private String tokenSalt;
    /**
     * 角色
     */
    private Set<Long> roleIds;
    /**
     * 角色名称
     */
    private Set<String> roleNames;
    /**
     * 权限
     */
    private List<String> permissions;
}
