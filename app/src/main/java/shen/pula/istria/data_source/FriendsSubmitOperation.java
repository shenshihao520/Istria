package shen.pula.istria.data_source;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.webService.ApiService;

/**
 * Created by shenshihao on 2017/4/3.
 */

public class FriendsSubmitOperation {

    public static void submitFriendQuan(String content,String userName, String path,String location){
        if(!path.equals(""))
        {
            File file = new File(path);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Istria_Url.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            ApiService apiService = retrofit.create(ApiService.class);

            RequestBody requestBody = new RequestBody() {
                @Override
                public MediaType contentType() {
                    return MediaType.parse("image/*");
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    InputStream is = new FileInputStream(file);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while((len=is.read(bytes))!=-1){
                        sink.write(bytes, 0, len);
                    }
                    is.close();
                }
            };
//            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                    .addFormDataPart("content",content)
//                    .addFormDataPart("userID",userName)
//                    .addFormDataPart("imageType",getImageType(path))
//                    .addFormDataPart("location",location)
//                    .addFormDataPart("file",file.getName(),RequestBody.create(MediaType.parse("image/*"), file)).build();
            Call<ResponseBody> call =
                    apiService.submitFriendQuan_image(content,userName,requestBody,getImageType(path),location);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        }
        else
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Istria_Url.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            ApiService apiService = retrofit.create(ApiService.class);
            Call<ResponseBody> call = apiService.submitFriendQuan(content,userName,location);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                }
            });
        }


    }
    static String getImageType(String path)
    {
        return path.substring(path.lastIndexOf(".")+1);
    }
}
