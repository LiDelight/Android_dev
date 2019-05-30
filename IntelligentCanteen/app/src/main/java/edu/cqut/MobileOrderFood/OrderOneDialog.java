package edu.cqut.MobileOrderFood;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.*;

public class OrderOneDialog  extends Dialog
{
	public enum ButtonID {BUTTON_NONE, BUTTON_OK, BUTTON_CANCEL};
	
	public int mNum = 0; //订购数量
	public ButtonID mBtnClicked=ButtonID.BUTTON_NONE;//指示哪个按钮被点击
	public Button mbtnOK=null;
	
	public OrderOneDialog(Context context) {
		super(context);
		setContentView(R.layout.orderonedialog);
		setCancelable(true);
		
		final TextView tvOrderNum = (TextView)findViewById(R.id.tvOrderNum);
		Button btnDecr = (Button)findViewById(R.id.btnSub);
		Button btnIncr = (Button)findViewById(R.id.btnAdd);
		Button btnOK = (Button)findViewById(R.id.order_dialog_ok);
		Button btnCancel = (Button)findViewById(R.id.order_dialog_cancel);
		
		Button.OnClickListener buttonListener = new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				//将显示的数量转换为整数数量
				int dispNum = Integer.parseInt(tvOrderNum.getText().toString());
				switch (v.getId()) {
				case R.id.btnSub:
					if (dispNum <= 0)
						break;
					else {
						dispNum--;
						tvOrderNum.setText(""+dispNum);
						break;
					}
				case R.id.btnAdd:
					dispNum++;
					tvOrderNum.setText(""+dispNum);
					break;
				case R.id.order_dialog_ok:
					mNum = dispNum;
					mBtnClicked = ButtonID.BUTTON_OK;
					dismiss();
					break;
				case R.id.order_dialog_cancel:
					mBtnClicked = ButtonID.BUTTON_CANCEL;
					dismiss();
					break;
				}
			}
		};
		
		btnDecr.setOnClickListener(buttonListener);
		btnIncr.setOnClickListener(buttonListener);
		btnOK.setOnClickListener(buttonListener);
		btnCancel.setOnClickListener(buttonListener);
	}

}
