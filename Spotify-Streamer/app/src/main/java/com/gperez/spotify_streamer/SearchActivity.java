package com.gperez.spotify_streamer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SearchActivity extends ActionBarActivity {
    private static final String LOG_CAT = SearchActivity.class.getSimpleName();
    private SpotifyApi spotifyApi;
    private SpotifyService spotifyService;

    private EditText inputSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initSpotifyService();

        inputSearchEditText = (EditText) findViewById(R.id.input_search_sound_artist_edittext);
        inputSearchEditText.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 3) {
                    doSearch(s.toString());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initSpotifyService() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String prefAccessTokenKey = this.getString(R.string.pref_access_token);
        String prefAccessToken = prefs.getString(prefAccessTokenKey, null);

        spotifyApi = new SpotifyApi();
        spotifyApi.setAccessToken(prefAccessToken);

        spotifyService = spotifyApi.getService();
    }

    private void doSearch(String query) {
        spotifyService.searchArtists(query, new Callback<ArtistsPager>() {
            @Override
            public void success(ArtistsPager artistsPager, Response response) {
                List<Artist> artistList = artistsPager.artists.items;

                for (Artist artist : artistList) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(String.format("Name: %s - Id: %s", artist.name, artist.id));

                    for (Image image : artist.images) {
                        stringBuilder.append(String.format(" - Url: %s", image.url));
                        break;
                    }

                    Log.d(LOG_CAT, stringBuilder.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_CAT, error.getMessage());
            }
        });
    }
}
