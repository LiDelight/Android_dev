package edu.cqut.MobileOrderFood;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.cqut.MobileOrderFood.fragment.MyItemRecyclerViewAdapter;
import edu.cqut.MobileOrderFood.fragment.dummy.DummyContent;

public class ItemFragment extends Fragment {

    private int mColumnCount = 1;

    public ItemFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            MyItemRecyclerViewAdapter myItemRecyclerViewAdapter=new MyItemRecyclerViewAdapter(DummyContent.ITEMS);
            recyclerView.setAdapter(myItemRecyclerViewAdapter);


           /*
                    //对话框销毁时的响应事件
                    orderDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (orderDlg.mBtnClicked == OrderOneDialog.ButtonID.BUTTON_OK) {
                                //将菜品加入到购物车中
                                long dishId = Long.parseLong(tvId.getText().toString());
                                Dish newDish = appInstance.g_dbAdepter.queryOneData(dishId);
                                appInstance.g_cart.AddOneOrderItem(newDish, orderDlg.mNum);
                                Toast.makeText(CaipinActivity.this, tvTitle.getText().toString()+":"
                                        +orderDlg.mNum, Toast.LENGTH_LONG).show();
                      */
        }
        return view;
    }
}
