package rocks.athrow.android_popular_movies.data;

import android.os.AsyncTask;

import rocks.athrow.android_popular_movies.interfaces.OnTaskComplete;

/**
 * FetchTask
 * A class to manage making calls to query the API
 * <p/>
 * Created by josel on 8/23/2016.
 */
public class FetchTask extends AsyncTask<String, Void, APIResponse> {
    private String mType;
    private String mMovieId;
    private OnTaskComplete mListener = null;

    public FetchTask(String type, String movieId, OnTaskComplete listener) {
        this.mType = type;
        this.mMovieId = movieId;
        this.mListener = listener;
    }

    @Override
    protected APIResponse doInBackground(String... String) {
        APIResponse apiResponse = new APIResponse();
        switch (mType) {
            case "getMovies":
                apiResponse = API.getMoviesFromAPI();
                break;
            case "getReviews":
                apiResponse = API.getMovieReviewsFromAPI(mMovieId);
        }
        return apiResponse;
    }

    @Override
    protected void onPostExecute(APIResponse apiResponse) {
        super.onPostExecute(apiResponse);
        mListener.OnTaskComplete(apiResponse);
    }
}
