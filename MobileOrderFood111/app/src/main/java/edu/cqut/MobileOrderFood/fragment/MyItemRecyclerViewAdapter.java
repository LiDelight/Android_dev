package edu.cqut.MobileOrderFood.fragment;

import android.content.DialogInterface;
import android.media.Image;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.cqut.MobileOrderFood.CaipinActivity;
import edu.cqut.MobileOrderFood.Dish;
import edu.cqut.MobileOrderFood.MainActivity;
import edu.cqut.MobileOrderFood.MyApplication;
import edu.cqut.MobileOrderFood.OrderOneDialog;
import edu.cqut.MobileOrderFood.OrderedActivity;
import edu.cqut.MobileOrderFood.R;
import edu.cqut.MobileOrderFood.TabhostActivity;
import edu.cqut.MobileOrderFood.fragment.dummy.DummyContent;
import edu.cqut.MobileOrderFood.fragment.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;

    public MyItemRecyclerViewAdapter(List<DummyItem> items) {
        mValues = items;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mImView.setImageResource(mValues.get(position).mImage);//设置图片为指定的资源
        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageView mImView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mImView = (ImageView) view.findViewById(R.id.imgview);
            mContentView = (TextView) view.findViewById(R.id.content);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final OrderOneDialog orderDlg = new OrderOneDialog(v.getContext());
                    orderDlg.setTitle(mContentView.getText().toString());
                    orderDlg.show();
                    orderDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (orderDlg.mBtnClicked == OrderOneDialog.ButtonID.BUTTON_OK) {
                                //将菜品加入到购物车中
                                long dishId = Long.parseLong(mIdView.getText().toString());
                                Dish newDish = MainActivity.mAppInstance.g_dbAdepter.queryOneData(dishId);
                                MainActivity.mAppInstance.g_cart.AddOneOrderItem(newDish, orderDlg.mNum);
                                Toast.makeText(v.getContext(), mContentView.getText().toString() + ":"
                                        + orderDlg.mNum, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                @Override
                public String toString() {
                    return super.toString() + " '" + mContentView.getText() + "'";
                }
            });


        }
    }
}
