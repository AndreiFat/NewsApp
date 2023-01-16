package com.example.newsapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ViewArticleActivity extends AppCompatActivity {

    private Article article;
    private TextView textTitle, textDescription, textContent, textAuthor, textPublishedAt;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);

        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Article");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(1,90,255)));

        article = (Article) getIntent().getSerializableExtra("data");
        textTitle = findViewById(R.id.text_details_title);
        textDescription = findViewById(R.id.text_details_description);
//        textContent = findViewById(R.id.text_details_content);
        textAuthor = findViewById(R.id.text_details_author);
        textPublishedAt = findViewById(R.id.text_details_time);
        imageView = findViewById(R.id.img_details_image);

        setDataToView();
    }

    public void setDataToView() {
        Drawable icon_link = getResources().getDrawable(R.drawable.ic_round_link_24);
        Drawable icon_calendar = getResources().getDrawable(R.drawable.ic_round_calendar_month_24);

        textTitle.setText(article.getTitle());
        textDescription.setText(article.getDescription());

        if(article.getAuthor()!=null){
            textAuthor.setCompoundDrawablesWithIntrinsicBounds(icon_link, null, null, null);
            textAuthor.setText(article.getAuthor());
        }
        textPublishedAt.setCompoundDrawablesWithIntrinsicBounds(icon_calendar,null,null,null);
        textPublishedAt.setText(article.getPublishedAt());
        Picasso.get().load(article.getUrlToImage()).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
               Intent intent = new Intent(this, SettingsActivity.class);
               startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}