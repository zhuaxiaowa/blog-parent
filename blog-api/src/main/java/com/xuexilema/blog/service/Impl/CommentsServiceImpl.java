package com.xuexilema.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuexilema.blog.dao.mapper.CommentsMapper;
import com.xuexilema.blog.dao.pojo.Comment;
import com.xuexilema.blog.dao.pojo.SysUser;
import com.xuexilema.blog.service.CommentsService;
import com.xuexilema.blog.service.UserService;
import com.xuexilema.blog.utils.UserThreadLocal;
import com.xuexilema.blog.vo.CommentVo;
import com.xuexilema.blog.vo.Result;
import com.xuexilema.blog.vo.UserVo;
import com.xuexilema.blog.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private UserService userService;

    /**
     * 根据文章id查询评论列表
     *
     * @param articleId
     * @return
     */
    @Override
    public Result commentsByArticleId(Long articleId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentsMapper.selectList(queryWrapper);

        return Result.success(copyList(comments));
    }

    /**
     * 评论
     * @param commentParam
     * @return
     */
    @Override
    public Result comment(CommentParam commentParam) {
        SysUser sysUser = UserThreadLocal.getLocal();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setContent(commentParam.getContent());
        comment.setAuthorId(sysUser.getId());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if(parent == null || parent == 0){
            comment.setLevel(1);
        }else{
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 :parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        // 做完之后记得做更新操作
        this.commentsMapper.insert(comment);
        return Result.success(null);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setId(String.valueOf(comment.getId()));
        // 时间格式化
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-HH-dd" +
                " HH:mm"));
        // 作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = userService.findUserVoById(authorId);
        commentVo.setAuthor(userVo);
        // 评论的评论
        List<CommentVo> commentVoList = findCommentsByParentId(comment.getId());
        commentVo.setChildrens(commentVoList);
        if (comment.getLevel() > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = userService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }
        return commentVo;
    }

    /**
     * 根据父级id 查找评论的评论
     *
     * @param parentId
     * @return
     */
    private List<CommentVo> findCommentsByParentId(Long parentId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, parentId);
        queryWrapper.eq(Comment::getLevel, 2);
        List<Comment> comments = commentsMapper.selectList(queryWrapper);
        return copyList(comments);

    }
}
