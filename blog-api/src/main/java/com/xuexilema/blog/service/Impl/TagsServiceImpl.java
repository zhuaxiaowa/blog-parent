package com.xuexilema.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuexilema.blog.dao.mapper.TagMapper;
import com.xuexilema.blog.dao.pojo.Tag;
import com.xuexilema.blog.service.TagsService;

import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagMapper tagMapper;  // mybatiplus无法进行多表查询 自己写一个
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {

        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);

        return copyList(tags);
    }

    /**
     * 查询最热标签
     * @param limit
     * @return
     */
    @Override
    public List<TagVo> hot(int limit) {
        List<Long> hotsTagIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(hotsTagIds)){
            return Collections.emptyList();
        }
        List<Tag> tagList = tagMapper.findTagsByHotsTagIds(hotsTagIds);
        return copyList(tagList);
    }

    /**
     * 所有文章标签
     * @return
     */
    @Override
    public Result findAll() {
        List<Tag> tagList = this.tagMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(tagList));
    }

    private List<TagVo> copyList(List<Tag> tags) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tags) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    private TagVo copy(Tag tag) {
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }


}
