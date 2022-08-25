package com.xuexilema.blog.service;

import com.xuexilema.blog.vo.ArticleVo;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.params.ArticleParam;
import com.xuexilema.blog.vo.params.PageParams;

import java.util.List;

public interface ArticleService {

    /**
     * 分页插件 首页文章列表
     * @param pageParams
     * @return
     */
    List<ArticleVo> listArticlesPage(PageParams pageParams);

    /**
     * 首页最热文章
     * @param limit
     * @return
     */
    Result hotArticles(int limit);

    /**
     * 首页最新文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 首页文章归档
     * @return
     */
    Result listArchives();

    /**
     * 根据id查找单个文章详情
     * @param id
     * @return
     */
    Result findArticleById(Long id);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
}
