package shen.pula.istria.viewController;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import shen.pula.istria.MPresenter.FriendsPresenter;
import shen.pula.istria.R;
import shen.pula.istria.VPresenter.OperationFriendsView;
import shen.pula.istria.data_module.FriendsSubmit;
import shen.pula.istria.utils.DividerItemDecoration;
import shen.pula.istria.view.EndlessRecyclerOnScrollListener;

/**
 * Created by dell on 2016/12/16.
 */

public class FriendsActivity extends AppCompatActivity implements OperationFriendsView, SwipeRefreshLayout.OnRefreshListener {
    private ImageView backdrop_ttl;
    private RecyclerView mRecyclerView;
    private FriendsAdapter mFriendsAdapter;
    private Context mContext;
    private FriendsPresenter friendsPresenter ;
    private  AppBarLayout mAppBarLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<FriendsSubmit> mFriendsSubmits;
    private LinearLayout progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_page);
        mContext = this;
        initWindow();
        initView();
        intiData();
    }

    private void intiData() {
        friendsPresenter = new FriendsPresenter();
        friendsPresenter.addFriendsSubmitListener(this);
        friendsPresenter.getFriendSubmitAndShow();
    }

    private void initRecyclerView(ArrayList<FriendsSubmit> friendsSubmits) {
        mFriendsSubmits = friendsSubmits;
        mFriendsAdapter = new FriendsAdapter(mContext,mFriendsSubmits);
        mRecyclerView.setAdapter(mFriendsAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                friendsPresenter.simulateLoadMoreData(mFriendsAdapter,mFriendsSubmits);
            }
        });
    }
    @TargetApi(19)
    private void initWindow()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            final Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_RecyclerView);
        backdrop_ttl = (ImageView) findViewById(R.id.backdrop);
        makeTitleChange();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,             //分割线
                DividerItemDecoration.VERTICAL_LIST));


        scroll_View();
        swipeRefreshLayout.setOnRefreshListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                mFriendsAdapter.addData(1);
                Intent intent = new Intent();
                intent.setClass(mContext, FriendsSubmitActivity.class);
                mContext.startActivity(intent);
                finish();
//                mRecyclerView.scrollToPosition(0);
            }
        });
//

    }
    private void makeTitleChange()
    {
        backdrop_ttl.setImageResource(R.mipmap.bkg_sunny_full);
//        setTheme(R.style.AppTheme_NoActionBar);                                  //随着天气改变顶部
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mAppBarLayout.setExpanded(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAppBarLayout.setExpanded(true);
    }

    @Override
    public void showFriendsSubmit(ArrayList<FriendsSubmit> friendsSubmits) {
        initRecyclerView(friendsSubmits);
    }
    private void scroll_View() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0)
                {
                    swipeRefreshLayout.setEnabled(true);
                }
                else
                {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
//                        Log.i("shen","swipe");
            swipeRefreshLayout.setRefreshing(false);
        }, 2000);
    }

}
