package com.gperez.spotify_streamer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.gperez.spotify_streamer.R;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;


public class MainActivity extends AppCompatActivity {
    private static final String LOG_CAT = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "aa715e806e384acfa7516ac49aec4a1f";
    private static final String REDIRECT_URI = "http://perezgabriel89.com/callback/";

    private SharedPreferences prefs;
    private String prefAccessTokenKey;
    private String prefAccessToken;

    private Button loginSpotifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefAccessTokenKey = this.getString(R.string.pref_access_token);
        prefAccessToken = prefs.getString(prefAccessTokenKey, null);

        loginSpotifyButton = (Button) findViewById(R.id.login_spotify_button);
        loginSpotifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginWindow();
            }
        });

        if (prefAccessToken != null) {
            startSearchActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    }
}
