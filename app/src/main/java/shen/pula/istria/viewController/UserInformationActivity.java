package shen.pula.istria.viewController;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shen.pula.istria.MPresenter.UserPresenter;
import shen.pula.istria.R;
import shen.pula.istria.VPresenter.OperationUserView;
import shen.pula.istria.data_module.DialogData;
import shen.pula.istria.data_module.User;
import shen.pula.istria.utils.DividerUserInformation;
import shen.pula.istria.utils.Istria_utils;
import shen.pula.istria.utils.PhotoControl;
import shen.pula.istria.utils.ShenDialog;

import com.orhanobut.dialogplus.Holder;

import static shen.pula.istria.utils.PhotoControl.CAMERA_REQUEST;
import static shen.pula.istria.utils.PhotoControl.PHOTO_CLIP;
import static shen.pula.istria.utils.PhotoControl.PHOTO_REQUEST;

/**
 * Created by dell on 2017/1/16.
 */

public class UserInformationActivity extends AppCompatActivity implements OperationUserView {
    private RecyclerView mRecyclerView;
    private UserInformationAdapter mAdapter;
    private Context mContext;
    private List<String> mData = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private ImageView mUserIcon;
    private TextView tv_userName;
    private ShenDialog dialog;
    private PhotoControl photoControl;
    private String imageName = "";
    private UserPresenter userPresenter;
    Resources resources;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinformation);
        mContext = this;
        imageName = "User_" + Istria_utils.getSP_String(this, Istria_utils.KEY_USERNAME);
        resources = getResources();
        initData();
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUserIcon = (ImageView) findViewById(R.id.userIcon);

        tv_userName = (TextView) findViewById(R.id.userEmail_tel);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        mAdapter = new UserInformationAdapter(mContext, mData);
        mAdapter.setOnItemClickListener(new UserInformationAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view) {
                TextView uf_deception = (TextView) view.findViewById(R.id.uf_deception);
                TextView uf_title = (TextView) view.findViewById(R.id.uf_title);

                if (uf_deception != null)
                    if (uf_deception.getText().toString().equals(resources.getString(R.string.account_secret_key_d))) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, SettingActivity.class);
                        mContext.startActivity(intent);
                    } else if (uf_deception.getText().toString().equals(resources.getString(R.string.security_setting_d))) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, MyOrderListActivity.class);
                        mContext.startActivity(intent);
                    }
                    else if (uf_deception.getText().toString().equals(resources.getString(R.string.self_information_d))) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, SaveUserInfoActivity.class);
                        mContext.startActivity(intent);
                    }

                if (uf_title != null)
                    if (uf_title.getText().toString().equals(resources.getString(R.string.account_secret_key))) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, SettingActivity.class);
                        mContext.startActivity(intent);
                    } else if (uf_title.getText().toString().equals(resources.getString(R.string.security_setting))) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, MyOrderListActivity.class);
                        mContext.startActivity(intent);
                    }
                    else if (uf_title.getText().toString().equals(resources.getString(R.string.self_information))) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, SaveUserInfoActivity.class);
                        mContext.startActivity(intent);
                    }

            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerUserInformation(this,             //分割线
                DividerUserInformation.VERTICAL_LIST));
        photoControl = new PhotoControl(UserInformationActivity.this);


        File file = new File(Environment.getExternalStorageDirectory() + "/hand.jpg");//获取图片
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/hand.jpg");//获得设置路径下图片并编码为Bitmap格式
            mUserIcon.setImageBitmap(bm);//设置图片为背景图
        }
        initDialog();
    }

    void initDialog() {
        Holder holder = new ListHolder();
        boolean isGrid = false;
        boolean expanded = false;
        ArrayList<DialogData> data = new ArrayList<>();
        DialogData dialogData = new DialogData();
        dialogData.setImageID(R.mipmap.camera_normal);
        dialogData.setTitle(this.getString(R.string.camera));
        data.add(dialogData);
        DialogData dialogData2 = new DialogData();
        dialogData2.setImageID(R.mipmap.gallery_normal);
        dialogData2.setTitle(this.getString(R.string.gallery));
        data.add(dialogData2);
        DialogAdapter adapter = new DialogAdapter(this, isGrid, data);


        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                if (position == 0) {
                    photoControl.getCamera("/DCIM/Istria", "/hand.jpg");
                } else {
                    photoControl.getPhoto();
                }

            }
        };
        mUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ShenDialog();
                dialog.showOnlyContentDialog(mContext, holder, Gravity.CENTER, adapter, itemClickListener, expanded);
            }
        });
    }

    private void initData() {
        userPresenter = new UserPresenter();
        userPresenter.addUserListener(this);

        mData.add(resources.getString(R.string.self_information));
        mData.add(resources.getString(R.string.self_information_d));
        mData.add(resources.getString(R.string.account_security));
        mData.add(resources.getString(R.string.account_secret_key));
        mData.add(resources.getString(R.string.account_secret_key_d));
        mData.add(resources.getString(R.string.security_setting));
        mData.add(resources.getString(R.string.security_setting_d));
//        mData.add(resources.getString(R.string.recent_activity));
//        mData.add(resources.getString(R.string.recent_activity_d));
    }

    @Override
    protected void onResume() {
        super.onResume();
        String userName = Istria_utils.getSP_String(this, Istria_utils.KEY_USERNAME);
        if (!userName.equals("")) {
            userPresenter.getUserAndShow(userName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.userinformation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File file = new File(Environment.getExternalStorageDirectory()
//                                + "/"+imageName+".jpg");//保存图片
                                + "/hand.jpg");//保存图片
                        if (file.exists()) {
                            //对相机拍照照片进行裁剪
                            photoControl.photoClip(Uri.fromFile(file));
                        }
                }
                break;
            case PHOTO_REQUEST://从相册取
                if (data != null) {
                    Uri uri = data.getData();
                    //对相册取出照片进行裁剪
                    photoControl.photoClip(uri);

                }
                break;
            case PHOTO_CLIP:
                //完成
                if (data != null) {

                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        try {
                            //获得图片路径
                            File filepath = photoControl.saveFile(photo, Environment.getExternalStorageDirectory().toString(), "/hand.jpg");
                            //上传照片
                            PhotoControl.uploadImage(imageName, filepath);
                            dialog.dismiss();
                            mUserIcon.setImageBitmap(photo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //上传完成将照片写入imageview与用户进行交互

                    }
                }
                break;
        }
    }

    @Override
    public void showUser(User user) {
        Message msg = new Message();
        msg.what = 5;
        msg.obj = user;
        mHandler.sendMessage(msg);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 5:
                    User user = (User) msg.obj;
                    if (user != null) {
                        tv_userName.setText(user.getuName());
                        mUserIcon.setImageBitmap(user.getUserHead());//设置图片为背景图
                    }
            }
        }
    };
}
