package rocks.athrow.android_popular_movies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;

import rocks.athrow.android_popular_movies.R;
import rocks.athrow.android_popular_movies.activity.MovieDetailActivity;
import rocks.athrow.android_popular_movies.data.MovieContract;
import rocks.athrow.android_popular_movies.fragment.MovieDetailFragment;
import rocks.athrow.android_popular_movies.util.Utilities;

/**
 * MovieListAdapter
 * Created by jose on 9/9/16.
 */
public class MovieListAdapter
        extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private static final String DATE_FORMAT_API = "yyyy-MM-dd";
    private static final String DATE_FORMAT_DISPLAY = "yyyy";
    private static final String POSTER_URL = "http://image.tmdb.org/t/p/w500";
    private Context mContext;
    private Boolean mTwoPane;
    private Cursor mValues;

    /**
     * MovieListAdapter
     * @param context the application context
     * @param twoPane equals true when the layout is in two pane mode
     * @param items a Cursor object containing the movies
     */
    public MovieListAdapter(Context context, Boolean twoPane, Cursor items) {
        mValues = items;
        mTwoPane = twoPane;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mValues.moveToPosition(position);
        // Get the movie variables
        final int id = mValues.getInt(MovieContract.MovieEntry.movie_id_index);
        String posterPath = POSTER_URL + mValues.getString(MovieContract.MovieEntry.movie_poster_path_index);
        final String title = mValues.getString(MovieContract.MovieEntry.movie_title_index);
        String releaseDateString = mValues.getString(MovieContract.MovieEntry.movie_poster_release_date_index);
        Date releaseDate = Utilities.getStringAsDate(releaseDateString, DATE_FORMAT_API, null );
        final String releaseYear = Utilities.getDateAsString(releaseDate, DATE_FORMAT_DISPLAY, null);
        final String overview = mValues.getString(MovieContract.MovieEntry.movie_overview_index);
        final String voteCount = mValues.getString(MovieContract.MovieEntry.movie_vote_count_index);
        // Set the movie views
        Picasso.with(mContext).load(posterPath).into(holder.mPoster);
        holder.mTitle.setText(title);
        holder.mYear.setText(releaseYear);
        // Set the click listener
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putInt(MovieDetailFragment.ARG_ID, id);
                    arguments.putString(MovieDetailFragment.ARG_TITLE, title);
                    arguments.putString(MovieDetailFragment.ARG_RELEASE_YEAR, releaseYear);
                    arguments.putString(MovieDetailFragment.ARG_OVERVIEW,overview);
                    arguments.putString(MovieDetailFragment.ARG_VOTE_COUNT, voteCount);
                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);
                    ((Activity)mContext).getFragmentManager().beginTransaction()
                            .replace(R.id.movie_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    //intent.putExtra(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mPoster;
        public final TextView mTitle;
        public final TextView mYear;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPoster = (ImageView) view.findViewById(R.id.movie_poster);
            mTitle = (TextView) view.findViewById(R.id.movie_title);
            mYear = (TextView) view.findViewById(R.id.movie_year);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }
}