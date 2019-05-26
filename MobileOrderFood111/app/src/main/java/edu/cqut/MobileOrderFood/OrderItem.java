package edu.cqut.MobileOrderFood;

public class OrderItem 
{
	public Dish mOneDish; //该订购细目中的一个菜品
	public int mQuantity=0; //该菜品的数量
	
	OrderItem(Dish theDish, int quantity)
	{
		mOneDish = theDish;
		mQuantity = quantity;
	}
	
	public float GetItemTotalPrice()
	{
		return mOneDish.mPrice * mQuantity;
	}
}
