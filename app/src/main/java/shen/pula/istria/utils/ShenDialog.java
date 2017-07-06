package shen.pula.istria.utils;

import android.content.Context;
import android.widget.BaseAdapter;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnItemClickListener;

/**
 * Created by dell on 2017/2/8.
 */

public class ShenDialog {
    private DialogPlus dialog;

    public void showOnlyContentDialog(Context context, Holder holder, int gravity, BaseAdapter adapter,
                                       OnItemClickListener itemClickListener,
                                       boolean expanded) {
        dialog = DialogPlus.newDialog(context)
                .setMargin(90, 0, 90, 0)
                .setContentHolder(holder)
                .setGravity(gravity)
                .setAdapter(adapter)
                .setOnItemClickListener(itemClickListener)
                .setExpanded(expanded)
                .setCancelable(true)
                .create();
        dialog.show();
    }
    public void dismiss()
    {
        dialog.dismiss();
    }
}
