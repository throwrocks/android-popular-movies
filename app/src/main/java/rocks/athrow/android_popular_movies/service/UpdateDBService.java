package rocks.athrow.android_popular_movies.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import rocks.athrow.android_popular_movies.activity.MovieListActivity;
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
        Parcelable[] moviesParceleable = (Parcelable[]) arguments.get(MovieListActivity.INTENT_EXTRA);
        if (moviesParceleable != null) {
            int parcelSize = moviesParceleable.length;
            ContentValues[] moviesContentValues = new ContentValues[parcelSize];
            int i = 0;
            while (i < moviesParceleable.length) {
                Parcel movieParcel = Parcel.obtain();
                movieParcel.writeParcelable(moviesParceleable[i], i);
                ContentValues movieContentValue = ContentValues.CREATOR.createFromParcel(movieParcel);
                moviesContentValues[i] = movieContentValue;
                i++;
            }
            MoviesProvider moviesProvider = new MoviesProvider(getApplicationContext());
            moviesProvider.bulkInsert(MovieContract.MovieEntry.CONTENT_URI, moviesContentValues);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MovieListActivity.UPDATE_DB_BROADCAST));
    }
}