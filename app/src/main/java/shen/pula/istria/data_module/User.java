package shen.pula.istria.data_module;

import android.graphics.Bitmap;

/**
 * Created by dell on 2016/12/29.
 */

public class User {
    private String uID = "";
    private String uName = "";
    private String passWord = "";
    private Bitmap userHead;

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }
    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Bitmap getUserHead() {
        return userHead;
    }

    public void setUserHead(Bitmap userHead) {
        this.userHead = userHead;
    }
}
