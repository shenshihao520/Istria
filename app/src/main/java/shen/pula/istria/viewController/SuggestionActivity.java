package shen.pula.istria.viewController;

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
import shen.pula.istria.R;
import shen.pula.istria.utils.HttpHelper;
import shen.pula.istria.utils.Istria_utils;

/**
 * Created by shenshihao520 on 2017/5/18.
 */

public class SuggestionActivity extends AppCompatActivity {
    EditText et_suggestion ;
    Button btn_submit_suggestion;
    Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion_activity);
        mContext = this;
//        initData();
        initView();


    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        et_suggestion = (EditText)findViewById(R.id.et_suggestion);
        btn_submit_suggestion = (Button)findViewById(R.id.btn_submit_suggestion);
        btn_submit_suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Istria_utils.getSP_String(mContext,Istria_utils.KEY_USERID).equals(""))
                {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Call<String> call = HttpHelper.getInstance().apiService.sendSuggestion(Istria_utils.getSP_String(mContext,Istria_utils.KEY_USERID),et_suggestion.getText().toString());
                            try {
                                String back = call.execute().body();
                                sendSuggestion(back);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                else
                {
                    Intent intent = new Intent();
                    intent.setClass(SuggestionActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj .equals("true"))
            {
                Toast.makeText(mContext,"Send Success!!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(SuggestionActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(mContext,"Send Fail!!",Toast.LENGTH_SHORT).show();
            }
        }
    };
    private void sendSuggestion(String s) {
        Message msg = new Message();
        msg.obj = s;
        handler.sendMessage(msg);
    }
//    private void initData() {
//
//    }
}
