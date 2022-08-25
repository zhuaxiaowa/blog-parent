package com.xuexilema.blog.service;


import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.TagVo;


import java.util.List;

public interface TagsService {

    List<TagVo> findTagsByArticleId(Long articleId);

    List<TagVo> hot(int limit); //最热标签（前几个）

    /**
     * 所有文章标签
     *
     * @return
     */
    Result findAll();

    /**
     * 文章分类标签
     *
     * @return
     */
    Result findTagsDetail();

    /**
     * 根据tagId查找tag下的文章
     * @param id
     * @return
     */
    Result tagsDetailById(Long id);
}
