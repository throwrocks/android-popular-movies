package rocks.athrow.android_popular_movies.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import rocks.athrow.android_popular_movies.activity.MovieDetailActivity;
import rocks.athrow.android_popular_movies.activity.MovieListActivity;
import rocks.athrow.android_popular_movies.R;
import rocks.athrow.android_popular_movies.data.APIResponse;
import rocks.athrow.android_popular_movies.data.FetchTask;
import rocks.athrow.android_popular_movies.data.MovieContract;
import rocks.athrow.android_popular_movies.interfaces.OnTaskComplete;
import rocks.athrow.android_popular_movies.service.UpdateDBService;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment implements OnTaskComplete {
    private String mMovieId;

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
        mMovieId = arguments.getString(MovieContract.MovieEntry.movie_id);
        OnTaskComplete onTaskCompleted = this;
        FetchTask fetchTask = new FetchTask(onTaskCompleted);
        fetchTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);
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

    }
    @Override
    public void OnTaskComplete(APIResponse apiResponse) {
        onTaskComplete(apiResponse);
    }
}
