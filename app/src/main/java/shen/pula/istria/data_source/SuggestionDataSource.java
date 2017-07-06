package shen.pula.istria.data_source;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import shen.pula.istria.data_module.Pollution;
import shen.pula.istria.data_module.Suggestion_out;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.webService.ApiService;
import shen.pula.istria.webService.GetPollutionInfoResponse;
import shen.pula.istria.webService.GetSuggestionInfoResponse;

/**
 * Created by dell on 2016/12/26.
 */

public class SuggestionDataSource implements TaskDataSource {
    @Override
    public Object getStringFromRemote(Object obj) {
        Suggestion_out suggestion_out = new Suggestion_out();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Istria_Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<GetSuggestionInfoResponse> call = apiService.getSuggestionOut("Beijing");
        try {
            GetSuggestionInfoResponse getSuggestionInfoResponse= call.execute().body();
            suggestion_out.setWindLevel(getSuggestionInfoResponse.windLevel);
            suggestion_out.setWindDir(getSuggestionInfoResponse.windDir);
            suggestion_out.setHumidity(getSuggestionInfoResponse.humidity);
            suggestion_out.setClothLevel(getSuggestionInfoResponse.clothLevel);
            suggestion_out.setCarWashLevel(getSuggestionInfoResponse.carWashLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suggestion_out;
    }

    @Override
    public Object getStringFromCache(Object obj) {
        return null;
    }
}
