package com.gperez.spotify_streamer.activities;

import android.os.Bundle;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.fragments.SearchFragment;

public class SearchActivity extends BaseManagerAsyncTaskActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new SearchFragment()).commit();
        }

    }

}
