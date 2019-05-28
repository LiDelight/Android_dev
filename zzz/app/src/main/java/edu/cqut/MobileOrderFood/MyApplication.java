package edu.cqut.MobileOrderFood;

import java.util.ArrayList;
import android.app.Application;
import android.content.Context;

public class MyApplication extends Application //该类用于保存全局变量
{
	MyUser g_user; //用户
    public ShoppingCart g_cart; //与登录用户相关联的购物车
	ArrayList<Order> g_orders; //与登录用户相关联的订单
//	Dishes g_dishes;  //菜品列表
    public String g_ip="";     //TCP通讯时店面服务器IP地址
    public String g_http_ip=""; //用HTTP通信时的店面服务器IP地址
    public int g_communiMode = 1; //通信模式，1为TCP通信，2为HTTP通信
    public int g_objPort = 35885;
    public static Context g_context;
    public  DBAdapter g_dbAdepter = null; //数据库辅助对象
	String g_imgDishImgPath="Android/data/edu.cqut.mobileorderfood/img"; //菜品图片路径
}
