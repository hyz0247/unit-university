package com.example.vo;

import lombok.Data;

import java.util.Map;

@Data
public class PieVO {
    private Integer value;
    private String name;
    private Map<String,String> itemStyle;
}
