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
import rocks.athrow.android_popular_movies.fragment.MovieDetailFragment;

/**
 * UpdateDBService
 * Created by jose on 9/16/16.
 */
public class UpdateDBService extends IntentService {
    private static final String SERVICE_NAME = "UpdateDBService";
    public static final String UPDATE_MOVIES_DB_SERVICE_BROADCAST = "UpdateMoviesBroadcast";
    public static final String UPDATE_REVIEWS_DB_SERVICE_BROADCAST = "UpdateReviewsBroadcast";
    public UpdateDBService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Bundle arguments = workIntent.getExtras();
        String intentType = arguments.getString(MovieListActivity.INTENT_TYPE);
        String JSON = arguments.getString(MovieListActivity.INTENT_EXTRA);
        if (intentType != null && JSON != null) {
            MoviesProvider moviesProvider = new MoviesProvider(getApplicationContext());
            switch (intentType) {
                case MovieListActivity.INTENT_TYPE_MOVIES:
                    ContentValues[] moviesContentValues;
                    moviesContentValues = JSONParser.getMoviesFromJSON(JSON);
                    moviesProvider.bulkInsert(MovieContract.MovieEntry.CONTENT_URI, moviesContentValues);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(UPDATE_MOVIES_DB_SERVICE_BROADCAST));
                    break;
                case MovieDetailFragment.INTENT_TYPE_REVIEWS:
                    int movieId = arguments.getInt("movie_id");
                    ContentValues[] reviewsContentValues;
                    reviewsContentValues = JSONParser.getReviewsFromJSON(JSON);
                    int countValues = reviewsContentValues.length;
                    int i = 0;
                    while (i < countValues){
                       reviewsContentValues[i].put("movie_id", movieId);
                       i ++;
                    }
                    moviesProvider.bulkInsert(MovieContract.ReviewsEntry.CONTENT_URI, reviewsContentValues);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(UPDATE_REVIEWS_DB_SERVICE_BROADCAST));

            }
        }
    }
}