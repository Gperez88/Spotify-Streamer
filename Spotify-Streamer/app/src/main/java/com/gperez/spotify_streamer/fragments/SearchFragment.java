package com.gperez.spotify_streamer.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.adapters.ArtistAdapter;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {
    private static final String LOG_CAT = SearchFragment.class.getSimpleName();
    private SpotifyApi spotifyApi;
    private SpotifyService spotifyService;

    private ArtistAdapter artistAdapter;
    private EditText inputSearchSoundArtistTextView;
    private ListView soundArtistResultListView;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        inputSearchSoundArtistTextView = (EditText) rootView.findViewById(R.id.input_search_sound_artist_edittext);
        inputSearchSoundArtistTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch(textView.getText().toString());

                    // hide edit text.
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });

        artistAdapter = new ArtistAdapter(getActivity());
        soundArtistResultListView = (ListView) rootView.findViewById(R.id.sound_artist_result_listview);
        soundArtistResultListView.setAdapter(artistAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initSpotifyService();
    }

    private void initSpotifyService() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String prefAccessTokenKey = this.getString(R.string.pref_access_token);
        String prefAccessToken = prefs.getString(prefAccessTokenKey, null);

        spotifyApi = new SpotifyApi();
        spotifyApi.setAccessToken(prefAccessToken);

        spotifyService = spotifyApi.getService();
    }

    private void doSearch(final String query) {
        spotifyService.searchArtists(query, new Callback<ArtistsPager>() {
            @Override
            public void success(final ArtistsPager artistsPager, Response response) {
                int total = artistsPager.artists.total;
                final boolean dataFound = total > 0 ? true : false;

                // update data adapter on ui thread.
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dataFound) {
                            artistAdapter.swapList(artistsPager.artists.items);
                            artistAdapter.notifyDataSetChanged();

                        } else {
                            Toast.makeText(getActivity(), getString(R.string.no_data_found, query), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(LOG_CAT, error.getMessage());
            }
        });
    }
}
