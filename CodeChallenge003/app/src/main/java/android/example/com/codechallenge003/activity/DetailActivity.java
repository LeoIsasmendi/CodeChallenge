package android.example.com.codechallenge003.activity;

import android.example.com.codechallenge003.Utils.Utils;
import android.example.com.codechallenge003.adapters.CharacterListAdapter;
import android.example.com.codechallenge003.model.Character;
import android.example.com.codechallenge003.model.TvShowCredits;
import android.example.com.codechallenge003.network.Client;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.example.com.codechallenge003.R;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private ListView mListView;
    private CharacterListAdapter mAdapter;
    private Integer showId;
    private ApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ((TextView) findViewById(R.id.detail_movie_title))
                .setText(getIntent().getStringExtra("SHOW_NAME"));
        ((TextView) findViewById(R.id.detail_popularity))
                .setText(getIntent().getStringExtra("SHOW_POPULARITY"));

        loadPoster(getIntent().getStringExtra("SHOW_POSTER_PATH"));

        showId = getIntent().getIntExtra("SHOW_ID", 0);
        mAdapter = new CharacterListAdapter(this, null);
        mListView = (ListView) findViewById(R.id.cast_list);
        requestData();
    }

    private void loadPoster(String posterPath) {
        ImageView mPoster = (ImageView) findViewById(R.id.detail_poster);
        Glide.with(this)
                .load(Utils.buildPosterPath(posterPath))
                .into(mPoster);
    }

    private void requestData() {
        mClient = new ApiClient();
        mClient.execute(showId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mClient != null) {
            mClient.cancel(true);
        }
    }

    private class ApiClient extends AsyncTask<Integer, Void, TvShowCredits> {
        @Override
        protected TvShowCredits doInBackground(Integer... integers) {
            try {
                Log.i(TAG, "doInBackground: Exception" + integers.length);
                return new Client().build().getTvShowCredits(integers[0]).execute().body();
            } catch (Exception e) {
                Log.i(TAG, "doInBackground: Exception" + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(TvShowCredits credits) {
            super.onPostExecute(credits);
            Log.d(TAG, "onPostExecute: ");
            updateAdapter(credits.getCast());
        }
    }

    private void updateAdapter(List<Character> casts) {
        if (casts != null) {
            mAdapter = new CharacterListAdapter(this, casts);
            mListView.setAdapter(mAdapter);
        } else {
            Toast.makeText(this, R.string.fetching_error, Toast.LENGTH_SHORT).show();
        }
    }
}
