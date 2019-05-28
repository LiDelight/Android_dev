package edu.cqut.MobileOrderFood.orderpage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.cqut.MobileOrderFood.R;

public class OrderRecycleviewAdapter extends RecyclerView.Adapter<OrderRecycleviewAdapter.MyHolder> {

    Context context;
    List<order_recycleview_item> list;

    public OrderRecycleviewAdapter(Context context,List<order_recycleview_item> list)
    {
        this.context=context;
        this.list=list;
    }


@Override
public MyHolder onCreateViewHolder(ViewGroup parent, int viewType ){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recycleview_item,parent,false);
        MyHolder myHolder=new MyHolder(view);
        return myHolder;
}

@Override
public void onBindViewHolder(MyHolder holder, int position){
        order_recycleview_item item=list.get(position);
        holder.textView.setText(item.getMealname());
        holder.imageView.setImageResource(item.getMealimg());
}

@Override
public int getItemCount()
{
    return list.size();
}



    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public MyHolder(View itemView)
        {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.orderimag);
            textView=(TextView)itemView.findViewById(R.id.mealname);

        }
    }
}
