package shen.pula.istria.viewController;

import android.content.Context;
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
import shen.pula.istria.R;
import shen.pula.istria.utils.HttpHelper;
import shen.pula.istria.utils.Istria_utils;

/**
 * Created by shenshihao520 on 2017/5/18.
 */

public class SaveUserInfoActivity  extends AppCompatActivity {
    Button btn_save;
    EditText et_name;
    EditText et_ege;
    Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_userinfo_activity);
        mContext = this;
        initData();
        initView();

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_save = (Button) findViewById(R.id.btn_save);
        et_name = (EditText)findViewById(R.id.et_name);
        et_ege = (EditText)findViewById(R.id.et_ege);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call<String> call = HttpHelper.getInstance().apiService.changeUserInformation(Istria_utils.getSP_String(mContext,Istria_utils.KEY_USERID),et_name.getText().toString(),et_ege.getText().toString());
                        try {
                            String back = call.execute().body();
                            changeUserInformation(back);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
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
    private void changeUserInformation(String s) {
        Message msg = new Message();
        msg.obj = s;
        handler.sendMessage(msg);
    }
    private void initData() {

    }
}
