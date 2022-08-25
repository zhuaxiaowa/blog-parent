package com.xuexilema.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuexilema.blog.dao.dos.Archivs;
import com.xuexilema.blog.dao.pojo.Article;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {

    List<Archivs> listArchives();

}
