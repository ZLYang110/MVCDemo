package com.zlyandroid.mvcdemo.net.cookie;

import com.zlyandroid.mvcdemo.util.SpUtils;
import com.zlyandroid.mvcdemo.util.log.ZLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            //解析Cookie
            for (String header : originalResponse.headers("Set-Cookie")) {

                stringBuilder.append(header);


                if(header.contains("JSESSIONID")){

                    String cookie = header.substring(header.indexOf("JSESSIONID"), header.indexOf(";"));
                    SpUtils.SetConfigString("cookie", cookie);
                    ZLog.d("cookie---qzs   "+cookie);


                }
            }
            ZLog.d("cookie全部-----   " + stringBuilder.toString());
        }

        return originalResponse;

    }
}
