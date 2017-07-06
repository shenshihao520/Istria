package shen.pula.istria.viewController;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import shen.pula.istria.R;

/**
 * Created by dell on 2017/1/16.
 */

public class UserInformationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_Title = 1;
    private static final int TYPE_GROUP = 2;
    private List<String> mData = new ArrayList<>();
    private Context mContext;
    private onItemClickListener itemClickListener;
    private boolean isTitle(int pos){
        if(mData.get(pos).startsWith("1")) {
            return true;
        }
        return false;
    }
    private boolean isItemTitle(int pos){
        if(mData.get(pos).startsWith("2")) {
            return true;
        }
        return false;
    }
    public UserInformationAdapter(Context mContext, List<String> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }
    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (isTitle(position) ) {
            viewType = TYPE_GROUP;
        } else if(isItemTitle(position)){
            viewType = TYPE_ITEM_Title;
        }
        else
        {
            viewType = TYPE_ITEM;
        }
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from (mContext);
        if(viewType == TYPE_GROUP)
        {
            View groupTitle = mInflater.inflate ( R.layout.userinformation_title, parent, false );
            GroupTitleViewHolder gTitle = new GroupTitleViewHolder ( groupTitle );
            return gTitle;
        }
        if (viewType == TYPE_ITEM_Title)
        {
            View item =  mInflater.inflate ( R.layout.userinformation_itemtitle, parent, false );
            ItemTitleViewHolder vItem = new ItemTitleViewHolder ( item );
            item.setOnClickListener(this);
            return vItem;
        }
        else
        {
            View item =  mInflater.inflate ( R.layout.userinformation_item, parent, false );
            ItemViewHolder vItem = new ItemViewHolder ( item );
            item.setOnClickListener(this);
            return vItem;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch ( holder.getItemViewType () ) {
            case TYPE_ITEM_Title:
                ItemTitleViewHolder itemTitleViewHolder = ( ItemTitleViewHolder ) holder;
                itemTitleViewHolder.item_title.setText(mData.get(position).substring(1));
                break;
            case TYPE_GROUP:
                GroupTitleViewHolder groupViewHolder = ( GroupTitleViewHolder ) holder;
                groupViewHolder.group_title.setText ( mData.get(position).substring(1));
                break;
            case TYPE_ITEM:
                ItemViewHolder itemViewHolder = ( ItemViewHolder ) holder;
                itemViewHolder.item_deception.setText ( mData.get(position));

        }
    }

    @Override
    public int getItemCount() {
        return mData.size ();
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            //注意这里使用getTag方法获取数据
           itemClickListener.onItemClick(v);

        }
    }

    class GroupTitleViewHolder extends RecyclerView.ViewHolder{
        TextView group_title;
        public GroupTitleViewHolder(View itemView) {
            super(itemView);
            group_title = (TextView) itemView.findViewById(R.id.information_title);
        }
    }
    class ItemTitleViewHolder extends RecyclerView.ViewHolder
    {
        TextView item_title;
        public ItemTitleViewHolder(View itemView) {
            super(itemView);
            item_title = (TextView) itemView.findViewById(R.id.uf_title);

        }
    }
    class ItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView item_deception;
        public ItemViewHolder(View itemView) {
            super(itemView);
            item_deception = (TextView) itemView.findViewById(R.id.uf_deception);

        }
    }
    public   interface onItemClickListener
    {
        void onItemClick(View view );
    }
    public void setOnItemClickListener(onItemClickListener listener) {
        this.itemClickListener = listener;
    }
}
