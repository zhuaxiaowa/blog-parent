package com.xuexilema.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuexilema.blog.dao.pojo.Comment;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.params.CommentParam;

public interface CommentsService{
    /**
     * 根据文章id查找评论列表
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    /**
     * 评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
