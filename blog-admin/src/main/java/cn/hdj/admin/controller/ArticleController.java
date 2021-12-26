package cn.hdj.admin.controller;


import cn.hdj.admin.domain.dto.ArticleFormDTO;
import cn.hdj.admin.domain.dto.ArticleSearchFormDTO;
import cn.hdj.admin.domain.vo.ArticleDetailVO;
import cn.hdj.admin.po.ArticlePO;
import cn.hdj.admin.service.IArticleService;
import cn.hdj.common.domain.vo.PageVO;
import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.common.validator.ValidatorUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 文章信息表 前端控制器
 * </p>
 *
 * @author huangjiajian
 * @since 2021-10-30
 */
@Api(tags = "文章管理")
@RestController
@RequestMapping("/api/admin/article")
public class ArticleController {

    @Autowired
    private IArticleService service;

    /**
     * 文章详情
     *
     * @return
     */
    @GetMapping("/info/{id:\\d+}")
    @ApiOperation(value = "文章详情", httpMethod = "GET", response = ResultVO.class)
    public ResultVO<ArticleDetailVO> info(@PathVariable("id") Long id) {
        ArticleDetailVO articleDetailVO=this.service.detail(id);
        return ResultVO.successJson(articleDetailVO);
    }

    /**
     * 文章列表
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "文章列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list(ArticleSearchFormDTO form) {
        IPage<ArticlePO> page = this.service.queryList(form);
        return ResultVO.successJson(PageVO.build(page));
    }

    /**
     * 保存文章
     *
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存文章", httpMethod = "POST", response = ResultVO.class)
    public ResultVO save(@RequestBody ArticleFormDTO form) {
        ValidatorUtils.validateEntity(form);
        this.service.saveArticle(form);
        return ResultVO.successJson(form.getId());
    }

    /**
     * 修改文章
     *
     * @return
     */
    @PutMapping("/edit/{id:\\d+}")
    @ApiOperation(value = "修改文章", httpMethod = "PUT", response = ResultVO.class)
    public ResultVO edit(@PathVariable("id") Long id, @RequestBody ArticleFormDTO form) {
        ValidatorUtils.validateEntity(form);
        this.service.editArticle(id, form);
        return ResultVO.successJson();
    }


    @DeleteMapping("/delete")
    @ApiOperation(value = "删除文章", httpMethod = "DELETE", response = ResultVO.class)
    public ResultVO deleteBatch(@RequestBody List<Long> articleIds) {
        this.service.deleteBatch(articleIds);
        return ResultVO.successJson();
    }


    @PutMapping("/publish/{id}")
    @ApiOperation(value = "发布文章", httpMethod = "POST", response = ResultVO.class)
    public ResultVO publishArticle(@PathVariable("id") Long articleId) {
        this.service.publishArticle(articleId);
        return ResultVO.successJson();
    }
}


