package cn.hdj.portal.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author huangjiajian
 * @version 1.0
 * @description: 时间线归档--文章
 */
@Data
public class TimelinePostVO {
    private Long id;
    private String slug;
    private String title;
    private Date createTime;
}
