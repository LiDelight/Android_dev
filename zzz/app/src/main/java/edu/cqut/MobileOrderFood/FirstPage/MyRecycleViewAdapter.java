package edu.cqut.MobileOrderFood.FirstPage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import edu.cqut.MobileOrderFood.R;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {

    Context context;
    List<ImgItem_content.ImgItem> imglist;
    public MyRecycleViewAdapter(Context context,List<ImgItem_content.ImgItem> list){
        this.context=context;
        this.imglist=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.firstpage_recycleview_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.image.setImageResource(imglist.get(position).firstpage_img);
    }

   @Override
   public int getItemCount()
   {
       return imglist.size();
   }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;

        public ViewHolder(View view)
        {
            super(view);
            image=(ImageView) view.findViewById(R.id.fisrtpage_image);

        }

    }

    public void update (List<ImgItem_content.ImgItem> list){
        this.imglist=list;
        notifyDataSetChanged();
    }





}
