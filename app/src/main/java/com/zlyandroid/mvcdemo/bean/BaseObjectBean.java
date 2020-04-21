package com.zlyandroid.mvcdemo.bean;



/**
 * @author zhangliyang
 * @date 2018/4/24.
 * Description：对象
 */
public class BaseObjectBean<BaseResponse> {


    /**
     * msg : success
     * code : 0
     * data : {}
     */

    private String errorMsg;
    private int errorCode;
    private DataBean data;

    public String getMsg() {
        return errorMsg;
    }

    public void setMsg(String msg) {
        this.errorMsg = msg;
    }

    public int getCode() {
        return errorCode;
    }

    public void setCode(int code) {
        this.errorCode = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean  {
    }

}
