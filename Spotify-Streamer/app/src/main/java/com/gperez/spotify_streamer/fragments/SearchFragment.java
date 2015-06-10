package com.gperez.spotify_streamer.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.gperez.spotify_streamer.activities.TopTenTracksActivity;
import com.gperez.spotify_streamer.adapters.ArtistAdapter;
import com.gperez.spotify_streamer.models.ArtistWrapper;
import com.gperez.spotify_streamer.tasks.AsyncTaskParams;
import com.gperez.spotify_streamer.tasks.SearchArtistAsyncTask;

public class SearchFragment extends BaseManagerListViewInstanceFragment<ArtistAdapter, ArtistWrapper> {
    private EditText inputSearchSoundArtistTextView;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    protected void initComponents() {
        inputSearchSoundArtistTextView = (EditText) getView().findViewById(R.id.input_search_sound_artist_edittext);
        inputSearchSoundArtistTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String queryString = textView.getText().toString();

                    if (queryString != null && !queryString.isEmpty()) {

                        AsyncTaskParams mAsyncTaskParams =
                                new AsyncTaskParams(getActivity(), SearchFragment.this, loadData, containerListView, true);

                        SearchArtistAsyncTask searchArtistAsyncTask = new SearchArtistAsyncTask(mAsyncTaskParams);
                        searchArtistAsyncTask.execute(queryString);

                        // hide edit text.
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                        return true;
                    }
                    Toast.makeText(getActivity(), getActivity().getString(R.string.name_artist_require), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    protected void restoreListViewInstanceState() {
        //passing the instance of the collection of artist who keep turning the screen.
        if (adapterListItemsInstance != null) {
            ArtistAdapter artistAdapter = new ArtistAdapter(getActivity(), adapterListItemsInstance);
            getListView().setAdapter(artistAdapter);

            // Restore previous state (including selected item index and scroll position)
            if (stateListViewInstance != null) {
                getListView().onRestoreInstanceState(stateListViewInstance);
            }
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        String artistId = ((ArtistAdapter) listView.getAdapter()).getArtistId(position);
        String artistName = ((ArtistWrapper) listView.getAdapter().getItem(position)).getName();

        Intent intentTopTenTracks = new Intent(getActivity(), TopTenTracksActivity.class);
        intentTopTenTracks.putExtra(TopTenTracksActivity.ARG_ARTIST_ID, artistId);
        intentTopTenTracks.putExtra(TopTenTracksActivity.ARG_ARTIST_NAME, artistName);

        startActivity(intentTopTenTracks);
    }
}
