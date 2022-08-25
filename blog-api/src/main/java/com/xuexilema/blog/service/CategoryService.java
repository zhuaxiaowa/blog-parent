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
}
