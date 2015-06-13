package com.gperez.spotify_streamer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.gperez.spotify_streamer.R;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class MainActivity extends BaseActivity {
    private static final String LOG_CAT = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "aa715e806e384acfa7516ac49aec4a1f";
    private static final String REDIRECT_URI = "http://perezgabriel89.com/callback/";

    private SharedPreferences prefs;
    private String prefAccessTokenKey;
    private String prefAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefAccessTokenKey = this.getString(R.string.pref_access_token);
        prefAccessToken = prefs.getString(prefAccessTokenKey, null);

        openLoginWindow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                case TOKEN:
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(prefAccessTokenKey, response.getAccessToken());
                    editor.commit();

                    startSearchActivity();

                    break;

                case ERROR:
                    logStatus("Auth error: " + response.getError());
                    break;

                default:
                    logStatus("Auth result: " + response.getType());
            }
        }
    }

    private void openLoginWindow() {
        final AuthenticationRequest request = new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI)
                .setScopes(new String[]{"streaming"})
                .build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    private void logStatus(String status) {
        Log.i(LOG_CAT, status);
    }

    private void startSearchActivity() {
        startActivity(new Intent(this, SearchActivity.class));
        this.finish();
    }
}
