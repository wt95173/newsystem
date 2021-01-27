package com.nsystem.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

public class MybatisPlusConfig {
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
