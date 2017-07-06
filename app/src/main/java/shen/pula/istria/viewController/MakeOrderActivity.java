package shen.pula.istria.viewController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import shen.pula.istria.R;

/**
 * Created by shenshihao520 on 2017/4/24.
 */

public class MakeOrderActivity extends AppCompatActivity {
    Button btn_make_order;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_order);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_make_order = (Button)findViewById(R.id.btn_submit_order);
        btn_make_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MakeOrderActivity.this,CheckOrderActivity.class);
                Bundle b = new Bundle();
                b.putString("key", "finish");
                //此处使用putExtras，接受方就响应的使用getExtra
                intent.putExtras( b );
                startActivity(intent);
            }
        });
    }
}
