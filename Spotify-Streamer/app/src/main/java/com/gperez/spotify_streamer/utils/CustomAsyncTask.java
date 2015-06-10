package com.gperez.spotify_streamer.utils;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * ===========================================
 * Android AsyncTasks during a screen rotation
 * ===========================================
 * The Activity is responsible for notifying the application when it is being destroyed and restarted in onSaveInstanceState.
 * The CustomApplication class will lookup all AsyncTasks that have been started on behalf of the Activity and null out their
 * Activity reference.  When the new Activity instance is created and initialized, the onRestoreInstanceState method notifies
 * the CustomApplication again and it will pass the new Activity reference to all of the AsyncTasks that are still running.
 *
 * @see [callorico/CustomAsyncTask](https://github.com/callorico/CustomAsyncTask)
 *
 */
public abstract class CustomAsyncTask<TParams, TProgress, TResult> extends AsyncTask<TParams, TProgress, TResult> {
    protected CustomApplication mApp;
    protected Activity mActivity;

    public CustomAsyncTask(Activity activity) {
        mActivity = activity;
        mApp = (CustomApplication) mActivity.getApplication();
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
        if (mActivity == null) {
            onActivityDetached();
        } else {
            onActivityAttached();
        }
    }

    protected void onActivityAttached() {
    }

    protected void onActivityDetached() {
    }

    @Override
    protected void onPreExecute() {
        mApp.addTask(mActivity, this);
    }

    @Override
    protected void onPostExecute(TResult result) {
        mApp.removeTask(this);
    }

    @Override
    protected void onCancelled() {
        mApp.removeTask(this);
    }
}