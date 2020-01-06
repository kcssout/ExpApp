package com.example.ExpApp;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.ExpApp.BkPagerAdapter.BkUtils;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {

    // 멤버 필드

    public final int MAX = 9;                     //뷰 페이저가 가질 페이지 최고 갯수=>0~9(10개).
    private int mPrevPosition;                     //이전에 선택되었던 포지션 값. 현재 페이지 표시 위한 변수
    private Context mContext;
    private Button mAddView;                     //뷰 페이저에 아이템 추가하는 버튼
    public Spinner mViewTypeSpinner;       //뷰 페이저에 추가할 아이템의 타입을 고르는 스피너
    private ViewPager mPager;                   //뷰 페이저
    public LinearLayout mPageMark;          //현재 몇 페이지 인지 나타내는 뷰
    public BkPagerAdapter mAdapter;         //아답터 객체. 아이템을 추가 하기 위해 변수 선언

    public ArrayList<Integer> mItems;
    public int reomveTempPage = 0;
    public static MainActivity instance;


    public static MainActivity getInstance() {
        if (instance == null) {
            return new MainActivity();
        }
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other2);

        instance = this;
        mContext = this;
        mAddView = findViewById(R.id.btn_add);
        mAddView.setOnClickListener(this);                        //클릭 리스너 등록
        mAddView.setOnLongClickListener(this);

        mViewTypeSpinner = (Spinner) findViewById(R.id.spinner_view_type); //아이템 타입 선택하는 스피너

        mPageMark = findViewById(R.id.page_mark);

        mPager = (ViewPager) findViewById(R.id.pager);                        //뷰 페이저
        mAdapter = new BkPagerAdapter(getApplicationContext());
        mPager.setAdapter(mAdapter);                                            //PagerAdapter로 설정
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {    //아이템이 변경되면, gallery나 listview의 onItemSelectedListener와 비슷
            //아이템이 선택이 되었으면
            @Override
            public void onPageSelected(int position) {

                try {
                    Log.d(TAG, "이전 페이지 : " + mPrevPosition + " // 현재페이지(now) : " + position + " // removepage : " + reomveTempPage);

                    mPageMark.getChildAt(mPrevPosition).setBackgroundResource(R.drawable.page_not);    //이전 페이지에 해당하는 페이지 표시 이미지 변경
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "error");
                }
                mPageMark.getChildAt(position).setBackgroundResource(R.drawable.page_select);        //현재 페이지에 해당하는 페이지 표시 이미지 변경
                mPrevPosition = position;                //이전 포지션 값을 현재로 변경
            }

            @Override
            public void onPageScrolled(int position, float positionOffest, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        mPrevPosition = 0;    //이전 포지션 값 초기화
        addPageSample();        //현재 페이지 표시하는 뷰 추가
        mPageMark.getChildAt(mPrevPosition).setBackgroundResource(R.drawable.page_select);    //이전 페이지에 해당하는 페이지 표시 이미지 변경
    }


    @Override
    public void onClick(View view) {
        if (view == mAddView) {
            addPageMark();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view == mAddView) {
            int action = Intent.FLAG_ACTIVITY_CLEAR_TASK ;
            addShortcut("추가",action,R.drawable.ic_launcher);
        }
        return false;
    }


    public class BkPagerAdapter extends PagerAdapter {

        private Context mContext;


        public BkPagerAdapter(Context context) {
            super();
            mContext = context;
            mItems = new ArrayList<Integer>();
            mItems.add(BkUtils.TYPE_TEXTVIEW); // 최초의 아이템은 기본으로 생성

        }

        //뷰 페이저의 아이템 갯수는 리스트의 갯수
        //나중에 뷰 페이저에 아이템을 추가하면 리스트에 아이템의 타입을 추가 후 새로 고침하게 되면
        //자동으로 뷰 페이저의 갯수도 늘어난다
        @Override
        public int getCount() {
            if (mItems == null) {
                return 0;
            } else {
                return mItems.size();
            }
        }


        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public Object instantiateItem(@NonNull View pager, int position) {
            View v = BkUtils.getView(mItems.get(position), mContext);

            ((ViewPager) pager).addView(v);
            return v;

        }


        //뷰 객체 삭제.
        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager) pager).removeView((View) view);
        }

        // instantiateItem메소드에서 생성한 객체를 이용할 것인지
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public void addItem(int type) {
            mItems.add(type);                //아이템 목록에 추가
            notifyDataSetChanged();        //아답터에 데이터 변경되었다고 알림. 알아서 새로고침

        }

        public void removeItem(int position) {
            reomveTempPage = position; // 현재 지울 페이지

            mItems.remove(position);            // item list 제거
            mPageMark.removeViewAt(position);   //  마크 제거
            mPager.setAdapter(mAdapter);        //PagerAdapter 셋팅
            notifyDataSetChanged();
        }

        @Override
        public void restoreState(@Nullable Parcelable state, @Nullable ClassLoader loader) {
        }

        @Nullable
        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        //버튼 클릭 시 페이지 추가하는 코드
    }

    public void addPageSample() {
        ImageView iv = new ImageView(getApplicationContext());
        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        iv.setBackgroundResource(R.drawable.page_not);
        mPageMark.addView(iv);//LinearLayout에 추가
    }

    public void firstPage() {
        ImageView iv = new ImageView(getApplicationContext());
        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        iv.setBackgroundResource(R.drawable.page_select);
        mPageMark.addView(iv);//LinearLayout에 추가
    }

    public void addPageMark() {// 페이지 표시 이미지 뷰 생성
        goActivity();
    }

    private void goActivity() {
        Intent a = new Intent(this, ListAddActivity.class);
        mContext.startActivity(a);
    }

//-------------
private static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final String EXTRA_KEY_SHORTCUT = "extra_key_shortcut";

    /**
     * 바로가기 생성 (버전 체크)
     *
     * @param name 바로가기 이름
     * @param flag 바로가기 실행시 구분 값
     * @param resId 바로가기 아이콘
     */
    public void addShortcut(String name, int flag, @DrawableRes int resId) {
        Intent action= new Intent(getApplicationContext(), ListAddActivity.class);
        action.putExtra(EXTRA_KEY_SHORTCUT, flag);    // 앱 내에서 바로가기 실행을 구분하기 위한 값
        action.setAction( Intent.ACTION_MAIN );

        // 디바이스의 현재버전 비교
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            sendShortcut(name, resId, action);        // 오레오 아래인 경우
        } else {
            sendShortcutOreo(name, resId, action);     // 오레오인 경우
        }
    }

    /**
     * 바로가기 생성 (오레오를 제외한 버전)
     *
     * @param name 바로가기 이름
     * @param resId 바로가기 아이콘
     * @param action 바로가기 인텐트 설정
     */
    private void sendShortcut(String name, @DrawableRes int resId, Intent action) {
        Intent intent = new Intent(ACTION_INSTALL_SHORTCUT);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(getApplicationContext(), resId));
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, action);
        getApplicationContext().sendBroadcast(intent);
    }

    /**
     * 바로가기 생성 (오레오 버전)
     *
     * @param name 바로가기 이름
     * @param resId 바로가기 아이콘
     * @param action 바로가기 인텐트 설정
     */
    @TargetApi(Build.VERSION_CODES.O)
    private void sendShortcutOreo(String name, @DrawableRes int resId, Intent action) {
        ShortcutManager shortcutManager = getApplicationContext().getSystemService(ShortcutManager.class);
        if (shortcutManager.isRequestPinShortcutSupported()) {
            if(action != null){
                Log.d(TAG, "action is not null");
            }
            ShortcutInfo pinShortcutInfo = new ShortcutInfo.Builder(getApplicationContext(), name) // 여러종류를 생성할 경우 구분하기 위한 값으로 꼭 name으로 안해도 됨
                    .setIntent(action)
                    .setShortLabel(name)
                    .setIcon(Icon.createWithResource(getApplicationContext(), resId)).build();
            Intent pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(pinShortcutInfo);
            PendingIntent successCallback = PendingIntent.getBroadcast(getApplicationContext(), 0, pinnedShortcutCallbackIntent, 0);

            shortcutManager.requestPinShortcut(pinShortcutInfo, successCallback.getIntentSender());
        }
    }



}
