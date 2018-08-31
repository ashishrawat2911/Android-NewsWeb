package com.rawat.ashish.newsweb.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rawat.ashish.newsweb.R;
import com.rawat.ashish.newsweb.adaptor.CategoriesAdaptor;
import com.rawat.ashish.newsweb.model.Categories;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    List<Categories> categoriesList;
    RecyclerView categoriesRecyclerView;
    CategoriesAdaptor categoriesAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        //instantiate RecyclerView
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        //creating a new list
        categoriesList = new ArrayList<>();
        loadCategories();
        categoriesAdaptor = new CategoriesAdaptor(this, categoriesList);
        categoriesRecyclerView.setAdapter(categoriesAdaptor);
        //setting the layout of RecyclerView as Grid
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void loadCategories() {
        //adding all the categories of news in the list
        categoriesList.add(new Categories(R.drawable.top_news, "Top Headlines", "top_news"));
        categoriesList.add(new Categories(R.drawable.health_news, "Health", "health"));
        categoriesList.add(new Categories(R.drawable.entertainment_news, "Entertainment", "entertainment"));
        categoriesList.add(new Categories(R.drawable.sports_news, "Sports", "sports"));
        categoriesList.add(new Categories(R.drawable.business_news, "Business", "business"));
        categoriesList.add(new Categories(R.drawable.tech_news, "Technology", "technology"));
        categoriesList.add(new Categories(R.drawable.science_news, "Science", "science"));
        categoriesList.add(new Categories(R.drawable.politics_news, "Politics", "politics"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuRate:
                String packageName = getApplicationContext().getPackageName();
                String playStoreLink = "https://play.google.com/store/apps/details?id=" + packageName;
                Intent appRateUsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink));
                startActivity(appRateUsIntent);
                return true;
            case R.id.menuShare:
                String packageName2 = getApplicationContext().getPackageName();
                Intent appShareIntent = new Intent(Intent.ACTION_SEND);
                appShareIntent.setType("text/plain");
                String extraText = "Hey! Check out this amazing app all News Categories.\n\n";
                extraText += "https://play.google.com/store/apps/details?id=" + packageName2;
                appShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(appShareIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
