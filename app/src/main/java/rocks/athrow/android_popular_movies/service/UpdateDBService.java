package rocks.athrow.android_popular_movies.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import rocks.athrow.android_popular_movies.activity.MovieListActivity;
import rocks.athrow.android_popular_movies.data.JSONParser;
import rocks.athrow.android_popular_movies.data.MovieContract;
import rocks.athrow.android_popular_movies.data.MoviesProvider;

/**
 * UpdateDBService
 * Created by jose on 9/16/16.
 */
public class UpdateDBService extends IntentService {
    public static final String UPDATE_DB_BROADCAST = "rocks.athrow.android_popular_movies.UpdateDB_Broadcast";

    public UpdateDBService() {
        super(UPDATE_DB_BROADCAST);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Bundle arguments = workIntent.getExtras();
        String intentType = arguments.getString(MovieListActivity.INTENT_TYPE);
        String JSON = arguments.getString(MovieListActivity.INTENT_EXTRA);
        if (intentType != null && JSON != null) {
            switch (intentType) {
                case MovieListActivity.INTENT_TYPE_MOVIES:
                    ContentValues[] moviesContentValues;
                    moviesContentValues = JSONParser.getMoviesFromJSON(JSON);
                    MoviesProvider moviesProvider = new MoviesProvider(getApplicationContext());
                    moviesProvider.bulkInsert(MovieContract.MovieEntry.CONTENT_URI, moviesContentValues);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(UPDATE_DB_BROADCAST));
                    break;
            }
        }
    }
}