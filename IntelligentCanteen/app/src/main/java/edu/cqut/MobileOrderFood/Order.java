package edu.cqut.MobileOrderFood;

import java.util.Date;
public class Order 
{
	public int mId = -1; //订单号
	public static int sequence=0;
	public ShoppingCart mOrderItems;//存放已点菜品的链表(已点菜品由购物车生成)
	public String mOrderTime; //订单生效时间
	public boolean judge;//判断是否已经评价
	public Order(String userid)
	{
		mOrderItems = new ShoppingCart(userid);
	}
	
	public Order(ShoppingCart cart)
	{
		mId = ++sequence;
		mOrderItems = cart;
		mOrderTime = new Date().toString();
		judge=false;
	}
}
