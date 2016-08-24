package rocks.athrow.android_popular_movies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * TODO: Refactor to accept ContentValues and update the database, this is from my original movies project where I was inserting records one at a time
 * DataStore
 * A class to store movie data into the database
 * Created by josel on 8/23/2016.
 */
public final class DataStore {

    /**
     * addMovieToDB
     * Helper method to handle insertion of a new movies in the movies database.
     */
    public static void addMovieToDB(
            Context context,
            int movieID,
            String movieOriginalLanguage,
            String movieOriginalTitle,
            String movieOverview,
            String movieReleaseDate,
            String moviePosterPath,
            String moviePopularity,
            String movieTitle,
            String movieVideo,
            String movieVoteAverage,
            String movieVoteCount) {
        // First, check if the movie with this name exists in the db
        Cursor movieCursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                new String[]{MovieContract.MovieEntry._id},
                MovieContract.MovieEntry.movie_id + " = ?",
                new String[]{Integer.toString(movieID)},
                null);
        // Create the content values
        ContentValues movieValues = new ContentValues();
        // Update database
        //Log.e(LOG_TAG, "addMovieToDB -> " + "put movie " + movieTitle);
        movieValues.put(MovieContract.MovieEntry.movie_id, movieID);
        movieValues.put(MovieContract.MovieEntry.movie_original_language, movieOriginalLanguage);
        movieValues.put(MovieContract.MovieEntry.movie_original_title, movieOriginalTitle);
        movieValues.put(MovieContract.MovieEntry.movie_overview, movieOverview);
        movieValues.put(MovieContract.MovieEntry.movie_release_date, movieReleaseDate);
        movieValues.put(MovieContract.MovieEntry.movie_poster_path, moviePosterPath);
        movieValues.put(MovieContract.MovieEntry.movie_popularity, moviePopularity);
        movieValues.put(MovieContract.MovieEntry.movie_title, movieTitle);
        movieValues.put(MovieContract.MovieEntry.movie_video, movieVideo);
        movieValues.put(MovieContract.MovieEntry.movie_vote_average, movieVoteAverage);
        movieValues.put(MovieContract.MovieEntry.movie_vote_count, movieVoteCount);
        movieValues.put(MovieContract.MovieEntry.db_table, MovieContract.MovieEntry.MOVIES_TABLE_NAME);
        // If the record does not exist, add it to the database
        if (movieCursor != null) {
            movieCursor.moveToFirst();
            context.getContentResolver().insert(
                    MovieContract.MovieEntry.CONTENT_URI,
                    movieValues
            );
            movieCursor.close();
        }
        // if the record exists, update the database
        else {
            String[] x = new String[]{Long.toString(movieID)};
            context.getContentResolver().update(
                    MovieContract.MovieEntry.CONTENT_URI,
                    movieValues,
                    MovieContract.MovieEntry.movie_id + " = ?",
                    x
            );
        }
    }

    /**
     * addReviewToDB
     * Helper method to handle insertion of movie reviews in the movies database.
     */
    public static void addReviewToDB(
            Context context,
            String reviewID,
            Integer reviewMovieID,
            String reviewAuthor,
            String reviewContent,
            String reviewUrl
    ) {
        Cursor reviewCursor = context.getContentResolver().query(
                MovieContract.ReviewsEntry.CONTENT_URI,
                new String[]{MovieContract.ReviewsEntry._id},
                MovieContract.ReviewsEntry.review_id + " = ?",
                new String[]{reviewID},
                null);
        // Create the content values
        ContentValues reviewValues = new ContentValues();
        reviewValues.put(MovieContract.ReviewsEntry.review_id, reviewID);
        reviewValues.put(MovieContract.ReviewsEntry.review_movie_id, reviewMovieID);
        reviewValues.put(MovieContract.ReviewsEntry.review_author, reviewAuthor);
        reviewValues.put(MovieContract.ReviewsEntry.review_content, reviewContent);
        reviewValues.put(MovieContract.ReviewsEntry.review_url, reviewUrl);
        reviewValues.put(MovieContract.ReviewsEntry.db_table, MovieContract.ReviewsEntry.REVIEWS_TABLE_NAME);
        if (reviewCursor != null) {
            reviewCursor.moveToFirst();
            context.getContentResolver().insert(
                    MovieContract.ReviewsEntry.CONTENT_URI,
                    reviewValues
            );
            reviewCursor.close();
        } else {
            String[] x = new String[]{reviewID};
            context.getContentResolver().update(
                    MovieContract.ReviewsEntry.CONTENT_URI,
                    reviewValues,
                    MovieContract.ReviewsEntry.review_id + " = ?",
                    x
            );
        }
    }

    /**
     * addTrailerToDB
     * Helper method to handle insertion of a new trailers in the movies database.
     */
    public static void addTrailerToDB(
            Context context,
            String trailerID,
            Integer trailerMovieID,
            String trailerLanguage,
            String trailerKey,
            String trailerName,
            String trailerSite,
            Long trailerSize,
            String trailerType) {
        Cursor trailerCursor = context.getContentResolver().query(
                MovieContract.TrailersEntry.CONTENT_URI,
                new String[]{MovieContract.TrailersEntry._id},
                MovieContract.TrailersEntry.trailer_id + " = ?",
                new String[]{trailerID},
                null);
        // Get content values
        ContentValues trailerValues = new ContentValues();
        trailerValues.put(MovieContract.TrailersEntry.trailer_id, trailerID);
        trailerValues.put(MovieContract.TrailersEntry.trailer_movie_id, trailerMovieID);
        trailerValues.put(MovieContract.TrailersEntry.trailer_language, trailerLanguage);
        trailerValues.put(MovieContract.TrailersEntry.trailer_key, trailerKey);
        trailerValues.put(MovieContract.TrailersEntry.trailer_name, trailerName);
        trailerValues.put(MovieContract.TrailersEntry.trailer_site, trailerSite);
        trailerValues.put(MovieContract.TrailersEntry.trailer_size, trailerSize);
        trailerValues.put(MovieContract.TrailersEntry.trailer_type, trailerType);
        trailerValues.put(MovieContract.TrailersEntry.db_table, MovieContract.TrailersEntry.TRAILERS_TABLE_NAME);
        if (trailerCursor != null) {
            trailerCursor.moveToFirst();
            context.getContentResolver().insert(
                    MovieContract.TrailersEntry.CONTENT_URI,
                    trailerValues
            );
            trailerCursor.close();
        } else {
            String[] x = new String[]{trailerID};
            context.getContentResolver().update(
                    MovieContract.TrailersEntry.CONTENT_URI,
                    trailerValues,
                    MovieContract.TrailersEntry.trailer_id + " = ?",
                    x
            );
        }
    }
}
