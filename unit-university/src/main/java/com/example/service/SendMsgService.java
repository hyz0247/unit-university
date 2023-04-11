package com.example.service;

import java.util.Map;

public interface SendMsgService {
    /**
     *
     * @param phoneNumbers 手机
     * @param param 发送 code
     * @return
     */
    public Boolean sendMsg(String  phoneNumbers, Map<String ,Integer> param);

}
