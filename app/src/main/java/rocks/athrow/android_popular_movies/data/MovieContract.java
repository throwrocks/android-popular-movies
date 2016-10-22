package rocks.athrow.android_popular_movies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * MovieContract
 * Created by josel on 8/23/2016.
 */
public class MovieContract {
    public static final String CONTENT_AUTHORITY = "rocks.athrow.android_popular_movies";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";
    private static final String PATH_MOVIE_ID = "movie/";
    public static final String PATH_REVIEWS = "reviews";
    public static final String PATH_TRAILERS = "trailers";

    /**
     * MovieEntry
     * The inner class that defines the contents of the movies table
     */
    public static final class MovieEntry implements BaseColumns {
        // This is the complete path to the Movies database
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        // Returns multiple content
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        // Returns a single content
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_ID;

        // The internal id is used by all tables
        public static final String _id = "_id";
        public static final String MOVIES_TABLE_NAME = "movies";
        public static final String movie_id = "id";
        public static final String movie_original_language = "original_language";
        public static final String movie_original_title = "original_title";
        public static final String movie_overview = "overview";
        public static final String movie_release_date = "release_date";
        public static final String movie_poster_path = "poster_path";
        public static final String movie_popularity = "popularity";
        public static final String movie_title = "title";
        public static final String movie_video = "video";
        public static final String movie_vote_average = "vote_average";
        public static final String movie_vote_count = "vote_count";
        public static final String movie_is_favorite = "is_favorite";
        // Non database fields constants
        public static final String movie_release_year = "release_year";
        // The index of all the fields
        public static final int movie_id_index = 1;
        public static final int movie_overview_index = 4;
        public static final int movie_poster_release_date_index = 5;
        public static final int movie_poster_path_index = 6;
        public static final int movie_title_index = 8;
        public static final int movie_vote_count_index = 10;

        public static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovies() {
            return CONTENT_URI.buildUpon().build();
        }

        public static Uri buildMovieWithID(long id) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(id)).build();
        }

        // Method to get the movie id from the URI
        public static String getMovieIDFromUI(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
    /**
     * ReviewsEntry
     * The inner class that defines the contents of the movies table
     */
    public static final class ReviewsEntry implements BaseColumns {
        // This is the complete path to the Movies database
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEWS).build();
        // Returns multiple content
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEWS;

        public static final String _id = "_id";
        public static final String REVIEWS_TABLE_NAME = "reviews";
        public static final String review_id = "id";
        public static final String review_movie_id = "movie_id";
        public static final String review_author = "author";
        public static final String review_content = "content";
        public static final String review_url = "url";

        public static Uri buildReviewsURI(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
    /**
     * TrailersEntry
     * The inner class that defines the contents of the movies table
     */
    public static final class TrailersEntry implements BaseColumns {
        // This is the complete path to the Movies database
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILERS).build();
        // Returns multiple content
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILERS;

        public static final String _id = "_id";
        public static final String TRAILERS_TABLE_NAME = "trailers";
        public static final String trailer_id = "id";
        public static final String trailer_movie_id = "movie_id";
        public static final String trailer_language = "iso_639_1";
        public static final String trailer_key = "key";
        public static final String trailer_name = "name";
        public static final String trailer_site = "site";
        public static final String trailer_size = "size";
        public static final String trailer_type = "type";

        public static Uri buildTrailersUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
