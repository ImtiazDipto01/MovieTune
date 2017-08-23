package com.example.dipto.movietune.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dipto.movietune.Constant;
import com.example.dipto.movietune.R;
import com.example.dipto.movietune.model.TopRatedModel;
import com.example.dipto.movietune.model.UpcomingModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dipto on 8/22/2017.
 */

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder> {

    private final LayoutInflater inflater;
    private Context context ;
    private List<UpcomingModel> list = Collections.emptyList() ;
    private ClickListener clickListener ;

    public UpcomingAdapter(Context context, List<UpcomingModel> list){
        this.context = context ;
        this.list = list ;
        inflater = LayoutInflater.from(context) ;
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener ;
    }

    @Override
    public UpcomingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_upcoming, parent, false) ;
        UpcomingViewHolder upcomingViewHolder = new UpcomingViewHolder(view) ;
        return upcomingViewHolder;
    }

    @Override
    public void onBindViewHolder(UpcomingViewHolder holder, int position) {

        UpcomingModel upcomingModel = list.get(position) ;
        //holder.upcomingimg.setImageResource(upcomingModel.getMovie_local());
        Glide.with(context).load(Constant.BASE_IMAGE_URL+upcomingModel.getMovie_poster()).placeholder(R.drawable.dd).into(holder.upcomingimg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class UpcomingViewHolder extends RecyclerView.ViewHolder{

        ImageView upcomingimg ;

        public UpcomingViewHolder(View itemView) {
            super(itemView);
            upcomingimg = (ImageView) itemView.findViewById(R.id.upcoming_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListener != null){
                        clickListener.itemClicked(v, getPosition());
                    }
                }
            });
        }
    }
    public interface ClickListener{
        public void itemClicked(View view, int position) ;
    }
}
