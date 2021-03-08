package com.example.android_fragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EpCharImgAdapter extends RecyclerView.Adapter<EpCharImgAdapter.ViewHolder> {

    private List<String> charImgs;
    private Context context;

    public EpCharImgAdapter(Context context, List<String> charImgs) {
        this.charImgs = charImgs;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(this.context);

        View charImgView = inflater.inflate(R.layout.item_charimg, parent, false);

        ViewHolder viewHolder = new ViewHolder(charImgView);
        Log.d("adapter", String.valueOf(this.getItemCount()));
        Log.d("adapter", charImgs.toString());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String charImg = charImgs.get(position);
        Log.d("adapter bind", charImg);
        Picasso.get().load(charImg).into(holder.charImg);

    }

    @Override
    public int getItemCount() {
        return charImgs.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView charImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.charImg = itemView.findViewById(R.id.imageView_epCharImg);
        }
    }
}