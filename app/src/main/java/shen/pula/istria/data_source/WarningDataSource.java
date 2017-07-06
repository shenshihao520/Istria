package shen.pula.istria.data_source;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.webService.ApiService;
import shen.pula.istria.webService.GetPollutionInfoResponse;
import shen.pula.istria.webService.GetWarningInfoResponse;

/**
 * Created by shenshihao520 on 2017/5/22.
 */

public class WarningDataSource implements TaskDataSource{
    @Override
    public Object getStringFromRemote(Object obj) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Istria_Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        GetWarningInfoResponse getPollutionInfoResponse = null;
        Call<GetWarningInfoResponse> call = apiService.getPollutionWarning((String) obj);
        try {
             getPollutionInfoResponse= call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return getPollutionInfoResponse;
    }

    @Override
    public Object getStringFromCache(Object obj) {
        return null;
    }
}
