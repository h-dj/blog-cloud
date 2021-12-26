package cn.hdj.portal.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author huangjiajian
 * @version 1.0
 * @description: 时间线归档--月份
 */
@SuppressWarnings("unchecked")
@Data
public class TimelineMonthVO {
    private Integer month;
    private Integer count;
    private List<TimelinePostVO> posts;
}
