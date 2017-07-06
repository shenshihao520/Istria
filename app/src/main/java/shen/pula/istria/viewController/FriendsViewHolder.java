package shen.pula.istria.viewController;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import shen.pula.istria.R;

/**
 * Created by dell on 2017/1/3.
 */
public class FriendsViewHolder extends RecyclerView.ViewHolder {
    public ImageView userHeadIcon;
    public TextView tv_userName;
    public TextView tv_location;
    public TextView tv_submitTime;
    public TextView tv_goodTimes;
    public ImageView zan;
    public TextView tv_comment;
    public TextView tv_clickForAll;


    public FriendsViewHolder(View itemView) {
        super(itemView);

        zan = (ImageView) itemView.findViewById(R.id.zan);
        userHeadIcon = (ImageView) itemView.findViewById(R.id.userHeadIcon);
        tv_userName = (TextView)itemView.findViewById(R.id.tv_userName);
        tv_location = (TextView)itemView.findViewById(R.id.tv_location);
        tv_submitTime = (TextView)itemView.findViewById(R.id.tv_submitTime);
        tv_goodTimes = (TextView)itemView.findViewById(R.id.tv_goodTimes);
        tv_comment = (TextView)itemView.findViewById(R.id.tv_comment);
        tv_clickForAll = (TextView)itemView.findViewById(R.id.tv_clickForAll);

    }

}
