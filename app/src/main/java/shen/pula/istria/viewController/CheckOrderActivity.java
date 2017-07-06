package shen.pula.istria.viewController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import shen.pula.istria.R;

/**
 * Created by shenshihao520 on 2017/4/24.
 */

public class CheckOrderActivity extends AppCompatActivity {
    Button button ;
    String status;
    TextView tv_order_status;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_order);
        initData();
        initView();

    }

    private void initData() {
        status = getIntent().getExtras().getString("key");
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_order_status = (TextView)findViewById(R.id.order_status);
        if(status.equals("finish"))
        {
            tv_order_status.setText(getResources().getString(R.string.finish_order));
        }
        else
        {
            tv_order_status.setText(getResources().getString(R.string.start_send));
        }
        button = (Button)findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CheckOrderActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
