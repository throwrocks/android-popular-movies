package rocks.athrow.android_popular_movies.data;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import rocks.athrow.android_popular_movies.BuildConfig;

/**
 * API
 * Created by josel on 8/22/2016.
 */
public final class API {
    private API() {
        throw new AssertionError("No API instances for you!");
    }
    private static final String API_TYPE_MOVIES = "movies";
    private static final String API_TYPE_REVIEWS = "reviews";
    private static final String API_TYPE_TRAILERS = "trailers";
    private static final String API_KEY = BuildConfig.APIKey;
    private static final String API_KEY_ARG = "&api_key=" + API_KEY;
    private static final String API_DISCOVER_MOVIES_URL = "http://api.themoviedb.org/3/discover/movie?";
    private static final String API_MOVIE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String API_YOUTUBE_URL = "http://youtu.be/";

    /**
     * getMoviesFromAPI
     * @return a JSON string containing the most popular movies
     */
    public static APIResponse getMoviesFromAPI(){
        String movieRequestURL = API_DISCOVER_MOVIES_URL + "popularity" + API_KEY_ARG;
        return httpConnect(API_TYPE_MOVIES, movieRequestURL);
    }

    /**
     * getMovieReviewsFromAPI
     * @param movieID the movie id for the movie that we want to get the results
     * @return a JSON string containing the movie's reviews
     */
    public static APIResponse getMovieReviewsFromAPI(String movieID){
        String movieRequestURL = API_MOVIE_URL + movieID + "/reviews?" + API_KEY_ARG;
        return httpConnect(API_TYPE_REVIEWS, movieRequestURL);
    }
    /**
     * getMovieReviewsFromAPI
     * @param movieID the movie id for the movie that we want to get the results
     * @return a JSON string containing the movie's reviews
     */
    public static APIResponse getMovieTrailersFromAPI(String movieID){
        String movieRequestURL = API_MOVIE_URL + movieID + "/videos?" + API_KEY_ARG;
        return httpConnect(API_TYPE_TRAILERS, movieRequestURL);
    }
    /**
     * callAPIURL
     * Use this method to call the API and get the results
     * @param movieRequestURL the full moviedb api url
     * @return a string of json data
     */
    private static APIResponse httpConnect(String APIType, String movieRequestURL){
        APIResponse apiResponse = new APIResponse(APIType);
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
            apiResponse.setResponseCode(urlConnection.getResponseCode());
            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                return apiResponse;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (buffer.length() == 0) {
                return apiResponse;
            }
            apiResponse.setResponseText(buffer.toString());
        } catch (IOException v) {
            apiResponse.setResponseText(v.toString());
            return apiResponse;
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
        return apiResponse;
    }

}
