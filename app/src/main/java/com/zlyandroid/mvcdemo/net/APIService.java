package com.zlyandroid.mvcdemo.net;



import com.zlyandroid.mvcdemo.bean.BaseObjectBean;
import com.zlyandroid.mvcdemo.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author zhangliyang
 * @date 2018/4/24.
 * Description：
 */
public interface APIService {

    /**
     * 登陆
     * https://www.wanandroid.com/user/login?username=zqpm&password=123456
     * @param username 账号
     * @param password 密码
     * @return
     */

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseResponse<LoginBean.DataBean>> login(@Field("username") String username,
                                                          @Field("password") String password);

    /**
     * 测试
     * http://image.so.com/j?q=北京&sn=0&pn=50
     * ?q={q}&sn={sn}&pn={pn}
     * Flowable
     */
    @GET("j")
    Observable<BaseObjectBean<LoginBean>> getList(@Query("q") String city,
                                                @Query("sn") Integer sn,
                                                @Query("pn") Integer pn);

}
