package shen.pula.istria.data_source;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shen.pula.istria.MainApplication;
import shen.pula.istria.R;

/**
 * Created by dell on 2017/2/8.
 */

public class LocationDataSource {
    Map<String,String> cityList = new HashMap<>();
    public Map<String,String> LocationDataSource()
    {
        Resources resources= MainApplication.resources ;
        cityList.put("Beijing",resources.getString(R.string.Beijing));
        cityList.put("Shanghai",resources.getString(R.string.Shanghai));
        cityList.put("Guangzhou",resources.getString(R.string.Guangzhou));
        return cityList;
    }
}
