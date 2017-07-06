package shen.pula.istria.data_module;

import java.util.ArrayList;

/**
 * Created by shenshihao520 on 2017/5/3.
 */

public class Order {
    private PostAddress postAddress;
    private ArrayList<String> postLineList;
    private ArrayList<GoodsItem> goodsItemList;

    public PostAddress getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(PostAddress postAddress) {
        this.postAddress = postAddress;
    }

    public ArrayList<String> getPostLineList() {
        return postLineList;
    }

    public void setPostLineList(ArrayList<String> postLineList) {
        this.postLineList = postLineList;
    }

    public ArrayList<GoodsItem> getGoodsItemList() {
        return goodsItemList;
    }

    public void setGoodsItemList(ArrayList<GoodsItem> goodsItemList) {
        this.goodsItemList = goodsItemList;
    }
}
