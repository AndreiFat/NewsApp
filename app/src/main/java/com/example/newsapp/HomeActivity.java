package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity  implements SelectListener {

    RecyclerView recyclerView;
    ArticleAdapter articleAdapter;
    ProgressDialog progressDialog;
    SearchView searchView;
    TextView text_view_1, text_view_2, text_view_3, text_view_4, text_view_5, text_view_6, text_view_7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(1,90,255)));


        showProgressDialog("Loading...");

        RequestManager manager = new RequestManager(this);
        manager.getArticle(listener, "general", null);
        Toast.makeText(HomeActivity.this, "Articles loaded", Toast.LENGTH_SHORT).show();
//        text_view_1 = findViewById(R.id.text_view_1);
//        text_view_2 = findViewById(R.id.text_view_2);
//        text_view_3 = findViewById(R.id.text_view_3);
//        text_view_4 = findViewById(R.id.text_view_4);
//        text_view_5 = findViewById(R.id.text_view_5);
//        text_view_6 = findViewById(R.id.text_view_6);
//        text_view_7 = findViewById(R.id.text_view_7);
    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<Article> list, String message) {
            if (list.isEmpty()) {
                Toast.makeText(HomeActivity.this, "No data loaded", Toast.LENGTH_SHORT).show();
            } else {
                showNews(list);
            }
            progressDialog.dismiss();

        }

        @Override
        public void onError(String message) {
            Toast.makeText(HomeActivity.this, "Error, try to reload", Toast.LENGTH_SHORT).show();
            showProgressDialog("Retry...");
            RequestManager manager = new RequestManager(HomeActivity.this);
            manager.getArticle(listener, "technology", null);
        }
    };

    private void showNews(List<Article> list) {
        recyclerView = findViewById(R.id.recycler_home);
        articleAdapter = new ArticleAdapter(this, list, this);
        articleAdapter.setHasStableIds(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(articleAdapter);
    }


    public void onClickToCategories(View view) {
        TextView textView = (TextView) view;
        String category = textView.getText().toString().toLowerCase(Locale.ROOT);
        showProgressDialog("Loading articles of " + category + " category...");
        RequestManager manager = new RequestManager(this);
        manager.getArticle(listener, category, null);
    }

    @Override
    public void OnNewsClick(Article article) {
        startActivity(new Intent(this, ViewArticleActivity.class)
                .putExtra("data", article));
    }

    public void showProgressDialog(String title) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return true;
    }

}