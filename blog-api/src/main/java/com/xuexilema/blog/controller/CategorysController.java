package com.xuexilema.blog.controller;

import com.xuexilema.blog.service.CategoryService;
import com.xuexilema.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorys")
public class CategorysController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result categorys() {
        return categoryService.findAll();
    }
}
