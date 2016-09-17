package rocks.athrow.android_popular_movies.interfaces;

import java.util.ArrayList;

import rocks.athrow.android_popular_movies.data.APIResponse;

/**
 * Created by jose on 9/9/16.
 */
public interface OnTaskComplete {
    void OnTaskComplete(ArrayList<APIResponse> apiResponses);
}
