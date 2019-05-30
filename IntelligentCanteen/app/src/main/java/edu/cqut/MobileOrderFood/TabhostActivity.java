package edu.cqut.MobileOrderFood;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.cqut.MobileOrderFood.fragment.MyFragmentPagerAdapter;
import edu.cqut.MobileOrderFood.fragment.dummy.TabEntity;
import edu.cqut.MobileOrderFood.widget.NoScrollViewPager;


@SuppressWarnings("deprecation")
public class TabhostActivity extends AppCompatActivity {

	private ImageView mZoomIv;//图片视图
	private Toolbar mToolBar;//工具栏
	private ViewGroup titleContainer;//该类是放置view的容器
	private AppBarLayout mAppBarLayout;//该类是可以动态控制的bar
	private ViewGroup titleCenterLayout;//该类是用来放置按钮，文本等的布局的
	private ImageView mSettingIv, mMsgIv;
	private CommonTabLayout mTablayout;
	private NoScrollViewPager mViewPager;//修改view pager//是用来翻页的

	private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
	private List<Fragment> fragments;//fragments 是一种可以嵌入到 activity中的UI片段

	private FloatingActionButton myfloat;//浮动按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_main);
		findId();
		initListener();
		initTab();
		initStatus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tabhost, menu);
		return true;
	}


	private void findId()
	{
		mToolBar = (Toolbar) findViewById(R.id.toolbar);
		mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
		mTablayout = (CommonTabLayout) findViewById(R.id.uc_tablayout);//标签布局
		mViewPager = (NoScrollViewPager) findViewById(R.id.uc_viewpager);//用来换页的界面
		myfloat=(FloatingActionButton) findViewById(R.id.floatbutton);
	}

	private void initTab(){
		fragments=getFragments();
		MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, getNames());

		mTablayout.setTabData(mTabEntities);
		mViewPager.setAdapter(myFragmentPagerAdapter);//view+fragements 实现水平翻页

	}

	private void initListener(){

		//该函数实现的时appBarlayout的动作
		//AppBarLayoutOverScrollViewBehavior myAppBarLayoutBehavoir = (AppBarLayoutOverScrollViewBehavior)
				//((CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams()).getBehavior();

		//该函数实现对fragment内容的刷新
		//myAppBarLayoutBehavoir.setOnProgressChangeListener(new AppBarLayoutOverScrollViewBehavior.onProgressChangeListener()

		myfloat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				{
					Intent intent = new Intent(TabhostActivity.this, OrderedActivity.class);
					startActivity(intent);
				}

			}
		});

		mTablayout.setOnTabSelectListener(new OnTabSelectListener() {
			@Override
			public void onTabSelect(int position) {
				mViewPager.setCurrentItem(position);//将tab和fragment进行绑定
			}

			@Override
			public void onTabReselect(int position) {

			}
		});

		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				mTablayout.setCurrentTab(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	private void initStatus(){
//初始化状态栏信息
	}



	public String[] getNames() {
		String[] mNames = new String[]{"一食堂", "二食堂", "小吃城", "美食广场"};
		for (String str : mNames) {
			mTabEntities.add(new TabEntity(str));
		}

		return mNames;
	}

	public List<Fragment> getFragments() {
		List<Fragment> fragments = new ArrayList<>();
		fragments.add(new ItemFragment());//增加的ItemFragment 中有recycle的布局
		fragments.add(new ItemFragment());
		fragments.add(new ItemFragment());
		fragments.add(new ItemFragment());
		return fragments;
	}
}
