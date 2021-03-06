package com.dongumen.nickolas.youthop.widgets.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongumen.nickolas.youthop.R;
import com.dongumen.nickolas.youthop.activities.OppActivity;
import com.dongumen.nickolas.youthop.models.enteties.OppListItem;
import com.dongumen.nickolas.youthop.utils.DateUtil;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortedListAdapter extends RecyclerView.Adapter<SortedListAdapter.ViewHolder> {


    private Context context;
    private List<OppListItem> listItems = new ArrayList<>();


    public SortedListAdapter(Context context, List<OppListItem> list) {
        listItems = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bindHolder(holder, listItems.get(position));
        if (position == getItemCount() - 1) {
            holder.divider.setVisibility(View.INVISIBLE);
        } else {
            holder.divider.setVisibility(View.VISIBLE);
        }
    }

    private void bindHolder(ViewHolder holder, OppListItem oppListItem) {
        holder.date.setText(DateUtil.getDeadlineDays(oppListItem.deadline));
        holder.place.setText(oppListItem.place);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images");

        Glide.with(context)
                .load(storageReference.child(oppListItem.imageId))
                .into(holder.image);
        holder.name.setText(oppListItem.name);
        holder.type.setText(oppListItem.type);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, OppActivity.class);
            intent.putExtra("id", oppListItem.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.type)
        TextView type;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.place)
        TextView place;
        @BindView(R.id.divider)
        View divider;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
