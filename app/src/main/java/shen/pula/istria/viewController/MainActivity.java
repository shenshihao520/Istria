package shen.pula.istria.viewController;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushSettings;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ListHolder;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import shen.pula.istria.MPresenter.LocationPresenter;
import shen.pula.istria.MPresenter.PollutionPresenter;
import shen.pula.istria.MPresenter.UserPresenter;
import shen.pula.istria.MPresenter.WeatherPresenter;
import shen.pula.istria.R;
import shen.pula.istria.VPresenter.OperationLocationView;
import shen.pula.istria.VPresenter.OperationMainView;
import shen.pula.istria.VPresenter.OperationPollutionView;
import shen.pula.istria.VPresenter.OperationUserView;
import shen.pula.istria.data_module.DialogData;
import shen.pula.istria.data_module.Pollution;
import shen.pula.istria.data_module.Suggestion_out;
import shen.pula.istria.data_module.User;
import shen.pula.istria.data_module.Weather;
import shen.pula.istria.utils.Date_I;
import shen.pula.istria.utils.Istria_Url;
import shen.pula.istria.utils.Istria_utils;
import shen.pula.istria.utils.ShenDialog;
import shen.pula.istria.utils.Utils;
import shen.pula.istria.view.MyScrollView;

//import shen.pula.istria.dagger.AppModule;

public class MainActivity extends AppCompatActivity implements OperationMainView, OperationPollutionView, NavigationView.OnNavigationItemSelectedListener, OperationUserView,OperationLocationView{

    public static int MAINACTIVITY_CODE = 1;

    private Toast toast;
    private TextView tv_weather_date;
    private TextView tv_temperature_low;
    private TextView tv_temperature_high;
    private TextView tv_temperature_now;
    private TextView tv_air_quality;
    private TextView tv_pm_25;

    private TextView tv_weather;
    private TextView tv_precip;
    private TextView tv_wind_scale;
    private TextView tv_car_level;
    private TextView cloth_level;
    private TextView tv_wind_level;
    private ImageView frameAnim;
    private ImageView im_weather_now;
    private WeatherPresenter weatherPresenter;
    private PollutionPresenter pollutionPresenter;
    private UserPresenter userPresenter;
    private LocationPresenter locationPresenter;
    private ArrayList<Weather> weathersList = new ArrayList<>();
    private MyScrollView scrollView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int alpha = 0;    //头图透明度
    private DrawerLayout drawer;
    private Context context;
    private LinearLayout ln_login;


    private TextView moreWeather_date1;
    private TextView moreWeather_dateText1;
    private ImageView moreWeather_image1;
    private TextView moreWeather_highT_Text1;
    private TextView moreWeather_lowT_Text1;

    private TextView moreWeather_date2;
    private TextView moreWeather_dateText2;
    private ImageView moreWeather_image2;
    private TextView moreWeather_highT_Text2;
    private TextView moreWeather_lowT_Text2;

    private TextView moreWeather_date3;
    private TextView moreWeather_dateText3;
    private ImageView moreWeather_image3;
    private TextView moreWeather_highT_Text3;
    private TextView moreWeather_lowT_Text3;

    private TextView moreWeather_date4;
    private TextView moreWeather_dateText4;
    private ImageView moreWeather_image4;
    private TextView moreWeather_highT_Text4;
    private TextView moreWeather_lowT_Text4;

    private TextView moreWeather_date5;
    private TextView moreWeather_dateText5;
    private ImageView moreWeather_image5;
    private TextView moreWeather_highT_Text5;
    private TextView moreWeather_lowT_Text5;

    private TextView moreWeather_date6;
    private TextView moreWeather_dateText6;
    private ImageView moreWeather_image6;
    private TextView moreWeather_highT_Text6;
    private TextView moreWeather_lowT_Text6;
    private NavigationView navigationView;
    private ImageView tv_userHead;
    private ImageView im_arrowPM;
    private View progressPM;
    private TextView tv_login;
    private static boolean isExit = false;    //判断点击第二次退出APP

    private MenuItem mCity;
    private Button btn_warrning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main);
        PushSettings.enableDebugMode(getApplicationContext(), true);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                Utils.getMetaValue(this, "api_key"));
        context = this;
        getWindow().setBackgroundDrawable(null);
        initView();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_share:
                shareAPP();
                break;
            case R.id.action_shop:
                Intent intent1 = new Intent();
                intent1.setClass(this, SalesActivity.class);
                startActivity(intent1);
                break;
            case R.id.action_friends:
                Intent intent2 = new Intent();
                intent2.setClass(this, FriendsActivity.class);
                startActivity(intent2);
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void shareAPP() {
//        checkInstallation()
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "今天天气很好，可以随时出行，最高气温30度，最低气温20度，祝您开心每一天！");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
    }

    private void initData() {

        weatherPresenter = new WeatherPresenter();
        weatherPresenter.addWeatherListener(this);
        weatherPresenter.getWeatherDataAndShow();
        weatherPresenter.getSixDaysAndShow();

        pollutionPresenter = new PollutionPresenter();
        pollutionPresenter.addPollutionListener(this);
        pollutionPresenter.getPollutionDataAndShow();
        pollutionPresenter.getSuggestionDataAndShow();

        userPresenter = new UserPresenter();
        userPresenter.addUserListener(this);

//        DaggerAppComponent.builder()
//                .activityModule(new AppModule(getApplication()))
//                .build()
//                .inject(this);
        locationPresenter = new LocationPresenter(context);
        locationPresenter.addLocationListener(this);
        locationPresenter.getLocation();

    }

    private void initView() {
        btn_warrning = (Button)findViewById(R.id.btn_warning);
//        ln_login = (TextView)findViewById(R.id.ln_login);
        tv_precip = (TextView) findViewById(R.id.humidity_level);
        tv_wind_scale = (TextView) findViewById(R.id.string_wind_level);
        tv_car_level = (TextView) findViewById(R.id.car_level);
        cloth_level = (TextView) findViewById(R.id.cloth_level);
        tv_wind_level = (TextView) findViewById(R.id.wind_level);

        moreWeather_date1 = (TextView) findViewById(R.id.moreWeather_date1);
        moreWeather_dateText1 = (TextView) findViewById(R.id.moreWeather_dateText1);
        moreWeather_image1 = (ImageView) findViewById(R.id.moreWeather_image1);
        moreWeather_highT_Text1 = (TextView) findViewById(R.id.moreWeather_highT_Text1);
        moreWeather_lowT_Text1 = (TextView) findViewById(R.id.moreWeather_lowT_Text1);

        moreWeather_date2 = (TextView) findViewById(R.id.moreWeather_date2);
        moreWeather_dateText2 = (TextView) findViewById(R.id.moreWeather_dateText2);
        moreWeather_image2 = (ImageView) findViewById(R.id.moreWeather_image2);
        moreWeather_highT_Text2 = (TextView) findViewById(R.id.moreWeather_highT_Text2);
        moreWeather_lowT_Text2 = (TextView) findViewById(R.id.moreWeather_lowT_Text2);

        moreWeather_date3 = (TextView) findViewById(R.id.moreWeather_date3);
        moreWeather_dateText3 = (TextView) findViewById(R.id.moreWeather_dateText3);
        moreWeather_image3 = (ImageView) findViewById(R.id.moreWeather_image3);
        moreWeather_highT_Text3 = (TextView) findViewById(R.id.moreWeather_highT_Text3);
        moreWeather_lowT_Text3 = (TextView) findViewById(R.id.moreWeather_lowT_Text3);

        moreWeather_date4 = (TextView) findViewById(R.id.moreWeather_date4);
        moreWeather_dateText4 = (TextView) findViewById(R.id.moreWeather_dateText4);
        moreWeather_image4 = (ImageView) findViewById(R.id.moreWeather_image4);
        moreWeather_highT_Text4 = (TextView) findViewById(R.id.moreWeather_highT_Text4);
        moreWeather_lowT_Text4 = (TextView) findViewById(R.id.moreWeather_lowT_Text4);

        moreWeather_date5 = (TextView) findViewById(R.id.moreWeather_date5);
        moreWeather_dateText5 = (TextView) findViewById(R.id.moreWeather_dateText5);
        moreWeather_image5 = (ImageView) findViewById(R.id.moreWeather_image5);
        moreWeather_highT_Text5 = (TextView) findViewById(R.id.moreWeather_highT_Text5);
        moreWeather_lowT_Text5 = (TextView) findViewById(R.id.moreWeather_lowT_Text5);

        moreWeather_date6 = (TextView) findViewById(R.id.moreWeather_date6);
        moreWeather_dateText6 = (TextView) findViewById(R.id.moreWeather_dateText6);
        moreWeather_image6 = (ImageView) findViewById(R.id.moreWeather_image6);
        moreWeather_highT_Text6 = (TextView) findViewById(R.id.moreWeather_highT_Text6);
        moreWeather_lowT_Text6 = (TextView) findViewById(R.id.moreWeather_lowT_Text6);


        tv_air_quality = (TextView) findViewById(R.id.air_quality);
        tv_pm_25 = (TextView) findViewById(R.id.pm_25);
        tv_temperature_high = (TextView) findViewById(R.id.temperature_high);
        tv_temperature_low = (TextView) findViewById(R.id.temperature_low);
        tv_temperature_now = (TextView) findViewById(R.id.temperature_now);
        tv_weather = (TextView) findViewById(R.id.weather_now);
        im_weather_now = (ImageView) findViewById(R.id.im_weather_now);
        scrollView = (MyScrollView) findViewById(R.id.scrollView);
        frameAnim = (ImageView) findViewById(R.id.frameAnim);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        progressPM = (View) findViewById(R.id.progressPM25);
        im_arrowPM = (ImageView) findViewById(R.id.arrow_PM);


        AnimatorSet set2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.sun_1_rotation);
        set2.setTarget(frameAnim);
        set2.start();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        scroll_View();
        /**
         * 下拉刷新
         */
        swipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
//                        Log.i("shen","swipe");
                swipeRefreshLayout.setRefreshing(false);
            }, 2000);
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setDrawer(toolbar);
         btn_warrning.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent();
                 intent.setClass(MainActivity.this,WarningActivity.class);
                 startActivity(intent);
             }
         });
    }

    private void setDrawer(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ln_login = (LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.ln_login);
        tv_login = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_login);
        tv_userHead = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_headImage);
        mCity = navigationView.getMenu().findItem(R.id.city);
        navigationView.setNavigationItemSelectedListener(this);
        Resources resource = (Resources) getBaseContext().getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.navigation_itme_color);
        navigationView.setItemTextColor(csl);
//        navigationView.setItemBackground(resource.getDrawable(R.drawable.nav_background));
        /**设置MenuItem默认选中项**/
        navigationView.getMenu().getItem(0).setChecked(false);

        ln_login.setOnClickListener((View v) -> {
            {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                this.setResult(MAINACTIVITY_CODE);
//                finish();

            }
        });
    }
    void checkLogin() {
        String userName = Istria_utils.getSP_String(this, Istria_utils.KEY_USERNAME);
        if (!userName.equals("")) {
            userPresenter.getUserAndShow(userName);
        }
    }
    /**
     * 上线滑屏总方法
     */
    private void scroll_View() {
        /**
         * scrollView 判断是否滑到头了
         */
        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
                if (swipeRefreshLayout != null) {
                    if (scrollView.getScrollY() == 0) {
//                            Log.i("shen","scoll1");
                        swipeRefreshLayout.setEnabled(true);
                    } else {
//                            Log.i("shen","scoll2");
                        swipeRefreshLayout.setEnabled(false);
                    }
                }
            });
        }
        /**
         * 根据滑动的距离来进行逐渐隐藏主天气图片
         */
        scrollView.setOnScrollListener((scrollView, x, y, oldx, oldy) -> {
            if (y >= 0)
                alpha = 255 - 255 * y / 650;
            else
                alpha = 255;
//                Log.i("shen",y+"");
//                Log.i("shen",alpha+"");
            if (alpha < 0) {
                alpha = 0;
            }
            frameAnim.getBackground().setAlpha(alpha);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }

    @Override
    public void showWeatherToday(Weather weather) {

        Message msg = new Message();
        msg.what = 1;
        msg.obj = weather;
        mHandler.sendMessage(msg);
    }

    @Override
    public void showSixDaysWeatherChange(ArrayList<Weather> weathers) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = weathers;
        mHandler.sendMessage(msg);
    }

    @Override
    public void showSuggestion(Suggestion_out suggestion_out) {
        Message msg = new Message();
        msg.what = 4;
        msg.obj = suggestion_out;
        mHandler.sendMessage(msg);
    }

    @Override
    public void showUser(User user) {
        Message msg = new Message();
        msg.what = 5;
        msg.obj = user;
        mHandler.sendMessage(msg);
    }

    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String du = getResources().getString(R.string.du);
            switch (msg.what) {
                case 1:
                    Weather weather = (Weather) msg.obj;
                    if (weather != null) {
                        tv_weather.setText(weather.getWeather_day());
                        tv_temperature_low.setText(weather.getTemperature_low() + du);
                        tv_temperature_high.setText(weather.getTemperature_high() + du);
                        tv_temperature_now.setText(weather.getTemperature_now() + du);
                        Picasso.with(context)
                                .load(Istria_Url.IMAGE_URL + weather.getImage() + ".png")
                                .into(im_weather_now);
                    }
                    break;
                case 2:
                    Pollution pollution = (Pollution) msg.obj;
                    if (pollution != null) {
                        tv_air_quality.setText(pollution.getAir_quality());
                        tv_pm_25.setText(pollution.getPm_25() + "");
                        im_arrowPM.setTranslationX(pollution.getPm_25() * progressPM.getWidth() / 500 - 20);
                    }
                    break;
                case 3:
                    ArrayList<Weather> weathers = (ArrayList<Weather>) msg.obj;
                    if (weathers.size() != 0) {
                        Date_I date = new Date_I();
                        moreWeather_date1.setText(weathers.get(0).getDate());
                        moreWeather_dateText1.setText(getResources().getString(R.string.today));
                        Picasso.with(context)
                                .load(Istria_Url.IMAGE_URL + weathers.get(0).getImage() + ".png")
                                .into(moreWeather_image1);
                        moreWeather_highT_Text1.setText(weathers.get(0).getTemperature_high() + du);
                        moreWeather_lowT_Text1.setText(weathers.get(0).getTemperature_low() + du);

                        moreWeather_date2.setText(weathers.get(1).getDate());
                        moreWeather_dateText2.setText(date.week("2016-" + weathers.get(1).getDate(), context));
                        Picasso.with(context)
                                .load(Istria_Url.IMAGE_URL + weathers.get(1).getImage() + ".png")
                                .into(moreWeather_image2);
                        moreWeather_highT_Text2.setText(weathers.get(1).getTemperature_high() + du);
                        moreWeather_lowT_Text2.setText(weathers.get(1).getTemperature_low() + du);

                        moreWeather_date3.setText(weathers.get(2).getDate());
                        moreWeather_dateText3.setText(date.week("2016-" + weathers.get(2).getDate(), context));
                        Picasso.with(context)
                                .load(Istria_Url.IMAGE_URL + weathers.get(2).getImage() + ".png")
                                .into(moreWeather_image3);
                        moreWeather_highT_Text3.setText(weathers.get(2).getTemperature_high() + du);
                        moreWeather_lowT_Text3.setText(weathers.get(2).getTemperature_low() + du);

                        moreWeather_date4.setText(weathers.get(3).getDate());
                        moreWeather_dateText4.setText(date.week("2016-" + weathers.get(3).getDate(), context));
                        Picasso.with(context)
                                .load(Istria_Url.IMAGE_URL + weathers.get(3).getImage() + ".png")
                                .into(moreWeather_image4);
                        moreWeather_highT_Text4.setText(weathers.get(3).getTemperature_high() + du);
                        moreWeather_lowT_Text4.setText(weathers.get(3).getTemperature_low() + du);

                        moreWeather_date5.setText(weathers.get(4).getDate());
                        moreWeather_dateText5.setText(date.week("2016-" + weathers.get(4).getDate(), context));
                        Picasso.with(context)
                                .load(Istria_Url.IMAGE_URL + weathers.get(4).getImage() + ".png")
                                .into(moreWeather_image5);
                        moreWeather_highT_Text5.setText(weathers.get(4).getTemperature_high() + du);
                        moreWeather_lowT_Text5.setText(weathers.get(4).getTemperature_low() + du);

                        moreWeather_date6.setText(weathers.get(5).getDate());
                        moreWeather_dateText6.setText(date.week("2016-" + weathers.get(5).getDate(), context));
                        Picasso.with(context)
                                .load(Istria_Url.IMAGE_URL + weathers.get(5).getImage() + ".png")
                                .into(moreWeather_image6);
                        moreWeather_highT_Text6.setText(weathers.get(5).getTemperature_high() + du);
                        moreWeather_lowT_Text6.setText(weathers.get(5).getTemperature_low() + du);

                    }
                    break;
                case 4:
                    Suggestion_out suggestion_out = (Suggestion_out) msg.obj;
                    if (suggestion_out != null) {
                        tv_precip.setText(suggestion_out.getHumidity());
                        ;
                        tv_wind_scale.setText(suggestion_out.getWindDir());
                        tv_car_level.setText(suggestion_out.getCarWashLevel());
                        cloth_level.setText(suggestion_out.getClothLevel());
                        tv_wind_level.setText(suggestion_out.getWindLevel());
                    }
                    break;
                case 5:
                    User user = (User) msg.obj;
                    if (user != null) {
                        tv_login.setText(user.getuName());
                        tv_userHead.setImageBitmap(user.getUserHead());//设置图片为背景图
                    }
                    ln_login.setOnClickListener((View v) -> {
                        {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, UserInformationActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }

    };

    @Override
    public void showPollution(Pollution pollution) {
        Message msg = new Message();
        msg.what = 2;
        msg.obj = pollution;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "雾霾卫士 https://www.pgyer.com/4xug");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

        }
//        else if (id == R.id.nav_tools) {
//
//        }
        else if (id == R.id.city) {
            Holder holder = new ListHolder();
            boolean isGrid = false;
            boolean expanded = false;
            ArrayList<DialogData> data = new ArrayList<>();
            DialogData dialogData = new DialogData();
            dialogData.setTitle(context.getString(R.string.Beijing));
            data.add(dialogData);
            DialogData dialogData2 = new DialogData();
            dialogData2.setTitle(context.getString(R.string.Shanghai));
            data.add(dialogData2);
            DialogData dialogData3 = new DialogData();
            dialogData3.setTitle(context.getString(R.string.Guangzhou));
            data.add(dialogData3);
            DialogAdapter adapter = new DialogAdapter(this, isGrid,data);
            new ShenDialog().showOnlyContentDialog(this,holder, Gravity.CENTER, adapter, itemClickListener, expanded);
        } else if (id == R.id.nav_suggestion) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,SuggestionActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, getResources().getString(R.string.anotherBack), Toast.LENGTH_SHORT).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finishAffinity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    OnItemClickListener itemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
            TextView textView = (TextView) view.findViewById(R.id.text_view);
           switch (position)
           {
               case 0 :
                   mCity.setTitle(textView.getText().toString());
                   Istria_utils.putSP(context, Istria_utils.KEY_USERLOCATION,textView.getText().toString());
                   dialog.dismiss();
                   break;
               case 1:
                   mCity.setTitle(textView.getText().toString());
                   Istria_utils.putSP(context, Istria_utils.KEY_USERLOCATION,textView.getText().toString());
                   dialog.dismiss();
                   break;
               case 2:
                   mCity.setTitle(textView.getText().toString());
                   Istria_utils.putSP(context, Istria_utils.KEY_USERLOCATION,textView.getText().toString());
                   dialog.dismiss();
                   break;
           }

        }
    };
    @Override
    public void showLocation(String city) {
        mCity.setTitle(city);
        Istria_utils.putSP(this, Istria_utils.KEY_USERLOCATION,city);
    }

//    /**
//     * 判断是否安装腾讯、新浪等指定的分享应用
//     *
//     * @param packageName 应用的包名
//     */
//    public boolean checkInstallation(String packageName) {
//        try {
//            this.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
//    }

}
