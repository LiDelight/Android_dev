package edu.cqut.MobileOrderFood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class dingdanActivity extends Activity {

    static List<Map<String, Object>> mfoodinfo;
    public ListView mlistview;
    static SimpleAdapter mlistItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan);
        mlistview = (ListView) findViewById(R.id.ListViewCainpin);
        final MyApplication appInstance = (MyApplication)getApplication();
        mfoodinfo = appInstance.g_dbAdepter.getDishData();
        //构造SimpleAdapter适配器，将它和自定义的布局文件、List数据源关联

        mlistItemAdapter = new SimpleAdapter(this,mfoodinfo,//数据源
                R.layout.listitem,//ListItem的XML实现
                //动态数组与ImageItem对应的子项
                new String[] {"dishid","image","title", "price"},
                //ImageItem的XML文件里面的1个ImageView,2个TextView的ID
                new int[] {R.id.dishid, R.id.img, R.id.title, R.id.price});

        mlistItemAdapter.notifyDataSetChanged();
        mlistview.setAdapter(mlistItemAdapter);
        //设置ListView选项点击监听器
        this.mlistview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, //选项所属的ListView
                                    View arg1, //被选中的控件，即ListView中被选中的子项
                                    int arg2, //被选中子项在ListView中的位置
                                    long arg3) //被选中子项的行号
                {
                   ListView templist = (ListView) arg0;
                    View mView = templist.getChildAt(arg2);//选中子项(即item)在listview中的位置
                    final TextView tvId = (TextView) mView.findViewById(R.id.dishid);
                    final TextView tvTitle = (TextView) mView.findViewById(R.id.title);

                }
        });
    }
}

