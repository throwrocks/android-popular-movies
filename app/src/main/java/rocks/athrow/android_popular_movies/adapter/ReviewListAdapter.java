package rocks.athrow.android_popular_movies.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rocks.athrow.android_popular_movies.R;
import rocks.athrow.android_popular_movies.data.MovieContract;

/**
 * Created by jose on 1/11/17.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {
    private final Cursor mValues;

    public ReviewListAdapter(Cursor mValues) {
        this.mValues = mValues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewListAdapter.ViewHolder holder, int position) {
        mValues.moveToPosition(position);
        final String author = mValues.getString(MovieContract.ReviewsEntry.review_author_index);
        final String content = mValues.getString(MovieContract.ReviewsEntry.review_content_index);
        holder.mAuthor.setText(author);
        holder.mReview.setText(content);
    }

    @Override
    public int getItemCount() {
        return mValues.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mAuthor;
        final TextView mReview;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mAuthor = (TextView) view.findViewById(R.id.review_item_author);
            mReview = (TextView) view.findViewById(R.id.review_item_review);

        }
    }
}
