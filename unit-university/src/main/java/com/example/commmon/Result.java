package com.example.commmon;


import lombok.Data;

@Data
public class  Result {

    private int code;//编码 200 、400
    private String msg;// 成功、失败
    private Long total;//总记录数
    private Object data;//数据
    private String imgUrl;

    public static Result fail(String msg){
        return result(400,msg,0L,null);
    }
    public static Result suc(String msg ){
        return result(200,msg,0L,null);
    }
    public static Result fail(){
        return result(400,"失败",0L,null);
    }
    public static Result suc(){
        return result(200,"成功",0L,null);
    }
    public static Result suc(Object data){
        return result(200,"成功",0L,data);
    }
    public static Result suc(Object data,Long total){
        return result(200,"成功",total,data);
    }
    public static Result uploadSuc(String imgUrl){
        return result(200,"成功",imgUrl);
    }

    public static Result result(int code,String msg ,String imgUrl){
        Result res = new Result();
        res.setImgUrl(imgUrl);
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }

    private static Result result(int code,String msg,Long total,Object data){
        Result res = new Result();
        res.setData(data);
        res.setCode(code);
        res.setMsg(msg);
        res.setTotal(total);
        return res;
    }

}
