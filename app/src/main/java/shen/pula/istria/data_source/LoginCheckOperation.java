package shen.pula.istria.data_source;

import android.os.Message;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import shen.pula.istria.data_module.User;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.webService.ApiService;
import shen.pula.istria.webService.GetUserInfoResponse;

import static shen.pula.istria.R.id.et_passWord;
import static shen.pula.istria.R.id.et_tel;

/**
 * Created by shenshihao on 2017/4/2.
 */

public class LoginCheckOperation implements TaskDataSource{
    @Override
    public Object getStringFromRemote(Object obj) {
        User post = new User();
        post = (User) obj;
        User user = new User();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Istria_Url.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<GetUserInfoResponse> call = apiService.checkUser(post.getuID(), post.getPassWord());
        try {
            GetUserInfoResponse response = call.execute().body();
            user.setuName(response.userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public Object getStringFromCache(Object obj) {
        return null;
    }
}
