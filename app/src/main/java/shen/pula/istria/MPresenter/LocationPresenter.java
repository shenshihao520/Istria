package shen.pula.istria.MPresenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

//import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import shen.pula.istria.VPresenter.OperationLocationView;
import shen.pula.istria.utils.MyLocationListener;

/**
 * Created by dell on 2017/2/8.
 */

public class LocationPresenter {
    private Context mContext;
    private OperationLocationView operationLocationView;
    public LocationClient mLocationClient = null;

//    @Inject
    public LocationPresenter (Context context)
    {
        mContext = context;
        initLocation();
    }
    public void addLocationListener(OperationLocationView operationLocationView) {
        this.operationLocationView = operationLocationView;
    }
    public void getLocation()
    {
        mLocationClient.start();
    }
    private void initLocation(){
        mLocationClient = new LocationClient(mContext);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span=1000;
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1)
            {
                Observable.just((String)msg.obj)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((String s) -> operationLocationView.showLocation(s));
            }
        }
    };
    public BDLocationListener  myListener = new MyLocationListener(handler);

}
