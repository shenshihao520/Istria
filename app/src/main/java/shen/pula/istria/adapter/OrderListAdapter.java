package shen.pula.istria.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shen.pula.istria.R;
import shen.pula.istria.data_module.GoodsItem;

/**
 * Created by shenshihao520 on 2017/4/18.
 */

public class OrderListAdapter extends BaseAdapter {
    private LayoutInflater mListContainer;
    List<GoodsItem> mData;
    Context mContext;

    public OrderListAdapter(Context context, List<GoodsItem> mGoodsItemList) {
        mListContainer = LayoutInflater.from(context);
        mData = mGoodsItemList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mData.get(i).getGoodsID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ListItemView listItemView = null;
        if (convertView == null) {
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = mListContainer.inflate(R.layout.myorder_item, null);
            //获取控件对象
            listItemView.goodsImage = (ImageView) convertView.findViewById(R.id.goods_image);
            listItemView.goodsTitle = (TextView) convertView.findViewById(R.id.goods_title);
            listItemView.goodsPrice = (TextView) convertView.findViewById(R.id.goods_price);
            listItemView.soldNum = (TextView) convertView.findViewById(R.id.sold_num);

            //设置控件集到convertView
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }
//      Log.e("image", (String) listItems.get(position).get("title"));  //测试
//      Log.e("image", (String) listItems.get(position).get("info"));

        //设置文字和图片
//        listItemView.goodsImage.setBackgroundResource(mData.get(position).getGoodsImage());
        listItemView.goodsTitle.setText(mData.get(position).getGoodsName());
        listItemView.goodsPrice.setText(mData.get(position).getGoodsPrice());
        listItemView.soldNum.setText(mData.get(position).getSoldNum()+"");
        //注册按钮点击时间爱你

        // 注册多选框状态事件处理


        return convertView;
    }
    public  class ListItemView{                //自定义控件集合
        public ImageView goodsImage;
        public TextView goodsTitle;
        public TextView goodsPrice;
        public TextView soldNum;
    }
}

