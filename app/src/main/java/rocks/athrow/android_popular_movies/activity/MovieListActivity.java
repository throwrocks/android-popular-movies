package rocks.athrow.android_popular_movies.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.stetho.Stetho;

import rocks.athrow.android_popular_movies.R;
import rocks.athrow.android_popular_movies.adapter.MovieListDummyAdapter;
import rocks.athrow.android_popular_movies.data.FetchTask;
import rocks.athrow.android_popular_movies.data.MovieContract;
import rocks.athrow.android_popular_movies.data.MoviesProvider;
import rocks.athrow.android_popular_movies.dummy.DummyContent;
import rocks.athrow.android_popular_movies.interfaces.OnTaskComplete;
import rocks.athrow.android_popular_movies.util.ItemOffsetDecoration;

/**
 * An activity representing a list of Movies. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MovieDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MovieListActivity extends AppCompatActivity implements OnTaskComplete{

    private Cursor mMovies;
    private MovieListDummyAdapter mAdapter;
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
        FetchTask fetchTask = new FetchTask(getApplicationContext(), onTaskCompleted);
        fetchTask.execute();
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new MovieListDummyAdapter(getApplicationContext(), mTwoPane, mMovies);
        recyclerView.setAdapter(mAdapter);
    }

    public void onTaskComplete() {
        MoviesProvider moviesProvider = new MoviesProvider(getApplicationContext());
        mMovies = moviesProvider.
                query(MovieContract.
                        MovieEntry.CONTENT_URI, null, null ,null, null);
        View recyclerView = findViewById(R.id.movie_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        ((RecyclerView) recyclerView).setLayoutManager(new GridLayoutManager(this, 2));
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen.item_offset);
        ((RecyclerView) recyclerView).addItemDecoration(itemDecoration);
        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        setupRecyclerView((RecyclerView) recyclerView);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnTaskComplete() {
        onTaskComplete();
    }
}
