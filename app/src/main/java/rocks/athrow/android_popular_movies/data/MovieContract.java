package rocks.athrow.android_popular_movies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by josel on 8/23/2016.
 */
public class MovieContract {
    //private static final String LOG_TAG = "MovieContract";
    /**
     * The Content Authority of the Movies Provider
     * <p/>
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     **/
    public static final String CONTENT_AUTHORITY = "com.rocks.athr0w.android-popular-movies";
    /**
     * The content URI for the top-level authority
     */
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * The path to the Movies database
     */
    public static final String PATH_MOVIES = "movies";
    // The path to the movies table
    private static final String PATH_MOVIE_ID = "movie/";
    // The path to the reviews table
    public static final String PATH_REVIEWS = "reviews";
    // The path to the reviews table
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
        //movies table
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
        public static final String db_table = "db_table";
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

        // Reviews table
        public static final String _id = "_id";
        public static final String REVIEWS_TABLE_NAME = "reviews";
        public static final String review_id = "id";
        public static final String review_movie_id = "movie_id";
        public static final String review_author = "author";
        public static final String review_content = "content";
        public static final String review_url = "url";
        public static final String db_table = "db_table";

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

        // Reviews table
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
        public static final String db_table = "db_table";


        public static Uri buildTrailersUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
