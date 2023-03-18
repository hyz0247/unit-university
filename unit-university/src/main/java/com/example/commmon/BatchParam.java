package com.example.commmon;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class BatchParam {

    private int status;
    private List idList = new ArrayList<>();
    private String reply;
    private int adminId;

    private HashMap hashMap = new HashMap();


}
