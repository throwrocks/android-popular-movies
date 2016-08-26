package rocks.athrow.android_popular_movies;


import android.content.ContentValues;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rocks.athrow.android_popular_movies.data.API;
import rocks.athrow.android_popular_movies.data.JSONParser;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class UnitTests extends Robolectric {
    private final static String ID = "id";
    private final static String EMPTY_STRING = "";
    private String moviesResultJSON;

    public String getMoviesResultJSON() {
        return API.getMoviesFromAPI();
    }

    public String getReviewsResultJSON(String movieId) {
        return API.getMovieReviewsFromAPI(movieId);
    }

    public String getTrailersResultJSON(String movieId) {
        return API.getMovieTrailersFromAPI(movieId);
    }

    public ContentValues[] getMoviesContentValues(String moviesResult) {
        return JSONParser.getMoviesFromJSON(moviesResult);
    }

    public ContentValues[] getReviewsContentValues(String reviewsResult) {
        return JSONParser.getReviewsFromJSON(reviewsResult);
    }

    public ContentValues[] getTrailersContentValues(String trailersResult) {
        return JSONParser.getTrailersFromJSON(trailersResult);
    }

    @Before
    public void setUp(){
        moviesResultJSON = getMoviesResultJSON();
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
        ContentValues[] moviesContentValues = getMoviesContentValues(moviesResultJSON);
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
        ContentValues[] contentValues = getMoviesContentValues(moviesResultJSON);
        for (ContentValues item : contentValues) {
            String movieId = item.getAsString(ID);
            assertTrue(movieId != null && !movieId.equals(EMPTY_STRING));
        }
    }

    /**
     * testGetMoviesReviews
     * Test calling the API for each movie's reviews
     * The result should be non-null, non-empty
     * TODO: Check that the JSON result has a results array
     *
     * @throws Exception
     */
    @Test
    public void testGetMovieReviews() throws Exception {
        ContentValues[] contentValues = getMoviesContentValues(moviesResultJSON);
        for (ContentValues item : contentValues) {
            String movieId = item.getAsString(ID);
            String results = getReviewsResultJSON(movieId);
            assertTrue(results != null);
        }
    }

    /**
     *
     */
    @Test
    public void testGetMovieTrailers() throws Exception {
        ContentValues[] contentValues = getMoviesContentValues(moviesResultJSON);
        for (ContentValues item : contentValues) {
            String movieId = item.getAsString(ID);
            String results = getTrailersResultJSON(movieId);
            assertTrue(results != null);
        }
    }
}