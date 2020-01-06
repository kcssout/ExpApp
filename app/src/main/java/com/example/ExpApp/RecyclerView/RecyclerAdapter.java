package com.example.ExpApp.RecyclerView;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ExpApp.MainActivity;
import com.example.ExpApp.R;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private String TAG = RecyclerAdapter.class.getSimpleName();
    private ArrayList<Integer> datalist;
    private int itemLayout;
    RecyclerViewHolder viewHolder;
    private Context mContext;

    public RecyclerAdapter(ArrayList<Integer> itemlist) {

        this.datalist = itemlist;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //레이아웃을 만들어서 Holder 에 저장
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_layout, parent, false);
        viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        int num = 0;
        try {
            Log.d(TAG, " position : " + position + " , num : " + num + " holder : " + holder.getAdapterPosition()); // + " , size : "+datalist.size()
            holder.tvTitle.setText(datalist.get(position).toString() + " 번쨰");
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext,"포지션 : " + position , Toast.LENGTH_SHORT).show();
                    int type = datalist.get(position);
                    MainActivity.getInstance().mAdapter.removeItem(position);
                    notifyDataSetChanged();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
//    * listView getView 를 대체
//     * 넘겨 받은 데이터를 화면에 출력하는 역할
//        Album item = albumlist.get(position);
//        holder.tvTitle.setText(item.getTitle());
//        holder.img.setBackgroundResource(item.getImage());
//        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


}

/**
 * 뷰 재활용을 위한 viewHolder
 */

class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public ImageView img;
    public TextView tvTitle;


    RecyclerViewHolder(View view) {
        super(view);
        img = view.findViewById(R.id.img);
        tvTitle = view.findViewById(R.id.textTitle);

    }
}