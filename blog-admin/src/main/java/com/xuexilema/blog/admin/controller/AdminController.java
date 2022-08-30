package com.xuexilema.blog.admin.controller;

import com.xuexilema.blog.admin.pojo.Permission;
import com.xuexilema.blog.admin.service.PermissionService;
import com.xuexilema.blog.admin.vo.Result;
import com.xuexilema.blog.admin.vo.params.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/permission/permissionList")
    public Result permissionList(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }
    @PostMapping("/permission/add")
    public Result permissionAdd(@RequestBody Permission permission){
        return permissionService.add(permission);
    }
    @PostMapping("/permission/update")
    public Result permissionUpdate(@RequestBody Permission permission){
        return permissionService.update(permission);
    }
    @PostMapping("/permission/delete/{id}")
    public Result permissionDelete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
