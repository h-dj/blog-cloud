package cn.hdj.admin.domain.dto;

import cn.hdj.common.domain.dto.BaseDTO;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @Author huangjiajian
 * @Date 2021/11/28 下午11:38
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
public class ArticleSearchFormDTO extends BaseDTO {

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章分类")
    private Long categoryId;

    @ApiModelProperty(value = "是否推荐")
    private Boolean recommend;

    @JsonIgnore
    public List<OrderItem> getOrderList() {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.desc("top"));
        if (BooleanUtil.isTrue(recommend)) {
            orderItems.add(OrderItem.desc("recommend"));
        } else {
            orderItems.add(OrderItem.desc("create_time"));
        }
        return orderItems;
    }

}
