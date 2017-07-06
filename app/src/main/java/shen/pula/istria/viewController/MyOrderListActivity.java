package shen.pula.istria.viewController;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import shen.pula.istria.R;
import shen.pula.istria.adapter.GoodsListAdapter;
import shen.pula.istria.adapter.OrderListAdapter;
import shen.pula.istria.data_module.GoodsItem;

/**
 * Created by shenshihao520 on 2017/5/7.
 */

public class MyOrderListActivity extends AppCompatActivity {
    private ListView mOrderList;
    private OrderListAdapter mOrderListAdapter;
    private List<GoodsItem> mGoodsItemList = new ArrayList<>();
    Context mContext ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder_list);
        initView();
        initData();
    }

    private void initData() {
        mContext = this;
        GoodsItem goodsItem = new GoodsItem();
        goodsItem.setGoodsID(12312312);
        goodsItem.setGoodsName("[3M]3M口罩9001V");
        goodsItem.setGoodsPrice("¥3.80");
        goodsItem.setSoldNum(1);
        mGoodsItemList.add(goodsItem);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mOrderList = (ListView)findViewById(R.id.myorder_list);
        mOrderListAdapter = new OrderListAdapter(this,mGoodsItemList);
        mOrderList.setAdapter(mOrderListAdapter);
        mOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(mContext, CheckOrderActivity.class);
                Bundle b = new Bundle();
                b.putString("key", "check");
                //此处使用putExtras，接受方就响应的使用getExtra
                intent.putExtras(b);
                mContext.startActivity(intent);
            }
        });
    }

}
