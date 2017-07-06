package shen.pula.istria.data_source;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import shen.pula.istria.data_module.FriendsSubmit;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.webService.ApiService;

/**
 * Created by dell on 2016/12/30.
 */

public class FriendsSubmitDataSource implements TaskDataSource {
    @Override
    public Object getStringFromRemote(Object obj) {
        if (obj.getClass() == String.class) {
            List<FriendsSubmit> friendsSubmits = null;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Istria_Url.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            ApiService apiService = retrofit.create(ApiService.class);

          Call<ArrayList<FriendsSubmit>> call = apiService.pullFriendQuanData("12:12");
            try {
                friendsSubmits = call.execute().body();

                return friendsSubmits;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return friendsSubmits;
        } else if (obj.getClass() == Integer.class) {
            List<FriendsSubmit> friendsSubmits = new ArrayList<FriendsSubmit>();
//            for(int i = 0;i < 10 ;i++)
            {

            }
            return friendsSubmits;
        }
        return null;
    }

    @Override
    public Object getStringFromCache(Object obj) {
        return null;
    }
}
