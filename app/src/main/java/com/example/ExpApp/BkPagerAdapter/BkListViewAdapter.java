package com.example.ExpApp.BkPagerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ExpApp.R;

public class BkListViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private String[][] mItems;

    public BkListViewAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mItems = new String[2][];
        mItems[0] = mContext.getResources().getStringArray(R.array.avengers);
        mItems[1] = mContext.getResources().getStringArray(R.array.actor);

    }

    @Override
    public int getCount() {
        return mItems == null ? 0 : mItems[0].length * 4;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    private class ViewHolder {
        TextView character, actor;
        ImageView poster;
    }
    @Override
    public View getView(int positon, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder.poster = convertView.findViewById(R.id.poster);
            holder.character = convertView.findViewById(R.id.character);
            holder.actor = convertView.findViewById(R.id.actor);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        int index = positon % 4;
        holder.poster.setImageResource(R.drawable.avengers1 + index);
        holder.character.setText(mItems[0][index]);
        holder.actor.setText(mItems[1][index]);

        return convertView;
    }
}
