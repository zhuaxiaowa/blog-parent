package com.xuexilema.blog.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 扫包，将此包下的接口生成代理实现类，并注册到spring 容器中。
@MapperScan("com.xuexilema.blog.dao.mapper")
public class MybatisPlusConfig {

    // 分页插件
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor Interceptor = new MybatisPlusInterceptor();
        Interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return Interceptor;
    }

}
