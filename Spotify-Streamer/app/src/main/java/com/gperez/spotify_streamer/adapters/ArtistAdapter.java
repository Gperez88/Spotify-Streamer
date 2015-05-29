package com.gperez.spotify_streamer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gperez.spotify_streamer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by gabriel on 5/28/2015.
 */
public class ArtistAdapter extends BaseAdapter {
    private Activity mActivity;
    private List<Artist> mArtistList;

    static class Holder {
        ImageView thumbnailImageView;
        TextView nameArtistTextView;
    }

    public ArtistAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public ArtistAdapter(Activity mActivity, List<Artist> mArtistList) {
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

    public String getArtistId(int position) {
        return mArtistList.get(position).id;
    }

    public void swapList(List<Artist> result) {

        if (mArtistList == null) {
            mArtistList = new ArrayList<>();
        }
        mArtistList.clear();
        mArtistList.addAll(result);
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

        Artist artist = mArtistList.get(position);

        String artistImageUrl = null;

        for (Image image : artist.images) {
            artistImageUrl = image.url;
            break;
        }

        Picasso.with(mActivity)
                .load(artistImageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .resizeDimen(R.dimen.thumbnail_sound_artist_result_width, R.dimen.thumbnail_sound_artist_result_height)
                .centerCrop()
                .into(holder.thumbnailImageView);

        holder.nameArtistTextView.setText(artist.name);

        return view;
    }
}
