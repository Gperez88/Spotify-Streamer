package com.gperez.spotify_streamer.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.activities.TopTenTracksActivity;
import com.gperez.spotify_streamer.adapters.ArtistAdapter;
import com.gperez.spotify_streamer.models.ArtistWrapper;
import com.gperez.spotify_streamer.tasks.SearchArtistAsyncTask;

import java.io.Serializable;
import java.util.List;

public class SearchFragment extends Fragment {
    private static final String LIST_ADAPTER_INSTANCE_STATE = "artist-list-instance-state";
    private List<ArtistWrapper> artistWrapperListInstanceState;

    private EditText inputSearchSoundArtistTextView;
    private ListView soundArtistResultListView;

    public SearchFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(LIST_ADAPTER_INSTANCE_STATE, (Serializable) ((ArtistAdapter) soundArtistResultListView.getAdapter()).getmArtistList());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            artistWrapperListInstanceState = (List<ArtistWrapper>) savedInstanceState.getSerializable(LIST_ADAPTER_INSTANCE_STATE);
        }

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        soundArtistResultListView = (ListView) rootView.findViewById(R.id.sound_artist_result_listview);
        soundArtistResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String artistId = ((ArtistAdapter) soundArtistResultListView.getAdapter()).getArtistId(position);
                String artistName = ((ArtistWrapper) soundArtistResultListView.getAdapter().getItem(position)).getName();

                Intent intent = new Intent(getActivity(), TopTenTracksActivity.class);
                intent.putExtra(TopTenTracksActivity.ARG_ARTIST_ID, artistId);
                intent.putExtra(TopTenTracksActivity.ARG_ARTIST_NAME,artistName);

                startActivity(intent);
            }
        });

        //passing the instance of the collection of artist who keep turning the screen.
        if (artistWrapperListInstanceState != null) {
            ArtistAdapter artistAdapter = new ArtistAdapter(getActivity(), artistWrapperListInstanceState);
            soundArtistResultListView.setAdapter(artistAdapter);
        }

        inputSearchSoundArtistTextView = (EditText) rootView.findViewById(R.id.input_search_sound_artist_edittext);
        inputSearchSoundArtistTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SearchArtistAsyncTask searchArtistAsyncTask = new SearchArtistAsyncTask(getActivity(), soundArtistResultListView);
                    searchArtistAsyncTask.execute(textView.getText().toString());

                    // hide edit text.
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });

        return rootView;
    }

}
