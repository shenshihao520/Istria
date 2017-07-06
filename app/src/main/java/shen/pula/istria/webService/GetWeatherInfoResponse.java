package shen.pula.istria.webService;

import shen.pula.istria.data_module.Weather;
import shen.pula.istria.webService.BaseResponse;

/**
 * Created by dell on 2017/1/10.
 */
public class GetWeatherInfoResponse extends BaseResponse {
    public String location;
    public String image;
    public int temperature_high;
    public int temperature_low;
    public int temperature_now;
    public String weather_day;
}
