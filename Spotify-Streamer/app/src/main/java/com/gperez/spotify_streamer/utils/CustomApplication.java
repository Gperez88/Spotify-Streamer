package com.gperez.spotify_streamer.utils;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
public class CustomApplication extends Application {
    /**
     * Maps between an activity class name and the list of currently running
     * AsyncTasks that were spawned while it was active.
     */
    private Map<String, List<CustomAsyncTask<?,?,?>>> mActivityTaskMap;

    public CustomApplication() {
        mActivityTaskMap = new HashMap<String, List<CustomAsyncTask<?,?,?>>>();
    }

    public void removeTask(CustomAsyncTask<?,?,?> task) {
        for (Map.Entry<String, List<CustomAsyncTask<?,?,?>>> entry : mActivityTaskMap.entrySet()) {
            List<CustomAsyncTask<?,?,?>> tasks = entry.getValue();
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i) == task) {
                    tasks.remove(i);
                    break;
                }
            }

            if (tasks.size() == 0) {
                mActivityTaskMap.remove(entry.getKey());
                return;
            }
        }
    }

    public void addTask(Activity activity, CustomAsyncTask<?,?,?> task) {
        String key = activity.getClass().getCanonicalName();
        List<CustomAsyncTask<?,?,?>> tasks = mActivityTaskMap.get(key);
        if (tasks == null) {
            tasks = new ArrayList<CustomAsyncTask<?,?,?>>();
            mActivityTaskMap.put(key, tasks);
        }

        tasks.add(task);
    }

    public void detach(Activity activity) {
        List<CustomAsyncTask<?,?,?>> tasks = mActivityTaskMap.get(activity.getClass().getCanonicalName());
        if (tasks != null) {
            for (CustomAsyncTask<?,?,?> task : tasks) {
                task.setActivity(null);
            }
        }
    }

    public void attach(Activity activity) {
        List<CustomAsyncTask<?,?,?>> tasks = mActivityTaskMap.get(activity.getClass().getCanonicalName());
        if (tasks != null) {
            for (CustomAsyncTask<?,?,?> task : tasks) {
                task.setActivity(activity);
            }
        }
    }
}
