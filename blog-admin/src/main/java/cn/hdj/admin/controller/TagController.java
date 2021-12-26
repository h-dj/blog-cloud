package cn.hdj.admin.controller;


import cn.hdj.admin.domain.dto.TagFormDTO;
import cn.hdj.admin.po.TagPO;
import cn.hdj.admin.service.ITagService;
import cn.hdj.common.domain.dto.BaseDTO;
import cn.hdj.common.domain.vo.PageVO;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.validator.ValidatorUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Api(tags = "标签")
@RestController
@RequestMapping("/api/admin/tags")
public class TagController {

    @Autowired
    private ITagService service;

    @ApiOperation(value = "标签下拉列表", httpMethod = "GET", response = ResultVO.class)
    @GetMapping("/selectList")
    public ResultVO selectable() {
        List<TagPO> tagList = service.list(Wrappers.<TagPO>lambdaQuery()
                .select(TagPO::getId, TagPO::getTagName)
        );
        return ResultVO.successJson(tagList);
    }

    @ApiOperation(value = "标签列表", httpMethod = "GET", response = ResultVO.class)
    @GetMapping("/list")
    public ResultVO list(String tagName, BaseDTO page) {
        IPage<TagPO> tagList = service.page(page.getIPage(),
                Wrappers.<TagPO>lambdaQuery()
                        .like(StrUtil.isNotEmpty(tagName), TagPO::getTagName, tagName)
        );
        return ResultVO.successJson(PageVO.build(tagList));
    }


    @ApiOperation(value = "标签详情", httpMethod = "GET", response = ResultVO.class)
    @GetMapping("/info/{id}")
    public ResultVO info(@PathVariable("id") Long tagId) {
        TagPO tagPO = service.getById(tagId);
        return ResultVO.successJson(tagPO);
    }


    @ApiOperation(value = "添加标签", httpMethod = "POST", response = ResultVO.class)
    @PostMapping("/save")
    public ResultVO save(@RequestBody TagFormDTO form) {
        ValidatorUtils.validateEntity(form);
        TagPO tagPO = new TagPO();
        BeanUtil.copyProperties(form, tagPO);
        this.service.saveAndUpdateById(tagPO);
        return ResultVO.successJson();
    }


    @ApiOperation(value = "更新标签", httpMethod = "PUT", response = ResultVO.class)
    @PutMapping("/update/{id}")
    public ResultVO edit(@PathVariable("id") Long id, @RequestBody TagFormDTO form) {
        ValidatorUtils.validateEntity(form);
        TagPO tagPO = BeanUtil.copyProperties(form, TagPO.class);
        tagPO.setId(id);
        this.service.saveAndUpdateById(tagPO);
        return ResultVO.successJson();
    }


    @ApiOperation(value = "删除标签", httpMethod = "DELETE", response = ResultVO.class)
    @DeleteMapping("/delete")
    public ResultVO delete(@RequestBody List<Long> ids) {
        service.delete(ids);
        return ResultVO.successJson();
    }
}

