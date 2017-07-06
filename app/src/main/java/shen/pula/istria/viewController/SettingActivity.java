package shen.pula.istria.viewController;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import shen.pula.istria.R;
import shen.pula.istria.utils.HttpHelper;
import shen.pula.istria.utils.Istria_utils;

/**
 * Created by dell on 2017/1/24.
 */

public class SettingActivity  extends AppCompatActivity {
    private Button btn_logout;
    private Context mContext;
    private EditText et_newPassword;
    private EditText et_oldPassword;
    private Button btn_change_password;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mContext = this;
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        et_newPassword = (EditText)findViewById(R.id.new_password);
        et_oldPassword = (EditText)findViewById(R.id.old_password);
        btn_change_password = (Button)findViewById(R.id.set_new_password);
        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call<String> call = HttpHelper.getInstance().apiService.changePassword(Istria_utils.getSP_String(mContext,Istria_utils.KEY_USERID),et_oldPassword.getText().toString(),et_newPassword.getText().toString());
                        try {
                            String back = call.execute().body();
                            changeSuccess(back);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Istria_utils.putSP(mContext,Istria_utils.KEY_USERID,"");
                Istria_utils.putSP(mContext,Istria_utils.KEY_USERNAME,"");
                Intent intent = new Intent();
                intent.setClass(mContext,MainActivity.class);
                mContext.startActivity(intent);
                finish();
            }
        });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj .equals("true"))
            {
                Toast.makeText(mContext,"Change Success!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(mContext,"Change Fail!!",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void changeSuccess(String s) {
        Message msg = new Message();
        msg.obj = s;
        handler.sendMessage(msg);
    }
}
