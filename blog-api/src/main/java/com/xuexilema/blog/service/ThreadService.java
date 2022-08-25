package com.xuexilema.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuexilema.blog.dao.mapper.ArticleMapper;
import com.xuexilema.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article) {

        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(article.getViewCounts() + 1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        queryWrapper.eq(Article::getViewCounts, article.getViewCounts());
        articleMapper.update(articleUpdate, queryWrapper);
        try {
            Thread.sleep(5000);
            // 睡眠5秒，证明不会影响主线程的使用
            System.out.println("文章阅读次数更新了。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
