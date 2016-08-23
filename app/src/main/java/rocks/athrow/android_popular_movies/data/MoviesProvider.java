package rocks.athrow.android_popular_movies.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by josel on 8/23/2016.
 */
public class MoviesProvider extends ContentProvider {
    private static final String LOG_TAG = "MoviesProvider";
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDBHelper mOpenHelper;

    private static final int MOVIES = 100;
    private static final int MOVIE_ID = 101;
    private static final int REVIEWS = 102;
    private static final int TRAILERS = 103;

    // Declare the query builders
    private static final SQLiteQueryBuilder sMoviesQueryBuilder;
    private static final SQLiteQueryBuilder sReviewsQueryBuilder;
    private static final SQLiteQueryBuilder sTrailersQueryBuilder;
    // Set the tables for each query builder
    static {
        sMoviesQueryBuilder = new SQLiteQueryBuilder();
        sMoviesQueryBuilder.setTables(
                MovieContract.MovieEntry.MOVIES_TABLE_NAME
        );

        sReviewsQueryBuilder = new SQLiteQueryBuilder();
        sReviewsQueryBuilder.setTables(
                MovieContract.ReviewsEntry.REVIEWS_TABLE_NAME
        );

        sTrailersQueryBuilder = new SQLiteQueryBuilder();
        sTrailersQueryBuilder.setTables(
                MovieContract.TrailersEntry.TRAILERS_TABLE_NAME
        );
    }

    //sMovieByID build the url to get a movie by id
    private static final String sMovieByID =
            MovieContract.MovieEntry.MOVIES_TABLE_NAME+
                    "." + MovieContract.MovieEntry._id + " = ? ";

    //sReviewsByID build the url to get movie reviews by id
    private static final String sReviewsByID =
            MovieContract.ReviewsEntry.REVIEWS_TABLE_NAME+
                    "." + MovieContract.ReviewsEntry.review_movie_id + " = ? ";

    //sTrailersByID build the url to get movie trailers by id
    private static final String sTrailersByID =
            MovieContract.TrailersEntry.TRAILERS_TABLE_NAME+
                    "." + MovieContract.TrailersEntry.trailer_movie_id + " = ? ";

    /**
     * getMoviesByID
     * @param uri the movie uri
     * @param projection the fields requested
     * @param sortOrder the order by parameter
     * @return the movie cursor
     */
    private Cursor getMovieByID(Uri uri, String[] projection, String sortOrder) {
        Cursor returnCursor;
        MergeCursor result;
        String movieId;
        String movieSelection = sMovieByID;
        String reviewsSelection = sReviewsByID;
        String trailersSelection = sTrailersByID;
        //---------------------------------------------------------
        // MOVIE
        //---------------------------------------------------------
        String id= MovieContract.MovieEntry.getMovieIDFromUI(uri);
        String[] movieSelectionArgs = {id};
        Cursor movieCursor = sMoviesQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                movieSelection,
                movieSelectionArgs,
                null,
                null,
                sortOrder + " DESC"
        );
        movieCursor.moveToFirst();
        movieId = movieCursor.getString(1);
        //---------------------------------------------------------
        // Reviews Cursor
        //---------------------------------------------------------
        String[] reviewsSelectionArgs = {movieId};
        Cursor reviewsCursor = sReviewsQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                null,
                reviewsSelection,
                reviewsSelectionArgs,
                null,
                null,
                null
        );
        reviewsCursor.moveToFirst();
        //---------------------------------------------------------
        // Trailers Cursor
        //---------------------------------------------------------
        String[] trailersSelectionArgs = {movieId};
        Cursor trailersCursor = sTrailersQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                null,
                trailersSelection,
                trailersSelectionArgs,
                null,
                null,
                null
        );
        trailersCursor.moveToFirst();
        Log.e(LOG_TAG, "trailersCursor -> " + trailersCursor.getExtras());
        //---------------------------------------------------------
        // The final complete Cursor
        //---------------------------------------------------------
        Cursor[] finalCursor = new Cursor[3];
        // Combine the movie, reviews, and trailers cursors
        finalCursor[0] = movieCursor;
        finalCursor[1] = reviewsCursor;
        finalCursor[2] = trailersCursor;
        result = new MergeCursor(finalCursor);
        // Return the final cursor
        returnCursor = result;
        return returnCursor ;
    }


    /**
     * buildUriMatcher
     * @return the uri matching the request
     */
    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, MovieContract.PATH_MOVIES, MOVIES);
        matcher.addURI(authority, MovieContract.PATH_MOVIES + "/#", MOVIE_ID);
        matcher.addURI(authority, MovieContract.PATH_REVIEWS, REVIEWS);
        matcher.addURI(authority, MovieContract.PATH_TRAILERS, TRAILERS);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        //Log.e(LOG_TAG, "onCreate -> " + true);
        mOpenHelper = new MovieDBHelper(getContext());
        return true;
    }

    /*
        Students: Here's where you'll code the getType function that uses the UriMatcher.  You can
        test this by uncommenting testGetType in TestProvider.
     */
    @Override
    public String getType(Uri uri) {
        //Log.e(LOG_TAG, "getType -> " + uri);
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        Log.e(LOG_TAG, "getType -> " + true);
        switch (match) {
            // Student: Uncomment and fill out these two cases
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            case REVIEWS:
                return MovieContract.ReviewsEntry.CONTENT_TYPE;
            case TRAILERS:
                return MovieContract.TrailersEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        //Log.e(LOG_TAG, "query -> " + uri);

        switch (sUriMatcher.match(uri)) {

            case MOVIES:
            {
                //Log.e(LOG_TAG, "query -> " + "MOVIES");
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.MOVIES_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case MOVIE_ID:{
                Log.e(LOG_TAG, "query -> " + uri);
                retCursor = getMovieByID(uri, projection, sortOrder);
                break;
            }
            case REVIEWS:
            {
                //Log.e(LOG_TAG, "query -> " + "MOVIES");
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.ReviewsEntry.REVIEWS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case TRAILERS:
            {
                //Log.e(LOG_TAG, "query -> " + "MOVIES");
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TrailersEntry.TRAILERS_TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // This causes the cursor to register a content observer
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    /*
        Student: Add the ability to insert Locations to the implementation of this function.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES:
            {
                long _id = db.insert(MovieContract.MovieEntry.MOVIES_TABLE_NAME, null, values);
                if (_id > 0) returnUri = MovieContract.MovieEntry.buildMoviesUri(_id);
                else throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case REVIEWS:
            {
                long _id = db.insert(MovieContract.ReviewsEntry.REVIEWS_TABLE_NAME, null, values);
                if (_id > 0) returnUri = MovieContract.ReviewsEntry.buildReviewsURI(_id);
                else throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TRAILERS:
            {
                long _id = db.insert(MovieContract.TrailersEntry.TRAILERS_TABLE_NAME, null, values);
                if (_id > 0) returnUri = MovieContract.TrailersEntry.buildTrailersUri(_id);
                else throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case MOVIES:
                rowsDeleted = db.delete(
                        MovieContract.MovieEntry.MOVIES_TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }



    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MOVIES:
                Log.e(LOG_TAG, "updateMovies " + true);
                rowsUpdated = db.update(MovieContract.MovieEntry.MOVIES_TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case REVIEWS:
                Log.e(LOG_TAG, "updateReviews " + true);
                rowsUpdated = db.update(MovieContract.ReviewsEntry.REVIEWS_TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case TRAILERS:
                Log.e(LOG_TAG, "updateTrailers " + true);
                rowsUpdated = db.update(MovieContract.TrailersEntry.TRAILERS_TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value: values) {

                        long _id = db.insert(MovieContract.MovieEntry.MOVIES_TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override@TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
