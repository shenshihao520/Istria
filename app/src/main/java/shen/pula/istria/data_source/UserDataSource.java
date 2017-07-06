package shen.pula.istria.data_source;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import shen.pula.istria.data_module.User;

/**
 * Created by dell on 2017/1/19.
 */

public class UserDataSource implements TaskDataSource{
    @Override
    public Object getStringFromRemote(Object obj) {

        return null;
    }

    @Override
    public Object getStringFromCache(Object obj) {

        User user = new User();
        user.setuName((String) obj);
        Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/DCIM/Istria" + "/hand.jpg");//获得设置路径下图片并编码为Bitmap格式
        user.setUserHead(bm);
        return user;
    }
}
