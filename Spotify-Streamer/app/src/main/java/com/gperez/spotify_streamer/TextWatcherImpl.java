package com.gperez.spotify_streamer;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by gabriel on 5/27/2015.
 */
public abstract class TextWatcherImpl implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
