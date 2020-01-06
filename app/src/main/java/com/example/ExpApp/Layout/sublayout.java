package com.example.ExpApp.Layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ExpApp.R;

public class sublayout extends LinearLayout {

    private Button prev, next;
    private RadioButton option, enable, disable;
    private RecyclerView rc_other;

    public sublayout(Context context) {
        super(context);

        init(context);
    }

    private void init(final Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.other, this, true);
        prev = v.findViewById(R.id.prev);
        next = v.findViewById(R.id.next);
        rc_other = v.findViewById(R.id.rc_other);

//        RecyclerView.LayoutManager
//        rc_other.setLayoutManager();

        prev.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(context, "야야야ㅑㅇ ", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}