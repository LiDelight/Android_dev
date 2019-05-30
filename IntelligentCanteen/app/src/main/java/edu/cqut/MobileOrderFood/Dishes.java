package edu.cqut.MobileOrderFood;

import java.util.ArrayList;

public class Dishes 
{
	public ArrayList<Dish> mDishes;  //菜品列表
	
	public int GetDishQuantity()
	{
		return mDishes.size();
	}
	
	public Dish GetDishbyIndex(int i)
	{
		return mDishes.get(i);
	}
	
	public Dish GetDishbyName(String dishName)
	{
		int s = mDishes.size();
		for (int i=0; i<s; i++) {
			Dish theDish = mDishes.get(i);
			if (dishName.equals(theDish.mName)) {
				return theDish;
			}
		}
		return null;
	}
}
