package shen.pula.istria.data_source;

import java.util.ArrayList;

import shen.pula.istria.data_module.FriendsSubmit;
import shen.pula.istria.data_module.Pollution;
import shen.pula.istria.data_module.Suggestion_out;
import shen.pula.istria.data_module.User;
import shen.pula.istria.data_module.Weather;
import shen.pula.istria.webService.GetWarningInfoResponse;

/**
 * Created by dell on 2016/11/15.
 */
public class TaskManager {

    TaskDataSource dataSource;

    public TaskManager(TaskDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Weather getWeathers(String city) {
        Weather weather = (Weather) dataSource.getStringFromRemote(city);

        return weather;
    }

    public ArrayList<Weather> getSixDaysWeathers(String city) {
        ArrayList<Weather> weathers = (ArrayList<Weather>) dataSource.getStringFromRemote(city);

        return weathers;
    }

    public Pollution getPollution(String city) {
        Pollution pollution = (Pollution) dataSource.getStringFromRemote(city);

        return pollution;
    }
    public GetWarningInfoResponse getWarning(String city) {
        GetWarningInfoResponse pollution = (GetWarningInfoResponse) dataSource.getStringFromRemote(city);

        return pollution;
    }

    public Suggestion_out getSuggestionDir(String city) {
        Suggestion_out suggestion_out = (Suggestion_out) dataSource.getStringFromRemote(city);
        return suggestion_out;
    }

    public ArrayList<FriendsSubmit> getFriendsSubmit(String date) {
        ArrayList<FriendsSubmit> friendsSubmit = (ArrayList<FriendsSubmit>) dataSource.getStringFromRemote(date);
        return friendsSubmit;
    }

    public User getUser(String userName) {
        User user = (User) dataSource.getStringFromCache(userName);
        return user;
    }

    public void loadFriendsMoreData(ArrayList<FriendsSubmit> mFriendsSubmits) {
        ArrayList<FriendsSubmit> friendsSubmit = (ArrayList<FriendsSubmit>) dataSource.getStringFromRemote(mFriendsSubmits.get(mFriendsSubmits.size() - 1).getiD());
        mFriendsSubmits.addAll(friendsSubmit);
    }
    public User checkLogin(User user)
    {
        User userBack = (User)dataSource.getStringFromRemote(user);
        return userBack;
    }
}
