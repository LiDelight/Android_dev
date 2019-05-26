package edu.cqut.MobileOrderFood;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class RegisterActivity extends Activity {

	public EditText metId, metPsword, metAffirmPsword, metPhone, metAddress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		metId = (EditText)findViewById(R.id.etRegisterUserId);
		metPsword = (EditText)findViewById(R.id.etRegisterUserPsword);
		metAffirmPsword = (EditText)findViewById(R.id.etRegisterUserAffirmPsword);
		metPhone = (EditText)findViewById(R.id.etRegisterUserMobilePhone);
		metAddress = (EditText)findViewById(R.id.etRegisterUserAddress);
		Button btnOK = (Button)findViewById(R.id.btnRegister);
		Button btnCancel = (Button)findViewById(R.id.btnCancel);
		
		Button.OnClickListener mybtnListener = new Button.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				switch (v.getId())
				{
				case R.id.btnCancel:
					finish();
					break;
				case R.id.btnRegister:
					String strPsword = metPsword.getText().toString();
					String strAffirmPsword = metAffirmPsword.getText().toString();
					if (strPsword.equals(strAffirmPsword))
					{
						//通过Intent将用户注册信息返回给父Activity，这里即MainActivity.
						Uri info = Uri.parse("用户注册信息");
						Intent intentUserInfo = new Intent(null, info);
						intentUserInfo.putExtra("user", metId.getText().toString());
						intentUserInfo.putExtra("password", metPsword.getText().toString());
						intentUserInfo.putExtra("phone", metPhone.getText().toString());
						intentUserInfo.putExtra("address", metAddress.getText().toString());
						setResult(RESULT_OK, intentUserInfo);
						finish();
					}
					else
					{
						Toast.makeText(RegisterActivity.this, 
								"两次密码输入不一致，请重新输入密码!", Toast.LENGTH_LONG).show();
						//清空密码输入框
						metPsword.setText("");
						metAffirmPsword.setText("");
						//让密码输入框获得焦点
						metPsword.setFocusable(true);
						metPsword.setFocusableInTouchMode(true);
						metPsword.requestFocus();
					}
				}
				
			}
		};
		
		btnOK.setOnClickListener(mybtnListener);
		btnCancel.setOnClickListener(mybtnListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
