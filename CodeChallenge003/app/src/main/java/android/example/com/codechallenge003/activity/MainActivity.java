package android.example.com.codechallenge003.activity;

import android.content.Intent;
import android.example.com.codechallenge003.R;
import android.example.com.codechallenge003.Utils.Utils;
import android.example.com.codechallenge003.adapters.ShowListAdapter;
import android.example.com.codechallenge003.model.TvShowInfo;
import android.example.com.codechallenge003.network.Client;
import android.example.com.codechallenge003.model.PopularTvShows;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    private ShowListAdapter mShowListAdapter;
    private ListView mMovieListView;
    private ProgressBar mProgressBar;
    private ApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShowListAdapter = new ShowListAdapter(this, null);
        mMovieListView = (ListView) findViewById(R.id.show_list);
        mMovieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplication(), DetailActivity.class);
                TvShowInfo item = mShowListAdapter.getItem(i);
                intent.putExtra("SHOW_ID", item.getId());
                intent.putExtra("SHOW_NAME", item.getName());
                intent.putExtra("SHOW_POPULARITY", Utils.DoubleToString(item.getPopularity()));
                intent.putExtra("SHOW_POSTER_PATH", item.getPosterPath());
                startActivity(intent);
            }
        });
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        requestData();
    }

    private void requestData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mMovieListView.setVisibility(View.GONE);
        mClient = new ApiClient();
        mClient.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClient != null) {
            mClient.cancel(true);
        }
    }

    private class ApiClient extends AsyncTask<Void, Void, PopularTvShows> {
        @Override
        protected PopularTvShows doInBackground(Void... voids) {
            try {
                return new Client().build().getPopularTvShows().execute().body();
            } catch (Exception e) {
                Log.i(TAG, "doInBackground: Exception" + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(PopularTvShows popularTvShows) {
            super.onPostExecute(popularTvShows);
            updateAdapter(popularTvShows.getShows());
        }
    }

    private void updateAdapter(List<TvShowInfo> shows) {
        if (shows != null) {
            mShowListAdapter = new ShowListAdapter(this, shows);
            mMovieListView.setAdapter(mShowListAdapter);
        } else {
            Toast.makeText(getApplicationContext(), R.string.fetching_error, Toast.LENGTH_SHORT).show();
        }
        mMovieListView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }


}
