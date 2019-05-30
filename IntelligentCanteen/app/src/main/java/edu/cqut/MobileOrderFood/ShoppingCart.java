package edu.cqut.MobileOrderFood;

import java.util.ArrayList;


public class ShoppingCart
{
	public String mUserName; //购物车所属用户的用户名
	private ArrayList<OrderItem> mOrderItems;//存放已点菜品的链表

	ShoppingCart()
	{

		mOrderItems = new ArrayList<OrderItem>();
	}

	ShoppingCart(String userName)
	{
		mUserName = userName;
		mOrderItems = new ArrayList<OrderItem>();
	}

	ShoppingCart(String userName, ArrayList<OrderItem> orderitems)
	{
		mUserName = userName;
		mOrderItems = orderitems;
	}

	public int GetOrderItemsQuantity()
	{
		int s = mOrderItems.size();
		return s;
	}

	public OrderItem GetItembyIndex(int i)
	{
		return mOrderItems.get(i);
	}

	public boolean DeleteItemByIndex(int i)
	{
		int s = mOrderItems.size();
		if (i>=0 && i<s) {
			mOrderItems.remove(i);
			return true;
		}
		return false;
	}

	public float GetCartTotalPrice() {
		float totalPrice = 0;
		if (!mOrderItems.isEmpty())
		{
			int s = mOrderItems.size();
			for (int i=0; i<s; i++)
				totalPrice += ((OrderItem)mOrderItems.get(i)).GetItemTotalPrice();
		}
		return totalPrice;
	}

	//根据菜品插入已点菜品链表中，返回插入菜品在链表中的索引
	public int AddOneOrderItem(Dish dish, int num)
	{
		int index = GetDishIndex(dish.mName); //查询该菜是否已点
		if (index == -1)
		{ //该菜没点
			if (num > 0)
			{ //将之插入到链表末尾
				OrderItem theItem = new OrderItem(dish, num);
				mOrderItems.add(theItem);
				return mOrderItems.size()-1;
			}
			else
				return -1;
		}
		else { //该菜已点
			if (num <=0 ) {//如果点餐数量小于等于0表示用户要删除该菜品
				DeleteOneOrderItem(dish.mName);
				return -1;
			}
			else { // 只需修改链表中相应菜的数量
				OrderItem theItem = new OrderItem(dish, num);
				mOrderItems.set(index, theItem);
				return index;
			}
		}
	}

	//根据菜名从已点菜品链表中将该菜品删除
	public void DeleteOneOrderItem(String dishName)
	{
		if (!mOrderItems.isEmpty()) {
			int s = mOrderItems.size();
			for (int i=0; i<s; i++) {
				String theName = ((OrderItem)mOrderItems.get(i)).mOneDish.mName;
				if (dishName.equals(theName)) {
					mOrderItems.remove(i);
					break;
				}
			}
		}
	}

	//根据菜名在已点菜品链表中修改该菜数量，返回修改菜品的菜品在购物车中的位置，当菜品在购物车中不存在时返回-1
	public int ModifyOneOrderItem(String dishName, int num)
	{
		if (!mOrderItems.isEmpty()) {
			int s = mOrderItems.size();
			for (int i=0; i<s; i++) {
				OrderItem theItem = (OrderItem)mOrderItems.get(i);
				if (dishName.equals(theItem.mOneDish.mName)) {
					theItem.mQuantity = num;
					mOrderItems.set(i, theItem);
					return i;
				}
			}
		}
		return -1;
	}

	//根据菜品名在已点菜品链表中查询该菜是否已点，返回已点菜品在链表中的位置,若没有返回-1
	private int GetDishIndex(String dishName)
	{
		if (!mOrderItems.isEmpty()) {
			int s = mOrderItems.size();
			for (int i=0; i<s; i++) {
				OrderItem theItem = (OrderItem)mOrderItems.get(i);
				if (dishName.equals(theItem.mOneDish.mName)) {
					return i;
				}
			}
		}
		return -1;
	}
}
