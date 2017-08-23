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

import java.util.Collections;
import java.util.List;

/**
 * Created by Dipto on 8/22/2017.
 */

public class NewReleaseAdapter extends RecyclerView.Adapter<NewReleaseAdapter.NewReleaseViewHolder> {

    private final LayoutInflater inflater;
    private Context context ;
    private List<NewReleaseModel> list = Collections.emptyList() ;

    private ClickListener clickListener ;

    public NewReleaseAdapter(Context context, List<NewReleaseModel> list){
        this.context = context ;
        this.list = list ;
        inflater = LayoutInflater.from(context) ;
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener ;
    }

    @Override
    public NewReleaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_new_release, parent, false) ;
        NewReleaseViewHolder newReleaseViewHolder = new NewReleaseViewHolder(view) ;
        return newReleaseViewHolder;
    }

    @Override
    public void onBindViewHolder(NewReleaseViewHolder holder, int position) {
        NewReleaseModel newReleaseModel = list.get(position) ;
        //holder.newrelase_img.setImageResource(newReleaseModel.getMovie_local());
        Glide.with(context).load(Constant.BASE_IMAGE_URL+newReleaseModel.getMovie_poster()).placeholder(R.drawable.dd).into(holder.newrelase_img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewReleaseViewHolder extends RecyclerView.ViewHolder{

        ImageView newrelase_img  ;
        public NewReleaseViewHolder(final View itemView) {
            super(itemView);
            newrelase_img = (ImageView) itemView.findViewById(R.id.new_release_img);

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
