package shen.pula.istria.data_module;

/**
 * Created by dell on 2016/11/1.
 */
public class Weather {
    private String weather_day;
    private int temperature_high;
    private int temperature_now;
    private int temperature_low;
    private String date;
    private String location;
    private String image;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTemperature_low() {
        return temperature_low;
    }

    public void setTemperature_low(int temperature_low) {
        this.temperature_low = temperature_low;
    }
    public int getTemperature_now() {
        return temperature_now;
    }

    public void setTemperature_now(int temperature_now) {
        this.temperature_now = temperature_now;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather_day() {
        return weather_day;
    }

    public void setWeather_day(String weather_day) {
        this.weather_day = weather_day;
    }

    public int getTemperature_high() {
        return temperature_high;
    }

    public void setTemperature_high(int temperature_high) {
        this.temperature_high = temperature_high;
    }


}
