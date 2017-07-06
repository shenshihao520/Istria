package shen.pula.istria.data_module;

import java.util.ArrayList;

/**
 * Created by dell on 2016/11/1.
 */
public class WeatherFactory {
    private ArrayList<Weather> weathersList = new ArrayList<>();


    public void addWeather(Weather weather) {
        weathersList.add(weather);
    }

    public void removeWeather(Weather weather) {
        weathersList.remove(weather);
    }

    public void removeWeather(int index) {
        if (index >= 0 && index < weathersList.size()) {
            weathersList.remove(index);
        }
    }

//    public void createWeather(String weatherName, double temperature) {
//        Weather weather = new Weather(weatherName, temperature);
//        weathersList.add(weather);
//    }

    public ArrayList<Weather> getWeathersList() {
        return weathersList;
    }

    public int getWeatherCounts() {
        return weathersList.size();
    }

}
