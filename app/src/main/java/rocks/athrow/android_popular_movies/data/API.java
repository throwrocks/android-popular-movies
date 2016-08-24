package rocks.athrow.android_popular_movies.data;

import android.net.Uri;
import android.os.Build;
import android.support.design.BuildConfig;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by josel on 8/22/2016.
 */
public class API {
    private static final String API_KEY = "";
    private static final String API_KEY_ARG = "&api_key=" + API_KEY;
    private static final String API_DISCOVER_MOVIES_URL = "http://api.themoviedb.org/3/discover/movie?";
    private static final String API_MOVIE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String API_YOUTUBE_URL = "http://youtu.be/";

    /**
     * getMoviesFromAPI
     * @return a JSON string containing the most popular movies
     */
    public static String getMoviesFromAPI(){
        String movieRequestURL = API_DISCOVER_MOVIES_URL + "popularity" + API_KEY_ARG;
        return callAPIString(movieRequestURL);
    }

    /**
     * getMovieReviewsFromAPI
     * @param movieID the movie id for the movie that we want to get the results
     * @return a JSON string containing the movie's reviews
     */
    public static String getMovieReviewsFromAPI(String movieID){
        String movieRequestURL = API_MOVIE_URL + movieID + "/reviews?" + API_KEY_ARG;
        return callAPIString(movieRequestURL);
    }
    /**
     * getMovieReviewsFromAPI
     * @param movieID the movie id for the movie that we want to get the results
     * @return a JSON string containing the movie's reviews
     */
    public static String getMovieTrailersFromAPI(String movieID){
        String movieRequestURL = API_MOVIE_URL + movieID + "/videos?" + API_KEY_ARG;
        return callAPIString(movieRequestURL);
    }
    /**
     * callAPIURL
     * Use this method to call the API and get the results
     * @param movieRequestURL the full moviedb api url
     * @return a sring of json data
     */
    private static String callAPIString (String movieRequestURL){
        String results = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            // Build the URL
            Uri builtUri = Uri.parse(movieRequestURL).buildUpon().build();
            URL url = new URL(builtUri.toString());
            // Establish the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                results = null;
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging ->  + "\n"
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                results = null;
            }
            results = buffer.toString();
        } catch (IOException v) {
            results = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }


}
