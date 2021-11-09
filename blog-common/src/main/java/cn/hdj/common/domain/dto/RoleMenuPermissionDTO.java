package cn.hdj.common.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/10/31 下午11:59
 */
@Getter
@Setter
public class RoleMenuPermissionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求url
     */
    private String requestUrl;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 权限编码
     */
    private String permissionCode;
}
