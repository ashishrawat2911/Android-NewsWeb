package com.rawat.ashish.newsweb.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rawat.ashish.newsweb.R;
import com.rawat.ashish.newsweb.model.News;
import com.rawat.ashish.newsweb.networks.APIClient;
import com.rawat.ashish.newsweb.networks.APIService;
import com.rawat.ashish.newsweb.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    APIService mAPIService;

    Call<News> newsCall;

    String url;

    String newsType;
    int newsPosition;

    TextView titleTextView, descTextView;

    ImageView detailsImageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        progressBar = findViewById(R.id.detailsProgressbar);
        progressBar.setIndeterminate(true);
        mAPIService = APIClient.getClient().create(APIService.class);

        titleTextView = findViewById(R.id.detailsTitleTextView);
        descTextView = findViewById(R.id.detailsDescTextView);
        detailsImageView = findViewById(R.id.detailsImageView);

        Intent receivedIntent = getIntent();
        newsType = receivedIntent.getStringExtra(Constants.NEWS_TYPE);
        newsPosition = receivedIntent.getIntExtra(Constants.POSITION, -1);
        loadData();
        (findViewById(R.id.moreNews)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailsActivity.this, WebViewActivity.class);
                i.putExtra(Constants.URL, url);
                startActivity(i);
            }
        });
    }

    //Fetching all the data from the News Api asynchronously using Retrofit
    private void loadData() {
        if (newsType.equals("top_news")) {
            newsCall = mAPIService.
                    topNews(getResources().getString(R.string.country), getResources().getString(R.string.api_key));
            newsCall.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    if (!response.isSuccessful()) {
                        newsCall = call.clone();
                        newsCall.enqueue(this);
                        return;
                    }
                    progressBar.setVisibility(View.GONE);


                    if (response.body() == null) return;
                    setDetails(response.body().getArticles().get(newsPosition).getTitle(), response.body().getArticles().get(newsPosition).getDescription(), response.body().getArticles().get(newsPosition).getUrlToImage());
                    url = response.body().getArticles().get(newsPosition).getUrl();
                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });

        } else {
            newsCall = mAPIService.topNewsCategory(getResources().getString(R.string.country), newsType, getResources().getString(R.string.api_key));
            newsCall.enqueue(new Callback<News>() {
                @Override
                public void onResponse(Call<News> call, Response<News> response) {
                    if (!response.isSuccessful()) {
                        newsCall = call.clone();
                        newsCall.enqueue(this);
                        return;

                    }
                    progressBar.setVisibility(View.GONE);
                    setDetails(response.body().getArticles().get(newsPosition).getTitle(), response.body().getArticles().get(newsPosition).getDescription(), response.body().getArticles().get(newsPosition).getUrlToImage());
                    url = response.body().getArticles().get(newsPosition).getUrl();

                }

                @Override
                public void onFailure(Call<News> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    //Data is fetched from two apis (this method is for code re-usability)
    private void setDetails(String title, String desc, String detailsImage) {
        titleTextView.setText(title);
        descTextView.setText(desc);
        if (detailsImage == null) {
            detailsImageView.setImageResource(R.drawable.no_image_available);
        } else
            Glide.with(DetailsActivity.this)
                    .asBitmap()
                    .load(detailsImage)
                    .into(detailsImageView);

    }

}
