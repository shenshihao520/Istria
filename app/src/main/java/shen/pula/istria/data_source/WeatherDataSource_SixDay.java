package shen.pula.istria.data_source;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import shen.pula.istria.data_module.Weather;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.webService.ApiService;

/**
 * Created by dell on 2016/12/23.
 */

public class WeatherDataSource_SixDay implements TaskDataSource{
    @Override
    public Object getStringFromRemote(Object obj) {
        ArrayList<Weather> weathers = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Istria_Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<Weather>> call = apiService.getWeatherSixDays("Beijing");
        try {
            ArrayList<Weather> getWeatherSixDayInfoResponse= call.execute().body();
            weathers = getWeatherSixDayInfoResponse;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return weathers;
    }

    @Override
    public Object getStringFromCache(Object obj) {
        return null;
    }
}
