package com.xuexilema.blog.controller;

import com.xuexilema.blog.dao.pojo.SysUser;
import com.xuexilema.blog.service.CommentsService;
import com.xuexilema.blog.utils.UserThreadLocal;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/article/{id}")
    public Result comments(@PathVariable("id") Long articleId) {
        return commentsService.commentsByArticleId(articleId);
    }

    /**
     * 评论
     * @param commentParam
     * @return
     */
    @PostMapping("/create/change")
    public Result comment(@RequestBody CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.getLocal();
        System.out.println(sysUser);
        return commentsService.comment(commentParam);
    }
}
