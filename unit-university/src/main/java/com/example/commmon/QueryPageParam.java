package com.example.commmon;

import lombok.Data;

import java.util.HashMap;

@Data
public class QueryPageParam {
    /** 默认当前页条数和当前页面*/
    private static int PAGE_SIZE = 20;
    private static int PAGE_NUM = 1;

    private int pageSize = PAGE_SIZE;
    private int pageNum = PAGE_NUM;
    private String checkCode = "";

    private HashMap param = new HashMap();
}
