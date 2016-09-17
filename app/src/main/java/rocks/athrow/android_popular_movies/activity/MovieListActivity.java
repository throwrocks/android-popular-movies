package rocks.athrow.android_popular_movies.activity;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.stetho.Stetho;

import rocks.athrow.android_popular_movies.R;
import rocks.athrow.android_popular_movies.adapter.MovieListAdapter;
import rocks.athrow.android_popular_movies.data.FetchTask;
import rocks.athrow.android_popular_movies.data.MovieContract;
import rocks.athrow.android_popular_movies.data.MoviesProvider;
import rocks.athrow.android_popular_movies.interfaces.OnTaskComplete;
import rocks.athrow.android_popular_movies.service.UpdateDBService;
import rocks.athrow.android_popular_movies.util.ItemOffsetDecoration;

public class MovieListActivity extends AppCompatActivity implements OnTaskComplete {
    public static final String INTENT_EXTRA = "movies";
    public static final String UPDATE_DB_BROADCAST = "rocks.athrow.android_popular_movies.UpdateDB_Broadcast";
    private Cursor mMovies;
    private MovieListAdapter mAdapter;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Stetho.initializeWithDefaults(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        OnTaskComplete onTaskCompleted = this;
        FetchTask fetchTask = new FetchTask(onTaskCompleted);
        fetchTask.execute();
    }

    /**
     * This method handles getting the ContentValues from the FetchTask
     * And passing them to the UpdateDBService
     *
     * @param moviesContentValues the movies returned from the API
     */
    public void onTaskComplete(ContentValues[] moviesContentValues) {
        if (moviesContentValues != null) {
            Intent updateDBIntent = new Intent(this, UpdateDBService.class);
            updateDBIntent.putExtra(INTENT_EXTRA, moviesContentValues);
            LocalBroadcastManager.getInstance(this).registerReceiver(
                    new ResponseReceiver(),
                    new IntentFilter(UPDATE_DB_BROADCAST));
            this.startService(updateDBIntent);
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
            MoviesProvider moviesProvider = new MoviesProvider(getApplicationContext());
            mMovies = moviesProvider.
                    query(MovieContract.
                            MovieEntry.CONTENT_URI, null, null, null, null);
            View recyclerView = findViewById(R.id.movie_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);
            int numberOfColumns;
            if (mTwoPane) {
                numberOfColumns = 1;
            } else {
                numberOfColumns = 2;
            }
            ((RecyclerView) recyclerView).setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen.item_offset);
            ((RecyclerView) recyclerView).addItemDecoration(itemDecoration);
            setupRecyclerView((RecyclerView) recyclerView);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * setupRecyclerView
     * This method handles setting the adapter to the RecyclerView
     *
     * @param recyclerView the movie's list RecyclerView
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new MovieListAdapter(this, mTwoPane, mMovies);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getResources().getConfiguration().orientation == 2) {
            mTwoPane = true;
        }
    }

    @Override
    public void OnTaskComplete(ContentValues[] moviesContentValues) {
        onTaskComplete(moviesContentValues);
    }
}
