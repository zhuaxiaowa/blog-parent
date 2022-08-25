package com.xuexilema.blog.controller;

import com.xuexilema.blog.service.TagsService;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @GetMapping("/hot")
    public Result listHotTags() {
        int limit = 6;
        List<TagVo> tagVos = tagsService.hot(limit);
        return Result.success(tagVos);
    }

    /**
     * 所有文章标签
     *
     * @return
     */
    @GetMapping
    public Result tags() {
        return tagsService.findAll();
    }

    /**
     * 文章分类标签
     *
     * @return
     */
    @GetMapping("/detail")
    public Result tagsDetail() {
        return tagsService.findTagsDetail();
    }
    @GetMapping("/detail/{id}")
    public Result tagsDetailById(@PathVariable("id") Long id){
        return tagsService.tagsDetailById(id);
    }
}
