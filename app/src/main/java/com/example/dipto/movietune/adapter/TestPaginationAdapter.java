package com.example.dipto.movietune.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dipto.movietune.R;
import com.example.dipto.movietune.fragment.TestPagination;
import com.example.dipto.movietune.model.NewReleaseModel;
import com.example.dipto.movietune.model.TestPaginationModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dipto on 8/23/2017.
 */

public class TestPaginationAdapter extends RecyclerView.Adapter<TestPaginationAdapter.TestPaginationViewHolder> {

    private final LayoutInflater inflater;
    private Context context ;
    private List<TestPaginationModel> list = Collections.emptyList() ;


    public TestPaginationAdapter(Context context, List<TestPaginationModel> list){
        this.context = context ;
        this.list = list ;
        inflater = LayoutInflater.from(context) ;
    }

    @Override
    public TestPaginationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_test_pagination, parent, false) ;
        TestPaginationViewHolder testPaginationViewHolder = new TestPaginationViewHolder(view) ;
        return testPaginationViewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(TestPaginationViewHolder holder, int position) {

        TestPaginationModel testPaginationModel = list.get(position) ;
        holder.textView.setText(testPaginationModel.getPagination());
    }

    public class TestPaginationViewHolder extends RecyclerView.ViewHolder{

        TextView textView ;

        public TestPaginationViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.my_text);
        }
    }
}
