package cn.hdj.admin.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author huangjiajian
 * @Description:
 */
@Data
public class TagFormDTO {

    /**
     * 标签id
     */
    private Long id;
    /**
     * 标签名称
     */
    @NotBlank(message = "标签名不能为空!")
    String tagName;
}
