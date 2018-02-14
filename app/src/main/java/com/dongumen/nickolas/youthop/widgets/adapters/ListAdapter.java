package com.dongumen.nickolas.youthop.widgets.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Nickolas on 14.02.2018.
 */

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.ViewHolder>{


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(TextView v) {
            super(v);
        }
    }
}
