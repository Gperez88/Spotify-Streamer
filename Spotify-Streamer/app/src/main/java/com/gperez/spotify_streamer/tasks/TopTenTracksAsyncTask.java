package com.gperez.spotify_streamer.tasks;

import android.widget.ListView;

import com.gperez.spotify_streamer.adapters.ArtistTopTenAdapter;
import com.gperez.spotify_streamer.models.ArtistWrapper;
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
    private ArtistWrapper artist;
    private boolean selectedFirstItem;

    public TopTenTracksAsyncTask(AsyncTaskParams mAsyncTaskParams, boolean selectedFirstItem) {
        super(mAsyncTaskParams);
        this.selectedFirstItem = selectedFirstItem;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (params == null || params.length == 0) {
            return null;
        }

        artist = (ArtistWrapper) params[0];

        String id = artist.getSpotifyId();

        final Map<String, Object> options = new HashMap<>();
        options.put(SpotifyService.OFFSET, 0);
        options.put(SpotifyService.LIMIT, 10);
        options.put(SpotifyService.COUNTRY, "DO");

        Tracks result = spotifyService.getArtistTopTrack(id, options);

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
                        track.duration_ms, track.album.name, thumbnailImage, track.preview_url, artist));
            }

            mArtistTopTenAdapter.swapList(trackTopTenArtistWrapperArrayList);
            mArtistTopTenAdapter.notifyDataSetChanged();

            ListView listView = asyncTaskParams.getListFragment().getListView();

            if(selectedFirstItem) {
                listView.setItemChecked(0, true);
                listView.performItemClick(listView, 0, listView.getItemIdAtPosition(0));
            }
        }

    }

    @Override
    protected void initAdapterListView() {
        mArtistTopTenAdapter = new ArtistTopTenAdapter(asyncTaskParams.getActivity());
        asyncTaskParams.getListFragment().setListAdapter(mArtistTopTenAdapter);
    }

}
