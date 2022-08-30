package com.xuexilema.blog.admin.vo.params;

import lombok.Data;

@Data
public class PageParam {
    // currentPage: 1, pageSize: 10, total: 0, queryString: null}
    private Integer currentPage;

    private Integer pageSize;

    private String queryString;
}
