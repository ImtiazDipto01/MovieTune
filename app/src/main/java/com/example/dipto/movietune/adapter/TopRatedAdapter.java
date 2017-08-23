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
import com.example.dipto.movietune.model.NewReleaseModel;
import com.example.dipto.movietune.model.TopRatedModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dipto on 8/22/2017.
 */

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.TopRatedViewHolder>{

    private final LayoutInflater inflater;
    private Context context ;
    private List<TopRatedModel> list = Collections.emptyList() ;
    private ClickListener clickListener ;

    public TopRatedAdapter(Context context, List<TopRatedModel> list){
        this.context = context ;
        this.list = list ;
        inflater = LayoutInflater.from(context) ;
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener ;
    }

    @Override
    public TopRatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_top_rated, parent, false) ;
        TopRatedViewHolder topRatedViewHolder = new TopRatedViewHolder(view) ;
        return topRatedViewHolder;
    }

    @Override
    public void onBindViewHolder(TopRatedViewHolder holder, int position) {

        TopRatedModel topRatedModel = list.get(position) ;
        //holder.toprated_img.setImageResource(topRatedModel.getMovie_local());
        Glide.with(context).load(Constant.BASE_IMAGE_URL+topRatedModel.getMovie_poster()).placeholder(R.drawable.dd).into(holder.toprated_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopRatedViewHolder extends RecyclerView.ViewHolder{

        ImageView toprated_img ;
        public TopRatedViewHolder(View itemView) {
            super(itemView);
            toprated_img = (ImageView) itemView.findViewById(R.id.top_rated_img);
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
