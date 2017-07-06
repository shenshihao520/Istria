package shen.pula.istria.data_module;

/**
 * Created by shenshihao520 on 2017/5/3.
 */

public class GoodsInformation extends GoodsItem{
    private String moreInformation;
    private String postPrice;

    public String getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(String moreInformation) {
        this.moreInformation = moreInformation;
    }

    public String getPostPrice() {
        return postPrice;
    }

    public void setPostPrice(String postPrice) {
        this.postPrice = postPrice;
    }
}
