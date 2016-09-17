package rocks.athrow.android_popular_movies.fragment;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import rocks.athrow.android_popular_movies.activity.MovieDetailActivity;
import rocks.athrow.android_popular_movies.activity.MovieListActivity;
import rocks.athrow.android_popular_movies.R;

/**
 * A fragment representing a single Movie detail screen.
 * This fragment is either contained in a {@link MovieListActivity}
 * in two-pane mode (on tablets) or a {@link MovieDetailActivity}
 * on handsets.
 */
public class MovieDetailFragment extends Fragment {

    public static final String ARG_ID = "id";
    public static final String ARG_TITLE = "title";
    public static final String ARG_RELEASE_YEAR = "release_year";
    public static final String ARG_OVERVIEW = "overview";
    public static final String ARG_VOTE_COUNT = "vote_count";
    public static final String ARG_POSTER_PATH = "poster_path";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);
        Bundle arguments = getArguments();
        String title = arguments.getString(ARG_TITLE);
        String releaseYear = arguments.getString(ARG_RELEASE_YEAR);
        String overview = arguments.getString(ARG_OVERVIEW);
        String voteCount = arguments.getString(ARG_VOTE_COUNT);

        ((TextView) rootView.findViewById(R.id.detail_title)).setText(title);
        ((TextView) rootView.findViewById(R.id.detail_release_year)).setText(releaseYear);
        ((TextView) rootView.findViewById(R.id.detail_overview)).setText(overview);
        RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.detail_rating_bar);
        ratingBar.setRating(Float.parseFloat(voteCount));
        //}
        return rootView;
    }
}
