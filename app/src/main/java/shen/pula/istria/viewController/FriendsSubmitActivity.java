package shen.pula.istria.viewController;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import shen.pula.istria.R;
import shen.pula.istria.data_module.DialogData;
import shen.pula.istria.data_source.FriendsSubmitOperation;
import shen.pula.istria.utils.Istria_utils;
import shen.pula.istria.utils.PhotoControl;
import shen.pula.istria.utils.ShenDialog;

import static android.R.attr.id;
import static com.baidu.location.h.j.S;
import static shen.pula.istria.utils.PhotoControl.CAMERA_REQUEST;
import static shen.pula.istria.utils.PhotoControl.PHOTO_REQUEST;

/**
 * Created by dell on 2017/2/9.
 */

public class FriendsSubmitActivity extends AppCompatActivity {
    private LinearLayout selectImage;
    private PhotoControl photoControl;
    private Context mContext;
    private ImageView showPhoto;
    private ShenDialog dialog;
    private EditText et_content;
    private String userName;
    private String imagePath = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_page_submit);
        mContext = this;
        userName =  Istria_utils.getSP_String(this, Istria_utils.KEY_USERID);
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        photoControl = new PhotoControl(this);
        selectImage = (LinearLayout) findViewById(R.id.add_photo);
        showPhoto = (ImageView) findViewById(R.id.show_photo);
        et_content = (EditText)findViewById(R.id.et_content);
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
                    photoControl.getCamera("/DCIM/Istria", "/submit.jpg");
                } else {
                    photoControl.getPhoto();
                }

            }
        };
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ShenDialog();
                dialog.showOnlyContentDialog(mContext, holder, Gravity.CENTER, adapter, itemClickListener, expanded);
            }
        });
        showPhoto.setOnClickListener((View view) ->
        {
            dialog = new ShenDialog();
            dialog.showOnlyContentDialog(mContext, holder, Gravity.CENTER, adapter, itemClickListener, expanded);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_friend_submit, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1://-1表示拍照成功
                        File file = new File(Environment.getExternalStorageDirectory()
                                + "/DCIM/Istria/submit.jpg");//保存图片
                        if (file.exists()) {
                            Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()
                                    + "/DCIM/Istria/submit.jpg", photoControl.getBitmapOption(2));
                            selectImage.setVisibility(View.GONE);
                            dialog.dismiss();
                            showPhoto.setVisibility(View.VISIBLE);
                            showPhoto.setImageBitmap(bitmap);
                            imagePath = Environment.getExternalStorageDirectory()
                                    + "/DCIM/Istria/submit.jpg";
                        }
                }
                break;
            case PHOTO_REQUEST://从相册取
                if (data != null) {
                    Uri uri = data.getData();
                    //对相册取出照片进行裁剪
                    Bitmap bitmap;
                    try {
                        bitmap = photoControl.getBitmapFormUri(FriendsSubmitActivity.this, uri);
                        selectImage.setVisibility(View.GONE);
                        dialog.dismiss();
                        showPhoto.setVisibility(View.VISIBLE);
                        showPhoto.setImageBitmap(bitmap);
                        imagePath = Istria_utils.getImageAbsolutePath(FriendsSubmitActivity.this,uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case android.R.id.home:
                intent.setClass(FriendsSubmitActivity.this, FriendsActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.submit:
                String location = Istria_utils.getSP_String(this,Istria_utils.KEY_USERLOCATION);
                FriendsSubmitOperation.submitFriendQuan(et_content.getText().toString(),userName,imagePath,location);
                intent.setClass(FriendsSubmitActivity.this, FriendsActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
