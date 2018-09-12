package com.example.android.kubiki_final;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private TextView tvNumber;
    private TextView tvName;
    private TextView tvCount;
    private TextView tvNothing;

    Result result;

    private ArrayList<Result> resultList = new ArrayList<>();

    public void setItems(Collection<Result> results) {
        resultList.addAll(results);
        notifyDataSetChanged();
    }

    public void clearItems() {
        resultList.clear();
        notifyDataSetChanged();
    }

    public void setItem(Result result) {
        resultList.add(result);
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.bind(resultList.get(position));
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        public void bind(Result result) {
            if (getItemCount() != 0) {
                tvNothing.setVisibility(View.GONE);
                tvNumber.setVisibility(View.VISIBLE);
                tvName.setVisibility(View.VISIBLE);
                tvCount.setVisibility(View.VISIBLE);
            }
            tvCount.setText(String.valueOf(result.getCount()));
            tvName.setText(result.getName());
            tvNumber.setText(String.valueOf(result.getId()));
        }

        public RecyclerHolder(View itemView) {
            super(itemView);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_number);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
            tvCount = (TextView) itemView.findViewById(R.id.tv_item_count);
            tvNothing = (TextView) itemView.findViewById(R.id.tv_nothing);
        }
    }
}
