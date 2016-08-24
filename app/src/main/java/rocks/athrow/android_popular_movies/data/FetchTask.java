package rocks.athrow.android_popular_movies.data;

import android.content.Context;
import android.os.AsyncTask;

/** DataHandler
 * A class to manage making calls to query the API, parsing the JSON, and storing the
 *
 * Created by josel on 8/23/2016.
 */
public class FetchTask extends AsyncTask {

    private Context mContext;

    /**
     * FetchTask Constructor
     * @param context need context to pass to handler methods
     */
    public void FetchTask (Context context){
        this.mContext = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
