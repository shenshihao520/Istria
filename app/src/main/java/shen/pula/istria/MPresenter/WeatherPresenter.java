package shen.pula.istria.MPresenter;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import shen.pula.istria.VPresenter.OperationMainView;
import shen.pula.istria.data_source.TaskManager;
import shen.pula.istria.data_module.Weather;
import shen.pula.istria.data_source.WeatherDataSource_SixDay;
import shen.pula.istria.data_source.WeatherDataSource_today;

/**
 * Created by dell on 2016/11/1.
 */
public class WeatherPresenter{

    OperationMainView operationMainView;
    TaskManager weatherData;
    TaskManager weatherDataSix;
    String city = "beijing";
    public WeatherPresenter() {
        this.weatherData = new TaskManager(new WeatherDataSource_today());
        this.weatherDataSix = new TaskManager(new WeatherDataSource_SixDay());
    }
    public void addWeatherListener(OperationMainView operationMainView)
    {
        this.operationMainView = operationMainView;
    }
    public void getWeatherDataAndShow() {

        //data操作
        Func1 dataAction = new Func1<String, Weather>() {
            @Override
            public Weather call(String date) {
                return  weatherData.getWeathers(date);
            }
        };
        //view操作
        Action1 viewAction = new Action1<Weather>() {
            @Override
            public void call(Weather weathers) {
                operationMainView.showWeatherToday(weathers);
            }
        };

        Observable.just(city)
                .subscribeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewAction);

    }
    public void getSixDaysAndShow()
    {
        Observable.just(city)
                .subscribeOn(Schedulers.io())
                .map(s -> weatherDataSix.getSixDaysWeathers(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( (ArrayList<Weather> weathers) -> operationMainView.showSixDaysWeatherChange(weathers));
    }



    /**
     * Test方法可以进行说教保留
     */
    public void rxJavaTest()
    {
        Observable<String> myObservable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {
                        sub.onNext("Hello, world!");
                        sub.onCompleted();
                    }
                }
        );
        /**
         * 详细版subscriber
         */
        Subscriber<String> mySubscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) { System.out.println(s); }

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) { }
        };

        myObservable.subscribe(mySubscriber);

        /**
         * 简化版  就是只重写了onNext()方法，其他默认空
         */
        Observable.just("Hello, world!").subscribe(s -> System.out.println(s)); //应用lambda表达式
    }

}
