package rocks.athrow.android_popular_movies.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import rocks.athrow.android_popular_movies.activity.MovieDetailActivity;
import rocks.athrow.android_popular_movies.activity.MovieListActivity;
import rocks.athrow.android_popular_movies.R;
import rocks.athrow.android_popular_movies.adapter.ReviewListAdapter;
import rocks.athrow.android_popular_movies.data.APIResponse;
import rocks.athrow.android_popular_movies.data.FetchTask;
import rocks.athrow.android_popular_movies.data.MovieContract;
import rocks.athrow.android_popular_movies.data.MoviesProvider;
import rocks.athrow.android_popular_movies.interfaces.OnTaskComplete;
import rocks.athrow.android_popular_movies.service.UpdateDBService;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment implements OnTaskComplete {
    public static final String INTENT_TYPE = "type";
    public static final String INTENT_EXTRA = "JSON";
    public static final String INTENT_TYPE_REVIEWS = "reviews";
    private final String GET_REVIEWS = "getReviews";
    private ReviewListAdapter mAdapter;
    private Cursor mReviews;
    private String mMovieId;
    private View rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mMovieId = Integer.toString(arguments.getInt(MovieContract.MovieEntry.movie_id));
        OnTaskComplete onTaskCompleted = this;
        // TODO: Query the database for reviews, because if they are in the database we can display while we fetch any new ones
        FetchTask fetchTask = new FetchTask(GET_REVIEWS, mMovieId, onTaskCompleted);
        fetchTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.movie_detail, container, false);
        Bundle arguments = getArguments();
        String title = arguments.getString(MovieContract.MovieEntry.movie_title);
        String releaseYear = arguments.getString(MovieContract.MovieEntry.movie_release_year);
        String overview = arguments.getString(MovieContract.MovieEntry.movie_overview);
        String voteCount = arguments.getString(MovieContract.MovieEntry.movie_vote_count);
        ((TextView) rootView.findViewById(R.id.detail_title)).setText(title);
        ((TextView) rootView.findViewById(R.id.detail_release_year)).setText(releaseYear);
        ((TextView) rootView.findViewById(R.id.detail_overview)).setText(overview);
        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.detail_rating_bar);
        ratingBar.setRating(Float.parseFloat(voteCount));
        return rootView;
    }

    public void onTaskComplete(APIResponse apiResponse) {
        if (apiResponse != null) {
            Intent updateDBIntent = new Intent(getActivity().getApplicationContext(), UpdateDBService.class);
            switch (apiResponse.getResponseCode()) {
                case 200:
                    updateDBIntent.putExtra(INTENT_TYPE, INTENT_TYPE_REVIEWS);
                    updateDBIntent.putExtra("movie_id", Integer.parseInt(mMovieId));
                    updateDBIntent.putExtra(INTENT_EXTRA, apiResponse.getResponseText());
                    LocalBroadcastManager.getInstance(getActivity()).
                            registerReceiver(new MovieDetailFragment.ResponseReceiver(),
                                    new IntentFilter(UpdateDBService.UPDATE_REVIEWS_DB_SERVICE_BROADCAST));
                    getActivity().startService(updateDBIntent);
                    break;
                default:
                    Context context = getActivity().getApplicationContext();
                    CharSequence text = apiResponse.getResponseText();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
            }
        }
    }
    /**
     * ResponseReceiver
     * This class is used to manage the response from the UpdateDB Service
     */
    private class ResponseReceiver extends BroadcastReceiver {

        private ResponseReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MoviesProvider moviesProvider = new MoviesProvider(context);
            mReviews =
                    moviesProvider.query(
                            MovieContract.ReviewsEntry.CONTENT_URI,
                            null,
                            MovieContract.ReviewsEntry.review_movie_id + "=?",
                            new String[]{mMovieId},
                            null
                    );
            TextView reviewsHeader = (TextView) rootView.findViewById(R.id.detail_reviews_header);
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.detail_reviews_list);
            if ( mReviews != null && mReviews.getCount() == 0){
                reviewsHeader.setVisibility(View.GONE);
                assert recyclerView != null;
                recyclerView.setVisibility(View.GONE);
            }else{
                reviewsHeader.setVisibility(View.VISIBLE);
                assert recyclerView != null;
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                mAdapter = new ReviewListAdapter(mReviews);
                recyclerView.setAdapter(mAdapter);
            }
        }
    }


    @Override
    public void OnTaskComplete(APIResponse apiResponse) {
        onTaskComplete(apiResponse);
    }
}
