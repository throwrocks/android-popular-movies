package rocks.athrow.android_popular_movies.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
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
        Parcelable[] moviesParcelable = (Parcelable[]) arguments.get(MovieListActivity.INTENT_EXTRA);
        if (moviesParcelable != null) {
            int size = moviesParcelable.length;
            ContentValues[] moviesContentValues = new ContentValues[size];
            int i = 0;
            while (i < size) {
                int value_movie_id = Integer.parseInt(((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_id).toString());
                String value_movie_original_language = ((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_original_language).toString();
                String value_movie_original_title = ((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_original_title).toString();
                String value_movie_overview = ((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_overview).toString();
                String value_movie_release_date =((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_release_date).toString();
                String value_movie_poster_path = ((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_poster_path).toString();
                Double value_movie_popularity = Double.parseDouble(((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_popularity).toString());
                String value_movie_title = ((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_title).toString();
                String value_movie_video = ((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_video).toString();
                Double value_movie_vote_average = Double.parseDouble(((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_vote_average).toString());
                String value_movie_vote_count = ((ContentValues) moviesParcelable[i]).get(MovieContract.MovieEntry.movie_vote_count).toString();
                ContentValues movieContentValues = new ContentValues();
                movieContentValues.put(MovieContract.MovieEntry.movie_id, value_movie_id);
                movieContentValues.put(MovieContract.MovieEntry.movie_original_language, value_movie_original_language);
                movieContentValues.put(MovieContract.MovieEntry.movie_original_title, value_movie_original_title);
                movieContentValues.put(MovieContract.MovieEntry.movie_overview, value_movie_overview);
                movieContentValues.put(MovieContract.MovieEntry.movie_release_date, value_movie_release_date);
                movieContentValues.put(MovieContract.MovieEntry.movie_poster_path, value_movie_poster_path);
                movieContentValues.put(MovieContract.MovieEntry.movie_popularity, value_movie_popularity);
                movieContentValues.put(MovieContract.MovieEntry.movie_title, value_movie_title);
                movieContentValues.put(MovieContract.MovieEntry.movie_video, value_movie_video);
                movieContentValues.put(MovieContract.MovieEntry.movie_vote_average, value_movie_vote_average);
                movieContentValues.put(MovieContract.MovieEntry.movie_vote_count, value_movie_vote_count);
                moviesContentValues[i] = movieContentValues;
                i++;
            }
            MoviesProvider moviesProvider = new MoviesProvider(getApplicationContext());
            moviesProvider.bulkInsert(MovieContract.MovieEntry.CONTENT_URI, moviesContentValues);

        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MovieListActivity.UPDATE_DB_BROADCAST)
        );
    }
}