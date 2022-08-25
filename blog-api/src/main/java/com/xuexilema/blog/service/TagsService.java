package com.xuexilema.blog.service;


import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.TagVo;


import java.util.List;

public interface TagsService {

    List<TagVo> findTagsByArticleId(Long articleId);

    List<TagVo> hot(int limit); //最热标签（前几个）

    /**
     * 所有文章标签
     * @return
     */
    Result findAll();
}
