package com.xuexilema.blog.service;

import com.xuexilema.blog.vo.CategoryVo;
import com.xuexilema.blog.vo.Result;

public interface CategoryService {

    CategoryVo findCategory(Long categoryId);

    /**
     * 查找文章所有标签
     * @return
     */
    Result findAll();

    /**
     * 查找首页种类详情
     * @return
     */
    Result findCategoryDetail();

    /**
     * 根据种类id查找该种类下有多少文章
     * @param id
     * @return
     */
    Result categoriesDetailById(Long id);
}
