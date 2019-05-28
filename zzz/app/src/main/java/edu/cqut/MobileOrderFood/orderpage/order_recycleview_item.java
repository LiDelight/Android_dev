package edu.cqut.MobileOrderFood.orderpage;

public class order_recycleview_item {
    private String mealname;
    private int mealimg;

    public order_recycleview_item(String mn, int mi)
    {
        this.mealname=mn;
        this.mealimg=mi;
    }

    public String getMealname()
    {
        return mealname;
    }

    public int getMealimg()
    {
        return mealimg;
    }
}
