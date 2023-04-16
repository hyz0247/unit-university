package com.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class JobTypeData {

    private Integer value;
    private String label;
    List<JobTypeData> children;
}
