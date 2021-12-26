package cn.hdj.admin.controller;


import cn.hdj.admin.domain.dto.CategoryFormDTO;
import cn.hdj.admin.po.CategoryPO;
import cn.hdj.admin.service.ICategoryService;
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
 * 分类表 前端控制器
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Api(tags = "文章分类")
@RestController
@RequestMapping("/api/admin/categorys")
public class CategoryController {

    @Autowired
    private ICategoryService service;


    
    @ApiOperation(value = "分类列表", httpMethod = "GET", response = ResultVO.class)
    @GetMapping("/list")
    public ResultVO list(String categoryName, BaseDTO form) {
        IPage page = service.page(
                form.getIPage(),
                Wrappers.<CategoryPO>lambdaQuery()
                        .like(StrUtil.isNotEmpty(categoryName), CategoryPO::getCategoryName, categoryName)
        );
        return ResultVO.successJson(PageVO.build(page));
    }

    @GetMapping("/selectList")
    public ResultVO selectable() {
        List<CategoryPO> list = service.list();
        return ResultVO.successJson(list);
    }


    @ApiOperation(value = "分类详情", httpMethod = "GET", response = ResultVO.class)
    @GetMapping("/info/{id}")
    public ResultVO list(@PathVariable("id") Long categoryId) {
        return ResultVO.successJson(service.getById(categoryId));
    }


    @ApiOperation(value = "添加分类", httpMethod = "POST", response = ResultVO.class)
    @PostMapping("/save")
    public ResultVO save(@RequestBody CategoryFormDTO categoryForm) {
        ValidatorUtils.validateEntity(categoryForm);
        CategoryPO CategoryPO = new CategoryPO();
        BeanUtil.copyProperties(categoryForm, CategoryPO);
        service.saveOrUpdateById(CategoryPO);
        return ResultVO.successJson();
    }


    @ApiOperation(value = "更新分类", httpMethod = "PUT", response = ResultVO.class)
    @PutMapping("/update/{id}")
    public ResultVO update(@PathVariable("id") Long id, @RequestBody CategoryFormDTO categoryForm) {
        ValidatorUtils.validateEntity(categoryForm);
        CategoryPO CategoryPO = new CategoryPO();
        BeanUtil.copyProperties(categoryForm, CategoryPO);
        CategoryPO.setId(id);
        service.saveOrUpdateById(CategoryPO);
        return ResultVO.successJson();
    }



    @DeleteMapping("/delete")
    @ApiOperation(value = "删除分类", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO delete(@RequestBody List<Long> ids) {
        service.delete(ids);
        return ResultVO.successJson();
    }
}


