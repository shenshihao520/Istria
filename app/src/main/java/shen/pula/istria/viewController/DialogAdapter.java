package shen.pula.istria.viewController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import shen.pula.istria.R;
import shen.pula.istria.data_module.DialogData;

public class DialogAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private boolean isGrid;
    ArrayList<DialogData> data;

    public DialogAdapter(Context context, boolean isGrid, ArrayList<DialogData> data) {
        layoutInflater = LayoutInflater.from(context);
        this.isGrid = isGrid;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            if (isGrid) {
                view = layoutInflater.inflate(R.layout.simple_grid_item, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.simple_list_item, parent, false);
            }
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(data.get(position).getTitle());
        if(data.get(position).getImageID() != 0)
        {
            viewHolder.imageView.setImageResource(data.get(position).getImageID());
        }
        else
        {
            viewHolder.imageView.setVisibility(View.GONE);
        }

        return view;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
