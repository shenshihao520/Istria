package shen.pula.istria;

import android.app.Application;
import android.content.res.Resources;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import shen.pula.istria.utils.HttpHelper;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.webService.ApiService;

/**
 * Created by dell on 2017/2/6.
 */

public class MainApplication extends Application {
    public static Resources resources ;
    @Override
    public void onCreate() {
        super.onCreate();
        resources = this.getResources();
    }

}
