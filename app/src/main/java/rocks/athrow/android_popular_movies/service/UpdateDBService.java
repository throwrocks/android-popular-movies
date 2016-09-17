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

    public UpdateDBService() {
        super(MovieListActivity.UPDATE_DB_BROADCAST);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Bundle arguments = workIntent.getExtras();
        String moviesJSON = arguments.getString(MovieListActivity.INTENT_EXTRA);
        ContentValues[] moviesContentValues;
        if (moviesJSON != null) {
            moviesContentValues = JSONParser.getMoviesFromJSON(moviesJSON);
            MoviesProvider moviesProvider = new MoviesProvider(getApplicationContext());
            moviesProvider.bulkInsert(MovieContract.MovieEntry.CONTENT_URI, moviesContentValues);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MovieListActivity.UPDATE_DB_BROADCAST));
        }
    }
}