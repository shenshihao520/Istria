package shen.pula.istria.viewController;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import shen.pula.istria.MPresenter.PollutionPresenter;
import shen.pula.istria.R;
import shen.pula.istria.VPresenter.OperationWaningView;
import shen.pula.istria.webService.GetWarningInfoResponse;

/**
 * Created by shenshihao520 on 2017/4/24.
 */

public class WarningActivity extends AppCompatActivity implements OperationWaningView{
    PollutionPresenter pollutionPresenter = new PollutionPresenter();
    TextView tv_PM25_M;
    TextView tv_airqulity_M;
    TextView tv_kouzhao_M;
    TextView tv_xiaotieshi;
    ImageView im_mask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warning_activity);
        initView();
        initData();
    }

    private void initData() {
        pollutionPresenter.addWarningListener(this);
        pollutionPresenter.getWarningDataAndShow();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_PM25_M = (TextView) findViewById(R.id.PM25_M);
        tv_airqulity_M = (TextView) findViewById(R.id.airqulity_M);
        tv_kouzhao_M = (TextView) findViewById(R.id.kouzhao_M);
        tv_xiaotieshi = (TextView) findViewById(R.id.xiaotieshi);
        im_mask = (ImageView) findViewById(R.id.im_mask);

    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GetWarningInfoResponse getWarningInfoResponse = (GetWarningInfoResponse) msg.obj;
            tv_PM25_M.setText(getWarningInfoResponse.pm_25+"");
            tv_xiaotieshi.setText(getWarningInfoResponse.suggestion);
            tv_kouzhao_M.setText(getWarningInfoResponse.mask);
            tv_airqulity_M.setText(getWarningInfoResponse.air_quality);
        }
    };
    @Override
    public void showWarning(GetWarningInfoResponse getWarningInfoResponse) {

        Message message = new Message();
        message.obj = getWarningInfoResponse;
        mHandler.sendMessage(message);

    }
}
