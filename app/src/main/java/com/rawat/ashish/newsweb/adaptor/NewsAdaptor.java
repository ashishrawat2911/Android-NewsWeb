package com.rawat.ashish.newsweb.adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rawat.ashish.newsweb.R;
import com.rawat.ashish.newsweb.activities.DetailsActivity;
import com.rawat.ashish.newsweb.model.News;
import com.rawat.ashish.newsweb.utils.Constants;

import java.util.List;

public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.MyViewHolder> {
    private Context ctx;
    private List<News.Article> newsList;
    private String newsType;

    public NewsAdaptor(Context ctx, List<News.Article> topHeadlinesList, String newsType) {
        this.ctx = ctx;
        this.newsList = topHeadlinesList;
        this.newsType = newsType;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(ctx).inflate(R.layout.news_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(newsList.get(position).getTitle());
        if (newsList.get(position).getUrlToImage()==null) {
            holder.displayImage.setImageResource(R.drawable.no_image_available);

        } else
            Glide.with(ctx)
                    .asBitmap()
                    .load(newsList.get(position).getUrlToImage())
                    .into(holder.displayImage);
    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView displayImage;
        CardView cardView;

        MyViewHolder(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTextView);
            displayImage = itemView.findViewById(R.id.itemImageView);
            cardView = itemView.findViewById(R.id.listCardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(ctx, DetailsActivity.class);
                    i.putExtra(Constants.NEWS_TYPE, newsType);
                    i.putExtra(Constants.POSITION, getAdapterPosition());
                    ctx.startActivity(i);

                }
            });
        }
    }
}

