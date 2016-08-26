package rocks.athrow.android_popular_movies;


import android.content.ContentValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import rocks.athrow.android_popular_movies.data.API;
import rocks.athrow.android_popular_movies.data.JSONParser;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class UnitTests extends Mockito{
    String moviesResultJSON;

    public ContentValues[] getMoviesContentValues(String moviesResult){
        return JSONParser.getMoviesFromJSON(moviesResult);
    }

    @Before
    public void getMoviesFromAPI(){
        moviesResultJSON = API.getMoviesFromAPI();
    }

    /**
     * testGetMoviesFromAPI
     * @throws Exception
     */
    @Test
    public void testGetMoviesFromAPI() throws Exception{
        assertTrue(moviesResultJSON != null);
    }

    /**
     * testGetMovieReviewsFromAPI
     * @throws Exception
     */
    @Test
    public void testGetMoviesContentValues() throws Exception{
        ContentValues[] moviesContentValues = getMoviesContentValues(moviesResultJSON) ;
        assertTrue(moviesContentValues.length > 0);
    }

    /**
     * testGetMovieId
     * @throws Exception
     */
    @Test
    public void testGetMovieId() throws Exception{
        ContentValues[] movies = JSONParser.getMoviesFromJSON(moviesResultJSON);
        String movieId = movies[0].getAsString("id");
        assertFalse(movieId != null && movieId.equals(""));
    }

}