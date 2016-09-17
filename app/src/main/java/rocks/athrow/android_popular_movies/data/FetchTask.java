package rocks.athrow.android_popular_movies.data;

import android.os.AsyncTask;

import java.util.ArrayList;

import rocks.athrow.android_popular_movies.interfaces.OnTaskComplete;

/**
 * FetchTask
 * A class to manage making calls to query the API
 * <p/>
 * Created by josel on 8/23/2016.
 */
public class FetchTask extends AsyncTask<String, Void, ArrayList<APIResponse>> {
    public OnTaskComplete mListener = null;

    public FetchTask(OnTaskComplete listener) {
        this.mListener = listener;
    }

    @Override
    protected ArrayList<APIResponse> doInBackground(String... String) {
        ArrayList<APIResponse> apiResponseArray = new ArrayList<>();
        apiResponseArray.add(API.getMoviesFromAPI());
        return apiResponseArray;
    }

    @Override
    protected void onPostExecute(ArrayList<APIResponse> apiResponseArray) {
        super.onPostExecute(apiResponseArray);
        mListener.OnTaskComplete(apiResponseArray);
    }
}
