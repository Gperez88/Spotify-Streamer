package com.gperez.spotify_streamer.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.gperez.spotify_streamer.adapters.ArtistAdapter;
import com.gperez.spotify_streamer.tasks.SearchArtistAsyncTask;

public class SearchFragment extends Fragment {
    private static final String LIST_ADAPTER_INSTANCE_STATE = "mArtistAdapterInstanceState";
    private ArtistAdapter mArtistAdapterInstanceState;

    private EditText inputSearchSoundArtistTextView;
    private ListView soundArtistResultListView;

    public SearchFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST_ADAPTER_INSTANCE_STATE, (ArtistAdapter) soundArtistResultListView.getAdapter());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mArtistAdapterInstanceState = (ArtistAdapter) savedInstanceState.getSerializable(LIST_ADAPTER_INSTANCE_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        soundArtistResultListView = (ListView) rootView.findViewById(R.id.sound_artist_result_listview);

        if (mArtistAdapterInstanceState != null) {
            soundArtistResultListView.setAdapter(mArtistAdapterInstanceState);
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
