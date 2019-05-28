package edu.cqut.MobileOrderFood;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class UserInfoActivity extends Activity {

	private DataFileAccess mDFA = new DataFileAccess(UserInfoActivity.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		
		TextView tvUserId = (TextView)findViewById(R.id.textUserId);
		final EditText etPsword = (EditText)findViewById(R.id.etLoginPswrod);
		final EditText etPhone = (EditText)findViewById(R.id.etUserPhone);
		final EditText etAddress = (EditText)findViewById(R.id.etUserAddress);
		Button btnModify = (Button)findViewById(R.id.btnModify);
		Button btnReturn = (Button)findViewById(R.id.btnReturn);
		
		//获得存储在全局变量中的用户信息
		final MyApplication appInstance = (MyApplication)getApplication(); 
		tvUserId.setText(appInstance.g_user.mUserid);
		etPhone.setText(appInstance.g_user.mUserphone);
		etAddress.setText(appInstance.g_user.mUseraddress);
		
		//设置按钮点击监听器
		btnModify.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//修改信息前检测用户输入的密码和系统全局变量中存储的密码是否一致
				if (etPsword.getText().toString().equals(appInstance.g_user.mPassword))
				{
					appInstance.g_user.mUserphone = etPhone.getText().toString();
					appInstance.g_user.mUseraddress = etAddress.getText().toString();
					//将修改后的用户信息保存到userinfo.txt文件
					mDFA.SaveUserInfotoFile("userinfo.txt", appInstance.g_user);
					finish();
				}
				else
				{
					Toast.makeText(UserInfoActivity.this, "请输入登录密码!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		btnReturn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info, menu);
		return true;
	}

}
