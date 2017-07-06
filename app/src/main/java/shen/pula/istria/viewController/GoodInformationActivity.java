package shen.pula.istria.viewController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import shen.pula.istria.R;

/**
 * Created by shenshihao520 on 2017/4/24.
 */

public class GoodInformationActivity extends AppCompatActivity {
    Button btn_buy;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_information);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_buy = (Button)findViewById(R.id.btn_buy_right_now);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(GoodInformationActivity.this,MakeOrderActivity.class);
                startActivity(intent);
            }
        });
    }

}
