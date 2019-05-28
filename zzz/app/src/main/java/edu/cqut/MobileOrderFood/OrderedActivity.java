package edu.cqut.MobileOrderFood;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import edu.cqut.MobileOrderFood.orderpage.OrderRecycleviewAdapter;
import edu.cqut.MobileOrderFood.orderpage.order_recycleview_item;

public class OrderedActivity extends Activity {


	static List<Map<String, Object>> morderedinfo;
	public ListView mlistview;
	static SimpleAdapter mlistItemAdapter;
	public TextView mtvTotalPrice = null;
	final MyApplication appInstance=(MyApplication)getApplication();

	/*增加部件*/
	RecyclerView OrderRecycle;
	public List<order_recycleview_item> mDatas;
	OrderRecycleviewAdapter adapter;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ordered);
		mtvTotalPrice = (TextView)findViewById(R.id.ordertotalprice);
		mlistview = (ListView) findViewById(R.id.OrderedListview);

		/*增加部分的初始化*/
		OrderRecycle=(RecyclerView)findViewById(R.id.order_recycleview);

		mDatas = new ArrayList<order_recycleview_item>();
		for (int i = 1; i <= 5; i++) {
			mDatas.add(new order_recycleview_item("宫保鸡丁",R.raw.food01gongbaojiding));
		}

		adapter=new OrderRecycleviewAdapter(this,mDatas);

		LinearLayoutManager layoutManager=new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);



		//设置ListView选项点击监听器
		this.mlistview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, //选项所属的ListView
											  View arg1, //被选中的控件，即ListView中被选中的子项
											   int arg2, //被选中子项在ListView中的位置
											  long arg3) //被选中子项的行号
			{
			     ListView templist = (ListView)arg0;  
			     View mView = templist.getChildAt(arg2);//选中子项(即item)在listview中的位置
			     final TextView tvTitle = (TextView)mView.findViewById(R.id.ordertitle);
				Button submit_ok = (Button)findViewById(R.id.submit_ok);
				Button submit_cancel = (Button)findViewById(R.id.submit_cancel);
				Button.OnClickListener buttonListener=new Button.OnClickListener(){
					@Override
					public void onClick(View v) {
						switch(v.getId()) {
							case R.id.submit_ok:
								//long dishId = Long.parseLong(tvId.getText().toString());
								//得到订单
								appInstance.g_orders.add(new Order(1,appInstance.g_cart,"2019.5.20"));

								//appInstance.g_cart.AddOneOrderItem(newDish, orderDlg.mNum);
								//Toast.makeText(CaipinActivity.this, tvTitle.getText().toString()+":"
								//		+orderDlg.mNum, Toast.LENGTH_LONG).show();
								//mUserName = etName.getText().toString();//评论信息
								//	mPaswrd = edPaswrd.getText().toString();
								break;
							case R.id.btnCancel:
								break;
						}
					}
				};



				//创建购买数量对话框
				final OrderOneDialog orderDlg = new OrderOneDialog(OrderedActivity.this);
				orderDlg.setTitle(tvTitle.getText().toString());
				orderDlg.show();
				//对话框销毁时的响应事件
				orderDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						if (orderDlg.mBtnClicked == OrderOneDialog.ButtonID.BUTTON_OK) {
							//修改购物车中的已点菜品
							MyApplication appInstance = (MyApplication)getApplication();
							String dishName = tvTitle.getText().toString();
							if (orderDlg.mNum <= 0) //如果该菜品数量为0，则将该菜品从已点菜单中删除
								appInstance.g_cart.DeleteOneOrderItem(dishName);
							else //修改购物车中该菜品的数量
								appInstance.g_cart.ModifyOneOrderItem(dishName, orderDlg.mNum);
							Toast.makeText(OrderedActivity.this, tvTitle.getText().toString()+":"
													+orderDlg.mNum, Toast.LENGTH_LONG).show();
							//将购物车中已点菜品列表(重新)填充进mlistItemAdapter适配器中，更新显示列表，再次计算价格
							UpdateOrderList();
						}					
					}
				});
			}
		});
	}

	private ArrayList<Map<String, Object>> getOrderedDishData()
	{
		ArrayList<Map<String, Object>> orderDishData=new ArrayList<Map<String,Object>>();  
		//将菜品信息填充进foodinfo列表
		MyApplication appInstance = (MyApplication)getApplication();
		int s = appInstance.g_cart.GetOrderItemsQuantity(); //得到已点菜品种类的数量
		for (int i=0; i<s; i++) {
			OrderItem theItem = appInstance.g_cart.GetItembyIndex(i); //得到当前已点菜品种类项
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", theItem.mOneDish.mName);
			map.put("price", theItem.mOneDish.mPrice);
			map.put("num", theItem.mQuantity);
			map.put("itemprice", theItem.GetItemTotalPrice());			
			orderDishData.add(map);
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
	
	private void UpdateOrderList()
	{
		morderedinfo=getOrderedDishData();
		//SimpleAdapter适配器，将它和自定义的布局文件、List数据源关联
		mlistItemAdapter = new SimpleAdapter(this,morderedinfo,//数据源   
	            R.layout.ordereditem,//ListItem的XML实现  
	            //动态数组与ImageItem对应的子项          
	            new String[] {"title", "price", "num", "itemprice"},   
	            //ImageItem的XML文件里面的1个ImageView,3个TextView ID  
	            new int[] {R.id.ordertitle, R.id.orderprice, R.id.ordernum, R.id.itemprice}); 		
		
//		mlistItemAdapter.notifyDataSetChanged();
		mlistview.setAdapter(mlistItemAdapter);
		//计算已点菜品的总价
		MyApplication appInstance = (MyApplication)getApplication();
		float tf = appInstance.g_cart.GetCartTotalPrice();
		//将总价保留小数点后两位，将它转换为字符串后再显示
		DecimalFormat fnum = new DecimalFormat("##0.00"); 
		String dd=fnum.format(tf); 
		mtvTotalPrice.setText(dd);
	}
}
