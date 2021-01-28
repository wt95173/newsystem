package com.nsystem.vo;

import lombok.Data;

import java.util.List;

@Data
public class TableVo<T> {
    private Integer code;
    private String msg;
    private long count;
    private List<T> data;
}
