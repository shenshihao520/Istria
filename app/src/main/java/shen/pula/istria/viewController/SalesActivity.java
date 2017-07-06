package shen.pula.istria.viewController;

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
import shen.pula.istria.data_module.GoodsItem;

/**
 * Created by dell on 2016/12/16.
 */

public class SalesActivity extends AppCompatActivity {
    private ListView mGoodListView;
    private GoodsListAdapter mGoodsListAdapter;
    private List<GoodsItem> mGoodsItemList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_list_activity);
        initView();
        initData();
    }

    private void initData() {
        GoodsItem goodsItem = new GoodsItem();
        goodsItem.setGoodsID(12312312);
        goodsItem.setGoodsName("[3M]3M口罩9001V");
        goodsItem.setGoodsPrice("¥3.80");
        goodsItem.setSoldNum(863);
        mGoodsItemList.add(goodsItem);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mGoodListView = (ListView)findViewById(R.id.goods_list);
        mGoodsListAdapter = new GoodsListAdapter(this,mGoodsItemList);
        mGoodListView.setAdapter(mGoodsListAdapter);
        mGoodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(SalesActivity.this,GoodInformationActivity.class);
                startActivity(intent);
            }
        });
    }
}
