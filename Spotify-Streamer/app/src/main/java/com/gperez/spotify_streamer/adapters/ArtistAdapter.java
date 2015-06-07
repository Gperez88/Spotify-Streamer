package com.gperez.spotify_streamer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gperez.spotify_streamer.R;
import com.gperez.spotify_streamer.models.ArtistWrapper;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 5/28/2015.
 */
public class ArtistAdapter extends BaseAdapter implements Serializable {
    private Activity mActivity;
    private List<ArtistWrapper> mArtistList;

    static class Holder {
        ImageView thumbnailImageView;
        TextView nameArtistTextView;
    }

    public ArtistAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public ArtistAdapter(Activity mActivity, List<ArtistWrapper> mArtistList) {
        this.mActivity = mActivity;
        this.mArtistList = mArtistList;
    }

    @Override
    public int getCount() {
        if (mArtistList == null) {
            return 0;
        }

        return mArtistList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mArtistList == null) {
            return 0;
        }

        return mArtistList.get(position);
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
            view = inflater.inflate(R.layout.item_artist_search_result, null);

            Holder holder = new Holder();
            holder.thumbnailImageView = (ImageView) view.findViewById(R.id.thumbnail_sound_artist_result_imageview);
            holder.nameArtistTextView = (TextView) view.findViewById(R.id.name_sound_artist_result_textview);

            view.setTag(holder);
        }

        Holder holder = (Holder) view.getTag();

        ArtistWrapper artist = mArtistList.get(position);

        Picasso.with(mActivity)
                .load(artist.getThumbnailImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .resizeDimen(R.dimen.thumbnail_sound_artist_result_width, R.dimen.thumbnail_sound_artist_result_height)
                .centerCrop()
                .into(holder.thumbnailImageView);

        holder.nameArtistTextView.setText(artist.getName());

        return view;
    }

    public String getArtistId(int position) {
        return mArtistList.get(position).getSpotifyId();
    }

    public void swapList(List<ArtistWrapper> result) {

        if (mArtistList == null) {
            mArtistList = new ArrayList<>();
        }
        mArtistList.clear();
        mArtistList.addAll(result);
    }

    public List<ArtistWrapper> getmArtistList() {
        return mArtistList;
    }
}
