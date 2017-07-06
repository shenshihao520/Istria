package shen.pula.istria.viewController;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import shen.pula.istria.R;
import shen.pula.istria.data_module.FriendsSubmit;
import shen.pula.istria.data_module.User;

import static com.baidu.location.h.j.U;

/**
 * Created by dell on 2016/12/28.
 */

public class FriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    public static final int ITEM_TYPE_Head = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    private ArrayList<FriendsSubmit> mData;
    private ArrayList<Integer> mZanClickList = new ArrayList<>();
    private ArrayList<Integer> mForAllClickList = new ArrayList<>();
    private int mBottomCount=1;//底部View个数
//    public boolean isBottomView(int position) {
//        return mBottomCount != 0 && position >= (mData.size());
//    }
    private ProgressBar progressBar;
    private Handler handler = new Handler( );

    private Runnable runnable = new Runnable( ) {

        public void run ( ) {

            progressBar.setVisibility(View.INVISIBLE);

        }

    };


    public FriendsAdapter(Context context, ArrayList<FriendsSubmit> friendsSubmits) {
        mData = friendsSubmits;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
       if (position >= (mData.size())) {
            return ITEM_TYPE_BOTTOM;
        }
//        else if (position == 0)
//       {
//           return ITEM_TYPE_Head;
//       }
       else {
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE_CONTENT)
        {
            View view = mInflater.inflate(R.layout.friends_item, parent, false);
            FriendsViewHolder viewHolder = new FriendsViewHolder(view);
            return viewHolder;
        }
        else if(viewType == ITEM_TYPE_Head)
        {
            View view = mInflater.inflate(R.layout.recyclerview_head, parent, false);
            HeadViewHolder viewHolder = new HeadViewHolder(view);
            return viewHolder;
        }
        else
        {
            View view = mInflater.inflate(R.layout.view_load_more, parent, false);
            BottomViewHolder  footViewHolder = new BottomViewHolder(view);
            return footViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FriendsViewHolder)
        {
            FriendsViewHolder friendsViewHolder = (FriendsViewHolder)holder;
            friendsViewHolder.tv_userName.setText(mData.get(position).getUserName());
            friendsViewHolder.tv_location.setText(mData.get(position).getLocation());
            friendsViewHolder.tv_submitTime.setText(mData.get(position).getTime());
            friendsViewHolder.tv_goodTimes.setText(mData.get(position).getZanNum() + "");
            friendsViewHolder.tv_comment.setText(mData.get(position).getContent());


            commentController(friendsViewHolder, position);
            zanController(friendsViewHolder, position);
            clickForAllController(friendsViewHolder, position);

            if (mForAllClickList.contains(position)) {
                friendsViewHolder.tv_clickForAll.setVisibility(View.VISIBLE);
            } else {
                friendsViewHolder.tv_clickForAll.setVisibility(View.GONE);
            }
            if (mZanClickList.contains(position)) {
                friendsViewHolder.zan.setImageResource(R.mipmap.night_zan_8);
            } else {
                friendsViewHolder.zan.setImageResource(R.mipmap.night_zan_0);
            }
        }
        else
        {
//            BottomViewHolder bottomViewHolder = (BottomViewHolder)holder;
//            progressBar = bottomViewHolder.progressBar;
//            progressBar.setVisibility(View.VISIBLE);
//            handler.postDelayed(runnable,1000);
        }


    }

    /**
     * 控制评论文字行高以及全文按钮的显示
     * @param holder
     * @param position
     */
    private void commentController(final FriendsViewHolder holder, final int position) {
        holder.tv_clickForAll.setTag(position);
        holder.tv_comment.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                    if (holder.tv_comment.getLineCount() > 5) {
                        lookForAllMsg(holder, position,mData.get(position).getContent());
                        holder.tv_comment.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        holder.tv_comment.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
            }
        });
    }

    /**
     * 控制点击全文按钮
     * @param holder
     * @param position
     */
    private void clickForAllController(final FriendsViewHolder holder, final int position) {
        holder.tv_clickForAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    if (!mForAllClickList.contains(holder.tv_clickForAll.getTag())) {
//                    int height = holder.tv_comment.getLineHeight() * holder.tv_comment.getLineCount();
//                    holder.tv_comment.setHeight(height);
//                    holder.tv_clickForAll.setVisibility(View.GONE);
//                }
                alert_AllTextDialog(mData.get(position).getContent(),holder.tv_clickForAll);

            }
        });
    }

    /**
     * 控制赞的动画及数据
     * @param holder
     * @param position
     */
    private void zanController(final FriendsViewHolder holder, final int position) {
        holder.zan.setTag(position);
        holder.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mZanClickList.contains(holder.zan.getTag())) {
                    mZanClickList.add(position);
                    int duration = runFrame(holder.zan);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            holder.tv_goodTimes.setText(mData.get(position).getZanNum() + 1 + "");
                            mData.get(position).setZanNum(mData.get(position).getZanNum() + 1);
                            holder.zan.setClickable(false);
                            holder.zan.setImageResource(R.mipmap.night_zan_8); //
                        }
                    }, duration);
                }
            }
        });
    }

    private void lookForAllMsg(FriendsViewHolder holder, int position, String content) {

        if (!mForAllClickList.contains(holder.tv_clickForAll.getTag())) {
            holder.tv_clickForAll.setVisibility(View.VISIBLE);
            mForAllClickList.add(position);
        }
    }

    /**
     * 弹框显示全文
     * @param content
     * @param tv_clickForAll
     */
    public void alert_AllTextDialog(String content, TextView tv_clickForAll)
    {
        View popupView = LayoutInflater.from(mContext)
                .inflate(R.layout.lookfor_allmsg, null);
        PopupWindow window = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        TextView tv_allText = (TextView) popupView.findViewById(R.id.allText);
        tv_allText.setMovementMethod(ScrollingMovementMethod.getInstance());
        tv_allText.setText(content);
        LinearLayout linearLayout = (LinearLayout) popupView.findViewById(R.id.lookFor_all);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

        // 2016/5/17 设置动画
        window.setAnimationStyle(R.style.popup_window_anim);
        //  2016/5/17 设置背景颜色
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        //2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        //  2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // 更新popupwindow的状态
        window.update();
        // 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAtLocation(tv_clickForAll, Gravity.TOP,100,100);
    }
    private int runFrame(ImageView zan) {
        //完全编码实现的动画效果
        AnimationDrawable anim = new AnimationDrawable();
        for (int i = 0; i <= 8; i++) {
            //根据资源名称和目录获取R.java中对应的资源ID
            int id = mContext.getResources().getIdentifier("night_zan_" + i, "mipmap", mContext.getPackageName());
            //根据资源ID获取到Drawable对象
            Drawable drawable = mContext.getResources().getDrawable(id);
            //将此帧添加到AnimationDrawable中
            anim.addFrame(drawable, 40);
        }
        int duration = 320;
        anim.setOneShot(true); //设置为loop
        zan.setImageDrawable(anim); //将动画设置为ImageView背景
        anim.start();  //开始动画

        return duration;
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    public void addData(int position) {
//        User user = new User();
//        user.setuName("Niko");
        FriendsSubmit friendsSubmit = new FriendsSubmit();
        friendsSubmit.setContent("Morning!!!!!!!!!!");
        friendsSubmit.setZanNum(0);
//        friendsSubmit.setUser(user);
        friendsSubmit.setTime("15:11");
        friendsSubmit.setLocation("Beijing");
        mData.add(position, friendsSubmit);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }
    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        LinearLayout bottom ;
        ProgressBar progressBar;
        public BottomViewHolder(View itemView) {
            super(itemView);
            bottom = (LinearLayout)itemView.findViewById(R.id.bottom) ;
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);

        }
    }
    //顶部 ViewHolder
    public static class HeadViewHolder extends RecyclerView.ViewHolder {
        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }
}
