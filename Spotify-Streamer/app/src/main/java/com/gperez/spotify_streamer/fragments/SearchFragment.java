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

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.activities.TopTenTracksActivity;
import com.gperez.spotify_streamer.adapters.ArtistAdapter;
import com.gperez.spotify_streamer.models.ArtistWrapper;
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
                    SearchArtistAsyncTask searchArtistAsyncTask = new SearchArtistAsyncTask(getActivity(), getListView());
                    searchArtistAsyncTask.execute(textView.getText().toString());

                    // hide edit text.
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                    return true;
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
        }
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        String artistId = ((ArtistAdapter) listView.getAdapter()).getArtistId(position);
        String artistName = ((ArtistWrapper) listView.getAdapter().getItem(position)).getName();

        Intent intent = new Intent(getActivity(), TopTenTracksActivity.class);
        intent.putExtra(TopTenTracksActivity.ARG_ARTIST_ID, artistId);
        intent.putExtra(TopTenTracksActivity.ARG_ARTIST_NAME, artistName);

        startActivity(intent);
    }
}
