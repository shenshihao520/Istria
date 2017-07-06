package shen.pula.istria.data_source;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import shen.pula.istria.data_module.Pollution;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.webService.ApiService;
import shen.pula.istria.webService.GetPollutionInfoResponse;
import shen.pula.istria.webService.GetWeatherInfoResponse;

/**
 * Created by dell on 2016/12/21.
 */

public class PollutionDataSource implements TaskDataSource {
    @Override
    public Object getStringFromRemote(Object obj) {
        Pollution pollution = new Pollution();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Istria_Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<GetPollutionInfoResponse> call = apiService.getPollutionToday("Beijing");
        try {
            GetPollutionInfoResponse getPollutionInfoResponse= call.execute().body();
            pollution.setAir_quality(getPollutionInfoResponse.air_quality);
            pollution.setPm_25(getPollutionInfoResponse.pm_25);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pollution;
    }

    @Override
    public Object getStringFromCache(Object obj) {
        return null;
    }
}
