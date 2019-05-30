package edu.cqut.MobileOrderFood;

import java.util.ArrayList;

import edu.cqut.MobileOrderFood.FirstPage.ImgItem_content;
import edu.cqut.MobileOrderFood.FirstPage.MyRecycleViewAdapter;
import edu.cqut.MobileOrderFood.FirstPage.MylayoutManager;
import edu.cqut.MobileOrderFood.MyApplication;
import edu.cqut.MobileOrderFood.LoginDialog;
import info.hoang8f.widget.FButton;


import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;


public class MainActivity extends Activity {
	private static final int REGISTERACTIVITY = 1; //设置注册Activity的请求码
	public static MyApplication mAppInstance =new MyApplication (); //用来设置程序全局变量
	private static String mUserFileName ="UserInfo";//定义SharedPreferences数据文件名称
	public FButton mImgBtnLogin, mImgBtnLogout,imgBtnorder,imgBtndingdan;


	RecyclerView recycleView;
	MyRecycleViewAdapter adapter;

	
	//文件访问对象
	private DataFileAccess mDFA = new DataFileAccess(MainActivity.this);
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		recycleView=(RecyclerView) findViewById(R.id.rollimg);
		adapter=new MyRecycleViewAdapter(this, ImgItem_content.imgItemList);
		Log.d("firstpage", "imglistsize: "+ImgItem_content.imgItemList.size());
		/*配置layoutmanger
		 */
		RecyclerView.LayoutManager layoutmanager=new LinearLayoutManager(this);
		((LinearLayoutManager) layoutmanager).setOrientation(LinearLayoutManager.HORIZONTAL);
		recycleView.setLayoutManager(layoutmanager);

		recycleView.setAdapter(adapter);


        //////////
		mAppInstance = (MyApplication)getApplication(); 
    	mAppInstance.g_context=getApplicationContext();
    	
    	mAppInstance.g_user = mDFA.ReadUserInfofromFile("userinfo.txt");
    	if (mAppInstance.g_user == null)
    		mAppInstance.g_user = new MyUser(); //读入失败则创建新用户
    	mAppInstance.g_order = new Orders();//创建订单列表
    	CopyDishImagesFromRawToSD(); //将RAW文件夹中的菜品图像复制到SD卡的指定文件夹中
    	
    	mAppInstance.g_dbAdepter = new DBAdapter(this);
    	mAppInstance.g_dbAdepter.open();
    	mAppInstance.g_dbAdepter.deleteAllData();//清除原有菜品数据
    	ArrayList<Dish> dishes = FillDishesList(); //将菜品列表保存在内存dishes表中
    	//将菜品从dishes表中填充进数据库
    	mAppInstance.g_dbAdepter.FillDishTable(dishes);


    	
    	FButton imgBtnRest = (FButton) findViewById(R.id.imgBtnRest);
		mImgBtnLogin= (FButton)findViewById(R.id.imgBtnLogin);
		mImgBtnLogout = (FButton) findViewById(R.id.imgBtnLogout);
		imgBtndingdan=(FButton)findViewById(R.id.imgBtndingdan);
    	//ImageButton imgBtnTakeout = (ImageButton)findViewById(R.id.imgBtnTakeout);
		/*  这些按钮被取消掉
    	FButton imgBtnUserInfo = (FButton) findViewById(R.id.imgBtnUserInfo);
    	ImageButton imgBtnMyOrderes = (ImageButton)findViewById(R.id.imgBtnMyOrderes);
    	mImgBtnLogout = (ImageButton)findViewById(R.id.imgBtnLogout);
    	*/
    	
    	//将各图像按钮注册到myImageButton点击事件监听器
		//imgBtnMyOrderes.setOnClickListener(new myImageButtonListener()); ////////查看订单按钮被取消掉
    	//imgBtnTakeout.setOnClickListener(new myImageButtonListener());
    	//imgBtnUserInfo.setOnClickListener(new myImageButtonListener());///////用户信息按钮被取消掉
    	imgBtnRest.setOnClickListener(new myImageButtonListener());
    	mImgBtnLogin.setOnClickListener(new myImageButtonListener());
		mImgBtnLogout.setOnClickListener(new myImageButtonListener());
		//imgBtnorder.setOnClickListener(new myImageButtonListener());
		imgBtndingdan.setOnClickListener(new myImageButtonListener());

    }

    //使用SharedPreferences读取用户信息
    private String LoadUserPreferencesName()
    {
    	int mode = Activity.MODE_PRIVATE;
    	//获取SharedPreferences对象
    	SharedPreferences usersetting = getSharedPreferences(mUserFileName, mode);
		String username = usersetting.getString("username", "");
		return username;
    }
    
    private boolean CopyDishImagesFromRawToSD()
    {
    	if (mDFA.SDCardState())//检查SD卡是否可用
    	{
        	//在SD卡中创建存放菜品图像的指定文件夹
    		if (!mDFA.isFileExist(mAppInstance.g_imgDishImgPath)) {
    			//文件夹不存在，创建文件夹
    			mDFA.createSDDir(mAppInstance.g_imgDishImgPath);
    		}
    		//依次将将raw文件夹中的菜品图像依次复制到SD卡的指定文件夹中
    		String strDishImgName = mAppInstance.g_imgDishImgPath + "/" + "food01gongbaojiding.jpg";
    		if (!(mDFA.isFileExist(strDishImgName)))
    			//将raw文件夹中的food01gongbaojiding.jpg文件拷贝至SD卡指定文件夹中
    			mDFA.CopyRawFilestoSD(R.raw.food01gongbaojiding, strDishImgName);
    		strDishImgName = mAppInstance.g_imgDishImgPath + "/" + "food02jiaoyanyumi.jpg";
    		if (!(mDFA.isFileExist(strDishImgName)))
    			//将raw文件夹中的food02jiaoyanyumi.jpg文件拷贝至SD卡指定文件夹中
    			mDFA.CopyRawFilestoSD(R.raw.food02jiaoyanyumi, strDishImgName);    		
    		strDishImgName = mAppInstance.g_imgDishImgPath + "/" + "food03qingzhengwuchangyu.jpg";
    		if (!(mDFA.isFileExist(strDishImgName)))
    			//将raw文件夹中的food03qingzhengwuchangyu.jpg文件拷贝至SD卡指定文件夹中
    			mDFA.CopyRawFilestoSD(R.raw.food03qingzhengwuchangyu, strDishImgName);    	
    		strDishImgName = mAppInstance.g_imgDishImgPath + "/" + "food04yuxiangrousi.jpg";
    		if (!(mDFA.isFileExist(strDishImgName)))
    			//将raw文件夹中的food04yuxiangrousi.jpg文件拷贝至SD卡指定文件夹中
    			mDFA.CopyRawFilestoSD(R.raw.food04yuxiangrousi, strDishImgName);
    		
    		return true;
    	}
    	return false;
    }
    
    private ArrayList<Dish> FillDishesList()
		{
    	String imgPath = mDFA.SDCardPath() + "/" + mAppInstance.g_imgDishImgPath + "/";
    	ArrayList<Dish> theDishesList = new ArrayList<Dish>();
    	Dish theDish = new Dish();
    	//添加菜品
    	theDish.mId = 1;
    	theDish.mName = "宫保鸡丁";
    	theDish.mPrice = (float) 20.0;
    	theDish.mImage = (R.raw.food01gongbaojiding);
    	theDish.mImageName = imgPath + "food01gongbaojiding.jpg";
    	theDishesList.add(theDish);
    	
    	theDish = new Dish();
    	theDish.mId = 2;
    	theDish.mName = "椒盐玉米";
    	theDish.mPrice = (float) 24.0;
    	theDish.mImage = (R.raw.food02jiaoyanyumi);
    	theDish.mImageName = imgPath + "food02jiaoyanyumi.jpg";
    	theDishesList.add(theDish);
    	
    	theDish = new Dish();
    	theDish.mId = 3;
    	theDish.mName = "清蒸武昌鱼";
    	theDish.mPrice = (float) 48.0;
    	theDish.mImage = (R.raw.food03qingzhengwuchangyu);
       	theDish.mImageName = imgPath + "food03qingzhengwuchangyu.jpg";
    	theDishesList.add(theDish);
    	
    	theDish = new Dish();
    	theDish.mId = 4;
    	theDish.mName = "鱼香肉丝";
    	theDish.mPrice = (float) 20.0;
    	theDish.mImage = (R.raw.food04yuxiangrousi);
       	theDish.mImageName = imgPath + "food04yuxiangrousi.jpg";
    	theDishesList.add(theDish);
    	return theDishesList;
    }

	public class myImageButtonListener implements View.OnClickListener
    {
		@Override
		public void onClick(View v) {
			switch (v.getId())
			{
				case R.id.imgBtndingdan:
					if (!mAppInstance.g_user.mIslogined)
					{
						//用户未登录,提示用户登录
						Toast.makeText(MainActivity.this, "请先登录!", Toast.LENGTH_LONG).show();
					}
					else
					{
						Intent intent = new Intent(MainActivity.this, OrderedActivity.class);
						startActivity(intent);
					}
					return;
			case R.id.imgBtnRest:
				if (!mAppInstance.g_user.mIslogined)
				{
					//用户未登录,提示用户登录
					Toast.makeText(MainActivity.this, "请先登录!", Toast.LENGTH_LONG).show();
				}

				else
				{
					Intent intent = new Intent(MainActivity.this, TabhostActivity.class);
					startActivity(intent);
				}
				return;

				case R.id.imgBtnLogin://用户未登录时该按钮才会出现
				//用户未登录，显示登录对话框让用户登录
				final LoginDialog loginDlg = new LoginDialog(MainActivity.this);
		    	//从SharedPreferences中载入用户名
		    	String holdName = LoadUserPreferencesName();
				loginDlg.DisplayUserName(holdName);
				loginDlg.show();
				//对话框销毁时的响应事件
				loginDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						switch (loginDlg.mBtnClicked)
						{
						case BUTTON_OK://用户点击了“确定”按钮
							MyApplication appInstance = (MyApplication)getApplication();
							//判断用户名及密码是否符合
							if (appInstance.g_user.mUserid.equals(loginDlg.mUserId) && 
									appInstance.g_user.mPassword.equals(loginDlg.mPsword)) {
								//用户登录成功
								appInstance.g_user.mIslogined = true;
								//隐藏“登录”按钮，显示“注销”按钮
								mImgBtnLogin.setVisibility(Button.GONE);
								mImgBtnLogout.setVisibility(Button.VISIBLE);
								//创建该用户的购物车
								appInstance.g_cart = new ShoppingCart(appInstance.g_user.mUserid);					
								//使用SharedPreferences保存用户名
								int mode = Activity.MODE_PRIVATE;//定义权限为私有
								//(1)获取SharedPreferences对象
								SharedPreferences usersetting = getSharedPreferences(mUserFileName, mode);
								//（2）获得Editor类a
								SharedPreferences.Editor ed = usersetting.edit();
								if (loginDlg.mIsHoldUserId)	{
									//(3)添加用户名数据
									ed.putString("username", appInstance.g_user.mUserid);	
								}
								else {
									//保存空的用户名(清除)
									ed.putString("username", "");
								}
								ed.commit();//保存键值对
								Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_LONG).show();
							}
							else {
								Toast.makeText(MainActivity.this, "用户名或者密码错误", Toast.LENGTH_LONG).show();
							}
							break;
						case BUTTON_REGISTER://用户点击了“注册”按钮
							//跳转到登陆页面
							Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
							startActivityForResult(intent, REGISTERACTIVITY);
							break;
						}
					}
				});
				return;



			case R.id.imgBtnLogout: //用户登录后该按钮才会出现
				mAppInstance.g_user.mIslogined = false;
				//隐藏“注销”按钮，显示“登录”按钮
				mImgBtnLogout.setVisibility(Button.GONE);
				mImgBtnLogin.setVisibility(Button.VISIBLE);		
				return;
			}
			
		}
    }
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
		case REGISTERACTIVITY:
			if (resultCode==Activity.RESULT_OK){
				//获得RegisterActivity封装在intent中的数据
				MyUser userInfo = new MyUser();
				userInfo.mUserid = data.getStringExtra("user");
				userInfo.mPassword = data.getStringExtra("password");
				userInfo.mUserphone = data.getStringExtra("phone");
				userInfo.mUseraddress = data.getStringExtra("address");
				//将用户信息保存到默认文件夹中
				String filename = "userinfo.txt";
				mDFA.SaveUserInfotoFile(filename, userInfo);
				mAppInstance.g_user = mDFA.ReadUserInfofromFile(filename);
				Toast.makeText(MainActivity.this, "已保存并读取!", Toast.LENGTH_LONG).show();
			}
			break;
		}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()){
				case R.id.action_exit:
					break;
				case R.id.action_setting:
					break;
			}
		return super.onOptionsItemSelected(item);
	}  
    
}
