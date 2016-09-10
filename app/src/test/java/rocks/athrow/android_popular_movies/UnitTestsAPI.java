package rocks.athrow.android_popular_movies;


import android.content.ContentValues;
import android.content.Context;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Random;

import rocks.athrow.android_popular_movies.data.API;
import rocks.athrow.android_popular_movies.data.APIResponse;
import rocks.athrow.android_popular_movies.data.JSONParser;
import rocks.athrow.android_popular_movies.data.MovieContract;
import rocks.athrow.android_popular_movies.data.MoviesProvider;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class UnitTestsAPI extends Robolectric {
    private final static String ID = "id";
    private final static String EMPTY_STRING = "";
    private static APIResponse mApiResponse;
    private static String moviesResultJSON;
    private static ContentValues[] moviesContentValues;

    public static APIResponse getMoviesResultJSON() {
        return API.getMoviesFromAPI();

    }

    public static APIResponse getReviewsResultJSON(String movieId) {
        return API.getMovieReviewsFromAPI(movieId);

    }

    public static APIResponse getTrailersResultJSON(String movieId) {
        return API.getMovieTrailersFromAPI(movieId);
    }

    public static ContentValues[] getMoviesContentValues(String moviesResult) {
        return JSONParser.getMoviesFromJSON(moviesResult);
    }

    public static ContentValues[] getReviewsContentValues(String reviewsResult) {
        return JSONParser.getReviewsFromJSON(reviewsResult);
    }

    public static ContentValues[] getTrailersContentValues(String trailersResult) {
        return JSONParser.getTrailersFromJSON(trailersResult);
    }

    public static int getRandomNumber(int start, int end) {
        Random r = new Random();
        return r.nextInt(end - start) + start;
    }

    @BeforeClass
    public static void setUp() {
        mApiResponse = getMoviesResultJSON();
        if (mApiResponse.getResponseCode() == 200) {
            moviesResultJSON = mApiResponse.getResponseText();
            moviesContentValues = getMoviesContentValues(moviesResultJSON);
        }
    }

    /**
     * testGetMoviesFromAPI
     * Test that the API returns a non-null, non-empty string
     * TODO: Check that the JSON result has a results array
     *
     * @throws Exception
     */
    @Test
    public void testGetMoviesFromAPI() throws Exception {
        assertTrue(moviesResultJSON != null && !moviesResultJSON.equals(EMPTY_STRING));
    }

    /**
     * testGetMoviesContentValuesReviewsFromAPI
     * Test that the JSONParser method returns a non-null, non-empty array of ContentValues
     *
     * @throws Exception
     */
    @Test
    public void testGetMoviesContentValues() throws Exception {
        assertTrue(moviesContentValues.length > 0);
    }

    /**
     * testGetMovieId
     * Test that all the movies have a non-null, non-empty id
     *
     * @throws Exception
     */
    @Test
    public void getMovieIDs() throws Exception {
        for (ContentValues item : moviesContentValues) {
            String movieId = item.getAsString(ID);
            assertTrue(movieId != null && !movieId.equals(EMPTY_STRING));
        }
    }

    /**
     * testGetMoviesReviews
     * Test calling the API for a random movie's reviews
     * The result should be non-null, non-empty
     * TODO: Check that the JSON result has a results array
     *
     * @throws Exception
     */
    @Test
    public void testGetMovieReviews() throws Exception {
        ContentValues item = moviesContentValues[getRandomNumber(1, moviesContentValues.length)];
        String movieId = item.getAsString(ID);
        APIResponse apiResponse = getReviewsResultJSON(movieId);
        switch (apiResponse.getResponseCode()) {
            case 200:
                assertTrue(true);
                break;
            default:
                assertTrue(false);
        }
    }

    /**
     * testGetMovieTrailers
     * Test calling the API for a random movie's trailers
     * The result should be non-null, non-empty
     * TODO: Check that the JSON result has a results array
     *
     * @throws Exception
     */
    @Test
    public void testGetMovieTrailers() throws Exception {
        ContentValues item = moviesContentValues[getRandomNumber(1, moviesContentValues.length)];
        String movieId = item.getAsString(ID);
        APIResponse apiResponse = getTrailersResultJSON(movieId);
        switch (apiResponse.getResponseCode()) {
            case 200:
                assertTrue(true);
                break;
            default:
                assertTrue(false);
        }
    }

    /**
     * testDatabaseBulkInsert
     * Test a bulk insert into the database
     * Returns the number of records inserted
     */
    @Test
    public void testDatabaseBulkInsert() {
        Context context = RuntimeEnvironment.application.getApplicationContext();
        MoviesProvider moviesProvider = new MoviesProvider(context);
        int result = moviesProvider.bulkInsert(MovieContract.MovieEntry.CONTENT_URI, moviesContentValues);
        assertTrue(result > 0);
    }
}