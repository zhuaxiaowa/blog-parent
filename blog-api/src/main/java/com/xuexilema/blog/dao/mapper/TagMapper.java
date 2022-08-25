package com.xuexilema.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuexilema.blog.dao.pojo.Tag;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询tags
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查找最热标签的id
     * @param limit 前limit个最热标签
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 根据最热标签Id查找最热标签tagvo
     * @param hotsTagId
     * @return
     */
    List<Tag> findTagsByHotsTagIds(List<Long> hotsTagId);
}
