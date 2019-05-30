package edu.cqut.MobileOrderFood;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import edu.cqut.MobileOrderFood.orderpage.OrderRecycleviewAdapter;

public class OrderedActivity extends Activity {


	public TextView mtvTotalPrice = null;
	final MyApplication appInstance=(MyApplication)getApplication();

	/*增加部件*/
	RecyclerView OrderRecycle;
	public List<OrderItem> mDatas;
	OrderRecycleviewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ordered);
		mtvTotalPrice = (TextView)findViewById(R.id.totalprice);
		Button mBtnSumit = (Button)findViewById(R.id.submit_ok);

		OrderRecycle=(RecyclerView) findViewById(R.id.order_recycleview);

		LinearLayoutManager layoutManager=new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        OrderRecycle.setLayoutManager(layoutManager);


		//设置取消按钮的监听器
		mBtnSumit.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 用户点击了“提交订单”按钮
				MyApplication appInstance = (MyApplication)getApplication();
				String strOrderMsg = null;
				if (appInstance.g_cart.GetOrderItemsQuantity() <= 0)
				{
					Toast.makeText(OrderedActivity.this, "还没选购任何菜品", Toast.LENGTH_LONG).show();
					return;
				}
			//	if (appInstance.isWifiEnabled(OrderedActivity.this) == false)
				//{
				//	Toast.makeText(OrderedActivity.this, "Wifi不可用，请开启Wifi!", Toast.LENGTH_LONG).show();
				//	return;
				//}

				//构造发送到服务器的订单信息
				//格式为：4:用户名:餐位名:菜品编号1:数量:菜品编号2:数量...
				strOrderMsg = "4:" + appInstance.g_user.mUserid + ":"
						+ appInstance.g_user.mSeatname;
				for (int i=0; i<appInstance.g_cart.GetOrderItemsQuantity(); i++) {
					OrderItem item = appInstance.g_cart.GetItembyIndex(i);
					strOrderMsg += ":" + item.mOneDish.mId + ":" + item.mQuantity;
				}
				//将用户信息更新到服务器
				String revMsg = appInstance.SendMessageToServer(strOrderMsg);
				if (revMsg.equals("AddFail"))
				{
					Toast.makeText(OrderedActivity.this, revMsg, Toast.LENGTH_LONG).show();
				}
				else if (revMsg.equals("Not set Server IP"))
					Toast.makeText(OrderedActivity.this, "服务器IP地址未设置!", Toast.LENGTH_LONG).show();
				else
				{
					//订购成功
					String[] sep = revMsg.split(":");

							//创建订单
					Order theOrder = new Order(appInstance.g_cart);
					//将订单加入到订单列表
					appInstance.g_order.morders.add(theOrder);
					//提示用户订单生成信息
					String strOrderInfo = "订单"+theOrder.mId+"提交成功, 时间: "+theOrder.mOrderTime;
					strOrderInfo = ", 就餐方式: 外卖";
					strOrderInfo += ", 就餐时间: "+theOrder.mOrderTime;
					Toast.makeText(OrderedActivity.this, strOrderInfo, Toast.LENGTH_LONG).show();
					//更新购物列表
					//appInstance.g_cart.ClearAllDishes();
					UpdateOrderList();
				}
			}
		});

	}

	private ArrayList<OrderItem> getOrderedDishData()
	{
		ArrayList<OrderItem> orderDishData=new ArrayList<OrderItem>();
		//将菜品信息填充进foodinfo列表
		MyApplication appInstance = (MyApplication)getApplication();
		int s = appInstance.g_cart.GetOrderItemsQuantity(); //得到已点菜品种类的数量
		for (int i=0; i<s; i++) {
			OrderItem theItem = appInstance.g_cart.GetItembyIndex(i); //得到当前已点菜品种类项
			orderDishData.add(theItem);
		}
		return orderDishData;		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ordered, menu);
		return true;
	}

	@Override
	protected void onResume() {
		//该函数在页面每次显示时自动调用
		super.onResume();
		//将购物车中已点菜品列表(重新)填充进mlistItemAdapter适配器中，更新显示列表，再次计算价格
		UpdateOrderList();
	}
	
	public void UpdateOrderList()
	{
		mDatas=getOrderedDishData();
		//SimpleAdapter适配器，将它和自定义的布局文件、List数据源关联
		adapter = new OrderRecycleviewAdapter(this,mDatas);

		adapter.SetItemClickListener(new OrderRecycleviewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(OrderItem item, int position) {
				final OrderOneDialog orderDlg = new OrderOneDialog(OrderedActivity.this);
				final String dishName=item.mOneDish.mName;
				orderDlg.setTitle(dishName);
				orderDlg.show();
				//对话框销毁时的响应事件
				orderDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						if (orderDlg.mBtnClicked == OrderOneDialog.ButtonID.BUTTON_OK) {
							//							//修改购物车中的已点菜品
							MyApplication appInstance = (MyApplication)getApplication();
							if (orderDlg.mNum <= 0) //如果该菜品数量为0，则将该菜品从已点菜单中删除
								appInstance.g_cart.DeleteOneOrderItem(dishName);
							else //修改购物车中该菜品的数量
								appInstance.g_cart.ModifyOneOrderItem(dishName, orderDlg.mNum);
							Toast.makeText(OrderedActivity.this, dishName+":"
									+orderDlg.mNum, Toast.LENGTH_LONG).show();
							//将购物车中已点菜品列表(重新)填充进mlistItemAdapter适配器中，更新显示列表，再次计算价格
							UpdateOrderList();
						}
					}
				});
			}
		});



	    OrderRecycle.setAdapter(adapter);
	   //计算已点菜品的总价
		MyApplication appInstance = (MyApplication)getApplication();  //删了不知道会有什么影响
		float tf = appInstance.g_cart.GetCartTotalPrice();
		//将总价保留小数点后两位，将它转换为字符串后再显示
		DecimalFormat fnum = new DecimalFormat("##0.00");
		String dd=fnum.format(tf);
		mtvTotalPrice.setText(dd);
	}
}
