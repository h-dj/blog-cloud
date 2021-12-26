package cn.hdj.admin.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/28 下午4:36
 */
@Data
public class CategoryFormDTO {

    /**
     * 分类id
     */
    private Long id;
    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    /**
     * 分类描述
     */
    private String description;


}
