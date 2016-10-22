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
    public OnTaskComplete mListener = null;

    public FetchTask(OnTaskComplete listener) {
        this.mListener = listener;
    }

    @Override
    protected APIResponse doInBackground(String... String) {
        return API.getMoviesFromAPI();
    }

    @Override
    protected void onPostExecute(APIResponse apiResponse) {
        super.onPostExecute(apiResponse);
        mListener.OnTaskComplete(apiResponse);
    }
}
