package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        showProgressDialog("Loading articles...");

        RequestManager manager = new RequestManager(this);
        manager.getArticle(listener, "technology", null);

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
                Snackbar.make(findViewById(R.id.home_layout), "No data found!", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Dismiss", v -> {
                        }).show();
            } else {
                showNews(list);
            }
            progressDialog.dismiss();

        }

        @Override
        public void onError(String message) {
            Snackbar.make(findViewById(R.id.home_layout), message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", v -> {
                        showProgressDialog("Retrying...");
                        RequestManager manager = new RequestManager(HomeActivity.this);
                        manager.getArticle(listener, "technology", null);
                    }).show();
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
}