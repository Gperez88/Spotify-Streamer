package com.gperez.spotify_streamer.tasks;

import android.app.Activity;
import android.support.v4.app.ListFragment;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.adapters.ArtistTopTenAdapter;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;

/**
 * Created by gabriel on 5/30/2015.
 */
public class TopTenTracksAsyncTask extends BaseSearchAsyncTask {
    private ArtistTopTenAdapter mArtistTopTenAdapter;
    private ListFragment mListFragment;

    public TopTenTracksAsyncTask(Activity mActivity, ListFragment mListFragment) {
        super(mActivity, R.string.title_progress_dialog, R.string.message_progress_dialog);
        this.mListFragment = mListFragment;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (params == null || params.length == 0) {
            return null;
        }

        String id = (String) params[0];

        final Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.OFFSET, 0);
        options.put(SpotifyService.LIMIT, 10);
        options.put(SpotifyService.COUNTRY, "DO");

        Tracks result = mSpotifyService.getArtistTopTrack(id, options);

        return result;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        if (result == null) {
            return;
        }

        Tracks tracks = (Tracks) result;
        final boolean dataFound = tracks.tracks.size() > 0;

        if (dataFound) {

            List<TrackTopTenArtistWrapper> trackTopTenArtistWrapperArrayList = new ArrayList<>();

            for (Track track : tracks.tracks) {
                String thumbnailImage = null;

                for (Image image : track.album.images) {
                    thumbnailImage = image.url;
                    break;
                }

                trackTopTenArtistWrapperArrayList.add(new TrackTopTenArtistWrapper(track.name,
                        track.album.name, thumbnailImage, track.preview_url));
            }

            mArtistTopTenAdapter.swapList(trackTopTenArtistWrapperArrayList);
            mArtistTopTenAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void initAdapterListView() {
        mArtistTopTenAdapter = new ArtistTopTenAdapter(mActivity);
        mListFragment.setListAdapter(mArtistTopTenAdapter);
    }

}
