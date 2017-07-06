package shen.pula.istria.data_source;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import shen.pula.istria.R;
import shen.pula.istria.data_module.Weather;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.webService.ApiService;
import shen.pula.istria.webService.GetWeatherInfoResponse;

/**
 * Created by dell on 2016/11/15.
 */
public class WeatherDataSource_today implements TaskDataSource {

    @Override
    public Object getStringFromRemote(Object object) {
        Weather weather = new Weather();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Istria_Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<GetWeatherInfoResponse> call = apiService.getWeatherToday("Beijing");
        try {
            GetWeatherInfoResponse getWeatherInfoResponse= call.execute().body();
            weather.setTemperature_high(getWeatherInfoResponse.temperature_high);
            weather.setTemperature_low(getWeatherInfoResponse.temperature_low);
            weather.setTemperature_now(getWeatherInfoResponse.temperature_now);
            weather.setWeather_day(getWeatherInfoResponse.weather_day);
            weather.setImage(getWeatherInfoResponse.image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weather;
    }

    @Override
    public Object getStringFromCache(Object obj) {
        return null;
    }
}
