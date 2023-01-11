package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArticleAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private Context context;
    private List<Article> articles;
    private SelectListener selectListener;

    public ArticleAdapter(Context context, List<Article> articles, SelectListener selectListener) {
        this.context = context;
        this.articles = articles;
        this.selectListener = selectListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.headlines_list_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.text_title.setText(articles.get(position).getTitle());
        holder.text_source.setText(articles.get(position).getSource().getName());

        String dateFromJSON = articles.get(position).getPublishedAt();
        Locale locale = new Locale("ro","RO");
        String [] items = dateFromJSON.split("[T,Z]");
        String date = items[0];
        String hour = items[1];

        String dateString = date;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateInput = null;
        try {
            dateInput = inputFormat.parse(dateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = outputFormat.format(dateInput);

            holder.text_time.setText(formattedDate+" "+hour);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (articles.get(position).getUrlToImage() != null) {
            Picasso.get().load(articles.get(position).getUrlToImage()).into(holder.img_headline);
        }

        holder.cardView.setOnClickListener(v ->{
            selectListener.OnNewsClick(articles.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
