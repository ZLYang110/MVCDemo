package com.zlyandroid.mvcdemo.bean;

import java.util.List;

/**
 * @author zhangliyang
 * @date 2018/4/24.
 * Description：数组
 */
public class BaseArrayBean<T> {

    /**
     * status : 1
     * msg : 获取成功
     * result : [] 数组
     */

    private int errorCode;
    private String errorMsg;
    private List<T> result;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
