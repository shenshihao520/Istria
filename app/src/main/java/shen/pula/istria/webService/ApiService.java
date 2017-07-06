package shen.pula.istria.webService;

import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import shen.pula.istria.data_module.FriendsSubmit;
import shen.pula.istria.data_module.Weather;

/**
 * Created by dell on 2017/1/10.
 */
public interface ApiService {
    @GET("weather?action=weather_today")
    Call<GetWeatherInfoResponse> getWeatherToday(@Query("location") String location);
    @GET("pollution?action=pollution_today")
    Call<GetPollutionInfoResponse> getPollutionToday(@Query("location") String location);
    @GET("suggestion?action=suggestion_out")
    Call<GetSuggestionInfoResponse> getSuggestionOut(@Query("location") String location);
    @GET("weather?action=weather_sixdays")
    Call<ArrayList<Weather>> getWeatherSixDays(@Query("location") String location);
    @GET("user?action=login")
    Call<GetUserInfoResponse> checkUser(@Query("userID") String name, @Query("password") String password);
    @POST("user?action=register")
    Observable<String> registerUser(@Query("userID") String name);
    @POST("upload_image?action=user_head")
    Call<ResponseBody> uploadImage(@Query("fileName") String description, @Body RequestBody body);
//
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})

    @POST("friend_submit?action=friends_quan_image")
    Call<ResponseBody> submitFriendQuan_image(@Query("content") String description, @Query("userID") String userName, @Body RequestBody body,@Query("imageType") String imageType,@Query("location") String location);

//    @POST("friend_submit?action=friends_quan_image")
//    Call<ResponseBody> upload(@Body RequestBody Body);
    @POST("user?action=suggestion")
    Call<String> sendSuggestion(@Query("userID") String userID,@Query("content") String content);

    @POST("user?action=userInformation")
    Call<String> changeUserInformation(@Query("userID") String userID,@Query("name") String name,@Query("age") String age);

    @POST("user?action=change_password")
    Call<String> changePassword(@Query("userID") String userID,@Query("oldPassword") String oldPassword,@Query("newPassword") String newPassword);

    @GET("pollution?action=warning")
    Call<GetWarningInfoResponse> getPollutionWarning(@Query("location") String location);

    @FormUrlEncoded
    @POST("friend_submit?action=friends_quan")
    Call<ResponseBody> submitFriendQuan(@Field("content") String description, @Field("userID") String userName, @Field("location") String location);

    @GET("friend_submit?action=pull")
    Call<ArrayList<FriendsSubmit>> pullFriendQuanData(@Query("time")String time);
}