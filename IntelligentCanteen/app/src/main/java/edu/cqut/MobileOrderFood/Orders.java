package edu.cqut.MobileOrderFood;
import java.util.ArrayList;




//订单列表
public class Orders
{
   public ArrayList<Order> morders;//订单链表
    public String mUserName; //总订单所属用户的用户名

    Orders()
    {
        morders = new ArrayList<Order>();
    }

    Orders(String userName, ArrayList<Order> orderitems)
    {
        mUserName = userName;
        morders = orderitems;
    }

    public int GetOrdersQuantity()
    {
        int s = morders.size();
        return s;
    }

    public Order GetorderbyIndex(int i)
    {
        return morders.get(i);
    }

    public boolean DeleteorderByIndex(int i)
    {
        int s = morders.size();
        if (i>=0 && i<s) {
            morders.remove(i);
            return true;
        }
        return false;
    }

}
