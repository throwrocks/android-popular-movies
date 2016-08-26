package rocks.athrow.android_popular_movies.data;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONParser
 * A class to parse JSON data from the Movies API
 * Created by josel on 8/23/2016.
 */
public class JSONParser {
    /**
     * getMoviesFromJSON
     *
     * @param moviesJSONString the JSON string of movies data
     * @return a ContentValues array with the movies records
     */
    public static ContentValues[] getMoviesFromJSON(String moviesJSONString) {
        final String mdb_list = "results";
        final String field_movie_id = "id";
        final String field_movie_original_language = "original_language";
        final String field_movie_original_title = "original_title";
        final String field_movie_overview = "overview";
        final String field_movie_release_date = "release_date";
        final String field_movie_poster_path = "poster_path";
        final String field_movie_popularity = "popularity";
        final String field_movie_title = "title";
        final String field_movie_video = "video";
        final String field_movie_vote_average = "vote_average";
        final String field_movie_vote_count = "vote_count";
        ContentValues[] moviesContentValues = null;
        try {
            JSONObject moviesJson = new JSONObject(moviesJSONString);
            JSONArray moviesArray = moviesJson.getJSONArray(mdb_list);
            int moviesQty = moviesArray.length();
            moviesContentValues = new ContentValues[moviesQty];
            for (int i = 0; i < moviesQty; i++) {
                JSONObject movieRecord = moviesArray.getJSONObject(i);
                int value_movie_id = movieRecord.getInt(field_movie_id);
                String value_movie_original_language = movieRecord.getString(field_movie_original_language);
                String value_movie_original_title = movieRecord.getString(field_movie_original_title);
                String value_movie_overview = movieRecord.getString(field_movie_overview);
                String value_movie_release_date = movieRecord.getString(field_movie_release_date);
                String value_movie_poster_path = movieRecord.getString(field_movie_poster_path);
                Double value_movie_popularity = Double.parseDouble(movieRecord.getString(field_movie_popularity));
                String value_movie_title = movieRecord.getString(field_movie_title);
                String value_movie_video = movieRecord.getString(field_movie_video);
                Double value_movie_vote_average = Double.parseDouble(movieRecord.getString(field_movie_vote_average));
                String value_movie_vote_count = movieRecord.getString(field_movie_vote_count);
                ContentValues movieContentValues = new ContentValues();
                movieContentValues.put(field_movie_id, value_movie_id);
                movieContentValues.put(field_movie_original_language, value_movie_original_language);
                movieContentValues.put(field_movie_original_title, value_movie_original_title);
                movieContentValues.put(field_movie_overview, value_movie_overview);
                movieContentValues.put(field_movie_release_date, value_movie_release_date);
                movieContentValues.put(field_movie_poster_path, value_movie_poster_path);
                movieContentValues.put(field_movie_popularity, value_movie_popularity);
                movieContentValues.put(field_movie_title, value_movie_title);
                movieContentValues.put(field_movie_video, value_movie_video);
                movieContentValues.put(field_movie_vote_average, value_movie_vote_average);
                movieContentValues.put(field_movie_vote_count, value_movie_vote_count);
                moviesContentValues[i] = movieContentValues;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesContentValues;
    }

    /**
     * getReviewsFromJSON
     *
     * @param reviewsJSONString the JSON string of reviews data
     * @return a ContentValues array with the trailer records
     */
    public static ContentValues[] getReviewsFromJSON(String reviewsJSONString) {
        ContentValues[] reviewsContentValues = null;
        final String reviews_list = "results";
        final String field_review_id = "id";
        final String field_review_author = "author";
        final String field_review_content = "content";
        final String field_review_url = "url";
        try {
            if (reviewsJSONString != null) {
                JSONObject reviewsJSON = new JSONObject(reviewsJSONString);
                JSONArray reviewsArray = reviewsJSON.getJSONArray(reviews_list);
                int reviewsQty = reviewsArray.length();
                reviewsContentValues = new ContentValues[reviewsQty];
                for (int i = 0; i < reviewsQty; i++) {
                    JSONObject reviewRecord = reviewsArray.getJSONObject(i);
                    if (reviewRecord.length() > 0) {
                        String value_review_id = reviewRecord.getString(field_review_id);
                        String value_review_author = reviewRecord.getString(field_review_author);
                        String value_review_content = reviewRecord.getString(field_review_content);
                        String value_review_url = reviewRecord.getString(field_review_url);
                        ContentValues reviewContentValues = new ContentValues();
                        reviewContentValues.put(field_review_id, value_review_id);
                        reviewContentValues.put(field_review_author, value_review_author);
                        reviewContentValues.put(field_review_content, value_review_content);
                        reviewContentValues.put(field_review_url, value_review_url);
                        reviewsContentValues[i] = reviewContentValues;
                    } else {
                        reviewsContentValues[i].put("error", "No reviews");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewsContentValues;
    }

    /**
     * getTrailersFromJSON
     *
     * @param trailersJSONString the JSON string of trailer data
     * @return a ContentValues array with the trailer records
     */
    public static ContentValues[] getTrailersFromJSON(String trailersJSONString) {
        ContentValues[] trailersContentValues = null;
        final String trailers_list = "results";
        final String field_trailer_id = "id";
        final String field_trailer_language = "iso_639_1";
        final String field_trailer_key = "key";
        final String field_trailer_name = "name";
        final String field_trailer_site = "site";
        final String field_trailer_size = "size";
        final String field_trailer_type = "type";
        try {
            if (trailersJSONString != null) {
                JSONObject trailersJSON = new JSONObject(trailersJSONString);
                JSONArray trailersArray = trailersJSON.getJSONArray(trailers_list);
                int trailersQTY = trailersArray.length();
                trailersContentValues = new ContentValues[trailersQTY];
                for (int d = 0; d < trailersQTY; d++) {
                    JSONObject trailerRecord = trailersArray.getJSONObject(d);
                    String value_trailer_id = trailerRecord.getString(field_trailer_id);
                    String value_trailer_language = trailerRecord.getString(field_trailer_language);
                    String value_trailer_key = trailerRecord.getString(field_trailer_key);
                    String value_trailer_name = trailerRecord.getString(field_trailer_name);
                    String value_trailer_site = trailerRecord.getString(field_trailer_site);
                    Long value_trailer_size = trailerRecord.getLong(field_trailer_size);
                    String value_trailer_type = trailerRecord.getString(field_trailer_type);
                    ContentValues trailerContentValues = new ContentValues();
                    trailerContentValues.put(field_trailer_id, value_trailer_id);
                    trailerContentValues.put(field_trailer_language, value_trailer_language);
                    trailerContentValues.put(field_trailer_key, value_trailer_key);
                    trailerContentValues.put(field_trailer_name, value_trailer_name);
                    trailerContentValues.put(field_trailer_site, value_trailer_site);
                    trailerContentValues.put(field_trailer_size, value_trailer_size);
                    trailerContentValues.put(field_trailer_type, value_trailer_type);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailersContentValues;
    }
}