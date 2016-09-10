package rocks.athrow.android_popular_movies.data;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import rocks.athrow.android_popular_movies.interfaces.OnTaskComplete;

/**
 * DataHandler
 * A class to manage making calls to query the API, parsing the JSON, and storing the
 * <p/>
 * Created by josel on 8/23/2016.
 */
public class FetchTask extends AsyncTask {
    public OnTaskComplete mListener = null;
    private Context mContext;

    public FetchTask(Context mContext, OnTaskComplete listener) {
        this.mContext = mContext;
        this.mListener = listener;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        APIResponse apiResponse = API.getMoviesFromAPI();
        String moviesResult = apiResponse.getResponseText();
        if (moviesResult != null) {
            ContentValues[] moviesContentValues = JSONParser.getMoviesFromJSON(moviesResult);
            MoviesProvider moviesProvider = new MoviesProvider(mContext);
            moviesProvider.bulkInsert(MovieContract.MovieEntry.CONTENT_URI, moviesContentValues);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        mListener.OnTaskComplete();
    }
}
