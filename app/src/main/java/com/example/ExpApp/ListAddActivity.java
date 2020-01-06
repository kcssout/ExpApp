package com.example.ExpApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ExpApp.R;
import com.example.ExpApp.RecyclerView.RecyclerAdapter;

public class ListAddActivity extends Activity {

    private Intent intent;
    private RecyclerView rc_view;
    private Button addView, removeView;
    public RecyclerAdapter recyclerAdapter;
    public static ListAddActivity instance;
    private static String TAG = ListAddActivity.class.getSimpleName();


    public static ListAddActivity getInstance() {
        if (instance == null) {
            return new ListAddActivity();
        }
        return instance;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = getIntent();

        rc_view = findViewById(R.id.rc_view);
        addView = findViewById(R.id.rc_add);
        removeView = findViewById(R.id.rc_remove);

        try{
            Log.d(TAG, "숫자 >> " + MainActivity.getInstance().mItems.size() );
            recyclerAdapter = new RecyclerAdapter(MainActivity.getInstance().mItems);
        }catch (Exception e){
            e.printStackTrace();
        }

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);

        rc_view.setAdapter(recyclerAdapter);
        rc_view.setLayoutManager(mLinearLayoutManager);
        rc_view.setItemAnimator(new DefaultItemAnimator());

        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MainActivity.getInstance().mAdapter.getCount() <= MainActivity.getInstance().MAX && MainActivity.getInstance().mAdapter.getCount()>0) {            //최대 갯수 이하이면
                    int type = MainActivity.getInstance().mViewTypeSpinner.getSelectedItemPosition();    //아이템 타입을 가져와
                    MainActivity.getInstance().mAdapter.addItem(type);                    //해당 타입의 아이템을 추가한다. 뷰 페이져 새로고침
                    MainActivity.getInstance().addPageSample();                                //페이지 표시 뷰도 추가
                    recyclerAdapter.notifyDataSetChanged();
                }else if(MainActivity.getInstance().mAdapter.getCount() == 0){
                    int type = MainActivity.getInstance().mViewTypeSpinner.getSelectedItemPosition();    //아이템 타입을 가져와
                    MainActivity.getInstance().mAdapter.addItem(type);                    //해당 타입의 아이템을 추가한다. 뷰 페이져 새로고침
                    MainActivity.getInstance().firstPage();                                //페이지 표시 뷰도 추가
                    recyclerAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "최대 10개의 아이템만 등록 가능합니다. 소스를 수정하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ;
    }
}
