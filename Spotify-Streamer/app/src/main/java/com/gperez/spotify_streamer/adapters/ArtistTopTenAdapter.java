package com.gperez.spotify_streamer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.models.TrackTopTenArtistWrapper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 5/28/2015.
 */
public class ArtistTopTenAdapter extends BaseCustomAdapter<TrackTopTenArtistWrapper> {

    static class Holder {
        ImageView thumbnailImageView;
        TextView nameAlbumTextView;
        TextView nameTrackTextView;
    }

    public ArtistTopTenAdapter(Activity mActivity){
        super(mActivity);
    }

    public ArtistTopTenAdapter(Activity mActivity, List<TrackTopTenArtistWrapper> adapterListItems) {
        super(mActivity, adapterListItems);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {

            LayoutInflater inflater = mActivity.getLayoutInflater();
            view = inflater.inflate(R.layout.item_top_ten_tracks, null);

            Holder holder = new Holder();
            holder.thumbnailImageView = (ImageView) view.findViewById(R.id.thumbnail_artist_top_ten_track_imageview);
            holder.nameAlbumTextView = (TextView) view.findViewById(R.id.name_album_top_ten_track_textview);
            holder.nameTrackTextView = (TextView) view.findViewById(R.id.name_track_top_ten_track_textview);

            view.setTag(holder);
        }

        Holder holder = (Holder) view.getTag();

        TrackTopTenArtistWrapper track = adapterListItems.get(position);

        Picasso.with(mActivity)
                .load(track.getAlbumArtThumbnail())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .resizeDimen(R.dimen.thumbnail_sound_artist_result_width, R.dimen.thumbnail_sound_artist_result_height)
                .centerCrop()
                .into(holder.thumbnailImageView);

        holder.nameAlbumTextView.setText(track.getAlbumName());
        holder.nameTrackTextView.setText(track.getTrackName());

        return view;
    }

    public void swapList(List<TrackTopTenArtistWrapper> result) {

        if (adapterListItems == null) {
            adapterListItems = new ArrayList<>();
        }
        adapterListItems.clear();
        adapterListItems.addAll(result);
    }

}
