package shen.pula.istria.VPresenter;

import java.util.ArrayList;

import shen.pula.istria.data_module.Suggestion_out;
import shen.pula.istria.data_module.Weather;

/**
 * Created by dell on 2016/11/1.
 * 对主页面进行改变
 */
public interface OperationMainView {

    void showWeatherToday(Weather weather);

    void showSixDaysWeatherChange(ArrayList<Weather> weathers);


}
