package edu.cqut.MobileOrderFood.orderpage;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.cqut.MobileOrderFood.MainActivity;
import edu.cqut.MobileOrderFood.MyApplication;
import edu.cqut.MobileOrderFood.OrderItem;
import edu.cqut.MobileOrderFood.OrderOneDialog;
import edu.cqut.MobileOrderFood.OrderedActivity;
import edu.cqut.MobileOrderFood.R;

public class OrderRecycleviewAdapter extends RecyclerView.Adapter<OrderRecycleviewAdapter.MyHolder> {

    Context context;
    List<OrderItem> list;
    private OnItemClickListener itemClickListener;


    public OrderRecycleviewAdapter(Context context, List<OrderItem> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recycleview_item, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        OrderItem item = list.get(position);
        holder.mealname.setText(item.mOneDish.mName);
        Bitmap bitmap = BitmapFactory.decodeFile(item.mOneDish.mImageName);
        holder.imageView.setImageBitmap(bitmap);
        holder.price.setText(String.valueOf(item.mOneDish.mPrice));
        holder.count.setText(String.valueOf(item.mQuantity));


        holder.myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null)
                    itemClickListener.onItemClick(list.get(position),position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        View myview;
        ImageView imageView;
        TextView mealname;
        TextView price;
        TextView count;

        public MyHolder(View itemView) {
            super(itemView);
            myview = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.orderimag);
            mealname = (TextView) itemView.findViewById(R.id.mealname);
            price = (TextView) itemView.findViewById(R.id.price);
            count = (TextView) itemView.findViewById(R.id.count);


        }
    }

    //点击事件接口
    public interface OnItemClickListener{
        void onItemClick(OrderItem item,int position);
    }

    //设置点击方式的方法
    public void SetItemClickListener(OnItemClickListener itemlistener)
    {
        this.itemClickListener=itemlistener;
    }
}