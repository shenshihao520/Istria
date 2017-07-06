package shen.pula.istria.viewController;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import shen.pula.istria.R;
import shen.pula.istria.utils.HttpHelper;
import shen.pula.istria.utils.Istria_utils;

import static cn.smssdk.SMSSDK.getVerificationCode;
import static cn.smssdk.SMSSDK.submitVerificationCode;

/**
 * Created by dell on 2016/12/22.
 */

public class RegisterFragment extends Fragment {
    EditText et_tel ;
    EditText et_code ;
    Button btn_sendCode;
    Button btn_register;
    CountDownTimer countDownTimer ;
    Context context ;
    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        context = getActivity();
        initView(view);
        sendMsg();
        return view;
    }
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1)
                Toast.makeText(getActivity(), (String)msg.obj, Toast.LENGTH_SHORT).show();
            if(msg.what == 2)
                Toast.makeText(getActivity(), (String)msg.obj, Toast.LENGTH_SHORT).show();
        }
    };
    private void initView(View view) {
        et_tel = (EditText)view.findViewById(R.id.et_tel);
        et_code = (EditText)view.findViewById(R.id.et_code);
        btn_sendCode = (Button)view.findViewById(R.id.sendCode);
        btn_register = (Button)view.findViewById(R.id.btn_register);

        btn_sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_tel.getText().toString().length() == 11)
                {
                    getVerificationCode("86",et_tel.getText().toString());
                    countDownTimer.start();
                }
                else
                {
                    Toast.makeText(context,getResources().getString(R.string.login_hint_error),Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitVerificationCode("86",et_tel.getText().toString(),et_code.getText().toString());
            }
        });
    }
    void sendMsg()
    {
        SMSSDK.initSDK(getActivity(), "1b1f4a22a40a5", "9be12884de8eb7fa788978e41f1e566c");
        EventHandler eh=new EventHandler(){

            @Override
            public void afterEvent(int event, int result, Object data) {

                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        HttpHelper.getInstance().apiService.registerUser(et_tel.getText().toString())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe((String s)->registerSuccess(s));
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功

                    }
                }else{
                    Throwable throwable = (Throwable) data;
                    JSONObject object = null;
                    try {
                        object = new JSONObject(throwable.getMessage());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String des = object.optString("detail");
                    int status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Message msg = new Message();
                        msg.obj = des;
                        msg.what = 1;
                        handler.sendMessage(msg);
                        countDownTimer.onFinish();
                    }
                }
            }
        };

        SMSSDK.registerEventHandler(eh); //注册短信回调
        countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //防止计时过程中重复点击
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_sendCode.setClickable(false);
                        btn_sendCode.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
                        btn_sendCode.setText(millisUntilFinished/1000 + getActivity().getResources().getString(R.string.second));
                    }
                });

            }

            @Override
            public void onFinish() {
                //重新给Button设置文字
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn_sendCode.setText(getActivity().getResources().getString(R.string.resend_msg));
                        //设置可点击
                        btn_sendCode.setClickable(true);
                        btn_sendCode.setBackgroundColor(getActivity().getResources().getColor(R.color.lite_blue));
                    }
                });


            }
        };
    }
    void registerSuccess(String result)
    {
        if (!result.equals(""))
        {
            File dirFile = new File(Environment.getExternalStorageDirectory() + "/hand.jpg");
            if(dirFile.exists())
                dirFile.delete();
            Istria_utils.putSP(getActivity(),Istria_utils.KEY_USERID,et_tel.getText().toString());
            Istria_utils.putSP(getActivity(),Istria_utils.KEY_USERNAME,result);
            Intent intent = new Intent();
            intent.setClass(getActivity(),MainActivity.class);
            startActivity(intent);
        }
        else
        {
            Message msg = new Message();
            msg.what = 2;
            msg.obj = getActivity().getResources().getString(R.string.register_fail);
            handler.sendMessage(msg);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
