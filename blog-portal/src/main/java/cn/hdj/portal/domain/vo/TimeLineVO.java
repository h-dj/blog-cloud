package cn.hdj.portal.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author huangjiajian
 * @version 1.0
 * @description: 时间线
 */
@Data
public class TimeLineVO implements Serializable {
    /**
     * 年
     */
    private Integer  year;
    /**
     * 文章数量
     */
    private Integer count;

    private List<TimelineMonthVO> months;


}
