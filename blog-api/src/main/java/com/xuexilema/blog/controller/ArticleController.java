package com.xuexilema.blog.controller;

import com.xuexilema.blog.com.aop.LogAnnotation;
import com.xuexilema.blog.service.ArticleService;
import com.xuexilema.blog.vo.ArticleVo;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.params.ArticleParam;
import com.xuexilema.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页文章列表
     *
     * @param pageParams
     * @return
     */
    // result是统一结果返回
    @PostMapping
    @LogAnnotation(module = "文章",operation = "获取文章列表")
    public Result articles(@RequestBody PageParams pageParams) {

        // ArticleVo页面接收的数据
        List<ArticleVo> articleVoList = articleService.listArticlesPage(pageParams);

//        int i = 10/0;   模拟系统异常

        return Result.success(articleVoList);
    }

    // git提交测试
    /**
     * 首页最热文章
     *
     * @return
     */
    @PostMapping("/hot")
    public Result hotArticles() {
        int limit = 5;
        Result result = articleService.hotArticles(limit);
        return result;
    }

    /**
     * 首页最新文章
     *
     * @return
     */
    @PostMapping("/new")
    public Result newArticles() {
        int limit = 5;
        Result result = articleService.newArticles(limit);
        return result;
    }

    @PostMapping("/listArchives")
    public Result listArchives() {
        int limit = 5;
        Result result = articleService.listArchives();
        return result;
    }

    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long id){
        return articleService.findArticleById(id);
    }

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
