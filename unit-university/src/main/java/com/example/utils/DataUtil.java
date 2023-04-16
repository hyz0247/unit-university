package com.example.utils;

import java.util.HashMap;
import java.util.Map;

public class DataUtil {

    public static Map<String,String> createItemStyle(Integer number){
        String color = "";
        if(number<5){
            color = "#96dee8";
        }
        if(number>5 && number<10){
            color = "#c4ebad";
        }
        if(number>10 && number<20){
            color = "#6be6c1";
        }
        if(number>20 && number<30){
            color = "#3fb1e3";
        }
        if(number>30){
            color = "#a0a7e6";
        }
        Map<String,String> map = new HashMap<>();
        map.put("color", color);
        return map;
    }

}
