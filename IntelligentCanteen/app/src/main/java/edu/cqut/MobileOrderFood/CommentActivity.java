package edu.cqut.MobileOrderFood;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;
import android.app.TabActivity;
import android.content.Intent;

@SuppressWarnings("deprecation")
public class CommentActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("Caipin").
                setIndicator("所有订单").setContent(new Intent().setClass(this, Comment1Activity.class)));
        tabHost.addTab(tabHost.newTabSpec("Order").
                setIndicator("已评价订单").setContent(new Intent().setClass(this, OrderedActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tabhost, menu);
        return true;
    }

}
