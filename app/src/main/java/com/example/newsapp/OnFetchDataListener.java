package com.example.newsapp;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {
    void onFetchData(List<Article> list, String message);

    void onError(String message);
}
