package com.xuexilema.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuexilema.blog.dao.dos.Archivs;
import com.xuexilema.blog.dao.mapper.ArticleBodyMapper;
import com.xuexilema.blog.dao.mapper.ArticleMapper;

import com.xuexilema.blog.dao.mapper.ArticleTagMapper;
import com.xuexilema.blog.dao.pojo.Article;
import com.xuexilema.blog.dao.pojo.ArticleBody;
import com.xuexilema.blog.dao.pojo.ArticleTag;
import com.xuexilema.blog.dao.pojo.SysUser;
import com.xuexilema.blog.service.*;
import com.xuexilema.blog.utils.UserThreadLocal;
import com.xuexilema.blog.vo.*;
import com.xuexilema.blog.vo.params.ArticleParam;
import com.xuexilema.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TagsService tagsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadService threadService;

    /**
     * 首页文章列表
     *
     * @param pageParams
     * @return
     */
    @Override
    public List<ArticleVo> listArticlesPage(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticlesPage(page,pageParams.getCategoryId()
        ,pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
        return copyList(articleIPage.getRecords(),true,true);
    }

    /**
     * 首页最热文章
     *
     * @param limit
     * @return
     */
    @Override
    public Result hotArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);  // 这个错误很要命 limit 后面要有空格 语法错误
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 首页最新文章
     *
     * @param limit
     * @return
     */
    @Override
    public Result newArticles(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 首页文章归档
     *
     * @return
     */
    @Override
    public Result listArchives() {
        List<Archivs> archivesList = articleMapper.listArchives();

        return Result.success(archivesList);
    }

    /**
     * 根据id 查询单个文章详情
     *
     * @param id
     * @return
     */
    @Override
    public Result findArticleById(Long id) {
        Article article = articleMapper.selectById(id);

        // 更新文章阅读次数
        threadService.updateViewCount(articleMapper, article);
        return Result.success(copy(article, true, true, true, true));
    }

    /**
     * 发布文章
     *
     * @param articleParam
     * @return
     */
    @Override
    @Transactional
    public Result publish(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.getLocal();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setBodyId(-1L);
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setCreateDate(System.currentTimeMillis());
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setCommentCounts(0);
        article.setWeight(Article.Article_Common);
        this.articleMapper.insert(article);
        // 标签
        List<TagVo> tagVos = articleParam.getTags();
        if (tagVos != null) {
            for (TagVo tagVo : tagVos) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(Long.parseLong(tagVo.getId()));
                articleTag.setArticleId(article.getId());
                this.articleTagMapper.insert(articleTag);
            }
        }
        // articleBody
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        article.setBodyId(articleBody.getId());

        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(String.valueOf(article.getId()));
        return Result.success(articleVo);
    }

    // copyList重写
    private List<ArticleVo> copyList(List<Article> articles, boolean isAuthor, boolean isTags) {

        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articles) {
            ArticleVo articleVo = copy(article, isAuthor, isTags, false, false);
            articleVoList.add(articleVo);
        }

        return articleVoList;

    }

    private List<ArticleVo> copyList(List<Article> articles, boolean isAuthor, boolean isTags, boolean isBody) {

        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articles) {
            ArticleVo articleVo = copy(article, isAuthor, isTags, isBody, false);
            articleVoList.add(articleVo);
        }

        return articleVoList;

    }

    private List<ArticleVo> copyList(List<Article> articles, boolean isAuthor, boolean isTags, boolean isBody, boolean isCategory) {

        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articles) {
            ArticleVo articleVo = copy(article, isAuthor, isTags, isBody, isCategory);
            articleVoList.add(articleVo);
        }

        return articleVoList;

    }

    // 截止
    private ArticleVo copy(Article article, boolean isAuthor, boolean isTags, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        if (isAuthor) {
            SysUser author = userService.findById(article.getAuthorId());
            articleVo.setAuthor(author.getNickname());
        }
        if (isTags) {
            List<TagVo> tags = tagsService.findTagsByArticleId(article.getId());
            articleVo.setTags(tags);
        }
        // 并不是所有的接口都需要 标签作者信息

        if (isBody) {
            ArticleBodyVo articleBodyVo = findArticleBody(article.getId());
            articleVo.setBody(articleBodyVo);
        }

        if (isCategory) {
            CategoryVo categoryVo = findArticleCategory(article.getCategoryId());
            articleVo.setCategoryVo(categoryVo);
        }
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return articleVo;
    }

    /**
     * 根据文章id 查询种类
     *
     * @param articleId
     * @return
     */
    private CategoryVo findArticleCategory(Long categoryId) {
        return categoryService.findCategory(categoryId);
    }

    /**
     * 根据文章id查询文章体
     *
     * @param articleId
     * @return
     */
    private ArticleBodyVo findArticleBody(Long articleId) {
        LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleBody::getArticleId, articleId);
        ArticleBody articleBody = articleBodyMapper.selectOne(queryWrapper);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
