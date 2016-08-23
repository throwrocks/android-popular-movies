package rocks.athrow.android_popular_movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josel on 8/23/2016.
 */
public class MovieDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_NAME = "movies.db";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /** ----------------------------------------------------
         * Create the movies table
         ----------------------------------------------------*/
        final String SQL_CREATE_MOVIES_TABLE =
                "CREATE TABLE " +
                        MovieContract.MovieEntry.MOVIES_TABLE_NAME + " (" +
                        MovieContract.MovieEntry._id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        // the ID of the location entry associated with this weather data
                        MovieContract.MovieEntry.movie_id + " INTEGER NOT NULL, " +
                        MovieContract.MovieEntry.movie_original_language + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.movie_original_title + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.movie_overview + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.movie_release_date + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.movie_poster_path + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.movie_popularity + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.movie_title + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.movie_video + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.movie_vote_average + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.movie_vote_count + " TEXT NOT NULL, " +
                        MovieContract.MovieEntry.movie_is_favorite + " INTEGER NULL, " +
                        MovieContract.MovieEntry.db_table + " TEXT NOT NULL " +
                        ")";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
        /** ----------------------------------------------------
         * Create the reviews table
         ----------------------------------------------------*/
        final String SQL_CREATE_REVIEWS_TABLE  =
                "CREATE TABLE " +
                        MovieContract.ReviewsEntry.REVIEWS_TABLE_NAME + " (" +
                        MovieContract.ReviewsEntry._id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MovieContract.ReviewsEntry.review_id + " TEXT NOT NULL, " +
                        MovieContract.ReviewsEntry.review_movie_id + " INTEGER NOT NULL, " +
                        MovieContract.ReviewsEntry.review_author + " TEXT NOT NULL, " +
                        MovieContract.ReviewsEntry.review_content + " TEXT NOT NULL, " +
                        MovieContract.ReviewsEntry.review_url + " TEXT NOT NULL, " +
                        MovieContract.ReviewsEntry.db_table + " TEXT NOT NULL " +
                        ")";
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEWS_TABLE);
        /** ----------------------------------------------------
         * Create the trailers table
         ----------------------------------------------------*/
        final String SQL_CREATE_TRAILERS_TABLE  =
                "CREATE TABLE " +
                        MovieContract.TrailersEntry.TRAILERS_TABLE_NAME + " (" +
                        MovieContract.TrailersEntry._id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MovieContract.TrailersEntry.trailer_id + " TEXT NOT NULL, " +
                        MovieContract.TrailersEntry.trailer_movie_id + " INTEGER NOT NULL, " +
                        MovieContract.TrailersEntry.trailer_language + " TEXT NOT NULL, " +
                        MovieContract.TrailersEntry.trailer_key + " TEXT NOT NULL, " +
                        MovieContract.TrailersEntry.trailer_name + " TEXT NOT NULL, " +
                        MovieContract.TrailersEntry.trailer_site + " TEXT NOT NULL," +
                        MovieContract.TrailersEntry.trailer_size + " INTEGER NOT NULL, " +
                        MovieContract.TrailersEntry.trailer_type + " TEXT NOT NULL, " +
                        MovieContract.TrailersEntry.db_table + " TEXT NOT NULL " +
                        ")";
        sqLiteDatabase.execSQL(SQL_CREATE_TRAILERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.MOVIES_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
