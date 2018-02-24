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

public class FilteredListAdapter extends RecyclerView.Adapter<FilteredListAdapter.ViewHolder>{


    private Context context;
    private List<OppListItem> listItems = new ArrayList<>();

    public FilteredListAdapter(Context context, List<OppListItem> listItems) {
        this.context = context;
        this.listItems = listItems;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_view, parent, false);
        return new FilteredListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bindHolder(holder, listItems.get(position));
    }

    private void bindHolder(ViewHolder holder, OppListItem oppListItem) {
        holder.deadlineDays.setText(DateUtil.getDeadlineDays(oppListItem.deadline));
        holder.deadlineDate.setText(DateUtil.getDateFrormated(oppListItem.deadline));
        holder.typeAndPlace.setText(String.format("%s in %s", oppListItem.type, oppListItem.place));
        StorageReference storageReference =
                FirebaseStorage.getInstance().getReference().child("images");
        Glide.with(context)
                .load(storageReference.child(oppListItem.imageId))
                .into(holder.image);
        holder.name.setText(oppListItem.name);
        holder.priceTag.setText(oppListItem.price);
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
         @BindView(R.id.price_tag)
        TextView priceTag;
        @BindView(R.id.type_and_place)
        TextView typeAndPlace;
        @BindView(R.id.deadline_date)
        TextView deadlineDate;
        @BindView(R.id.deadline_days)
        TextView deadlineDays;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
