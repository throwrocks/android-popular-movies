package rocks.athrow.android_popular_movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import rocks.athrow.android_popular_movies.R;
import rocks.athrow.android_popular_movies.activity.MovieDetailActivity;
import rocks.athrow.android_popular_movies.fragment.MovieDetailFragment;

/**
 * MovieListAdapter
 * Created by jose on 9/9/16.
 */
public class MovieListAdapter
        extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {
    private static final String mPosterUrl = "http://image.tmdb.org/t/p/w500";
    private Context mContext;
    private Boolean mTwoPane;
    private Cursor mValues;

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
        String posterPath = mPosterUrl + mValues.getString(6);
        Picasso.with(mContext).load(posterPath).into(holder.mPoster);
        holder.mTitle.setText(mValues.getString(8));
        holder.mYear.setText(mValues.getString(5));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    //arguments.putString(MovieDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                    MovieDetailFragment fragment = new MovieDetailFragment();
                    fragment.setArguments(arguments);
                    fragment.getActivity().getSupportFragmentManager().beginTransaction()
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
        public Cursor mItem;

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