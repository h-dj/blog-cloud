package cn.hdj.portal.controller;


import cn.hdj.common.domain.vo.ResultVO;
import cn.hdj.portal.domain.dto.ArticleSearchFormDTO;
import cn.hdj.portal.domain.vo.ArticleDetailVO;
import cn.hdj.portal.domain.vo.TimeLineVO;
import cn.hdj.portal.po.CategoryPO;
import cn.hdj.portal.service.IArticleService;
import cn.hdj.portal.service.ICategoryService;
import cn.hdj.portal.service.ITagService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author huangjiajian
 * @Description:　前台博客文章控制器
 */
@RestController
@RequestMapping("/api/articles")
public class ArticleFrontController {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private ITagService tagService;
    @Autowired
    private ICategoryService categoryService;



    /**
     * 搜索标题，描述，内容
     *
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public ResultVO search(@RequestParam("keyword") String keyword,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        //articleRepository.pageSearch(keyword, page, pageSize);
        return ResultVO.successJson();
    }


    @GetMapping({"", "/"})
    @ApiOperation(value = "文章列表", httpMethod = "GET", response = ResultVO.class)
    public ResultVO list(ArticleSearchFormDTO params) {
        return this.articleService.articleList(params);
    }


    @GetMapping("/{slug}")
    public ResultVO detail(@PathVariable("slug") String slug) {
        ArticleDetailVO detail = articleService.detail(slug);
        return ResultVO.successJson(detail);
    }




    @GetMapping("/tags")
    @ApiOperation(value = "标签墙", httpMethod = "GET", response = ResultVO.class)
    public ResultVO tags() {
        return ResultVO.successJson(tagService.groupCount());
    }


    @GetMapping("/categorys")
    @ApiOperation(value = "标签墙", httpMethod = "GET", response = ResultVO.class)
    public ResultVO categorys() {
        return ResultVO.successJson(
                categoryService.list(Wrappers.<CategoryPO>lambdaQuery()
                        .select(CategoryPO::getId, CategoryPO::getCategoryName)
                )
        );
    }

    /**
     * @return
     */
    @GetMapping("/archive")
    @ApiOperation(value = "归档", httpMethod = "GET", response = ResultVO.class)
    public ResultVO archive(@ApiParam(value = "标签") String tag) {
        List<TimeLineVO> list= articleService.archive(tag);
        return ResultVO.successJson(list);
    }
}
