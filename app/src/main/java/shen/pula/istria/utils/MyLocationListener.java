package shen.pula.istria.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;

import java.util.List;

/**
 * Created by dell on 2017/2/8.
 */

public class MyLocationListener implements BDLocationListener {

    Handler mHandler;
    public MyLocationListener(Handler handler) {
        super();
        mHandler = handler;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        //Receive Location
       if (location.getLocType() == BDLocation.TypeServerError) {
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
        }
        else
       {
           Message msg = new Message();
           msg.what = 1;
           msg.obj = location.getAddrStr();
           mHandler.sendMessage(msg);
       }
    }
}
