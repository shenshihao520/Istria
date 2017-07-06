package shen.pula.istria.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import shen.pula.istria.webService.ApiService;

/**
 * Created by dell on 2017/2/7.
 */

public class HttpHelper {
    private Retrofit retrofit;
    public ApiService apiService;
    private static final int DEFAULT_TIMEOUT = 20;
    public  HttpHelper()
    {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
         retrofit = new Retrofit.Builder()
                .baseUrl(Istria_Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
         apiService = retrofit.create(ApiService.class);
    }
    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpHelper INSTANCE = new HttpHelper();
    }

    //获取单例
    public static HttpHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
