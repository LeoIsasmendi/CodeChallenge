package android.example.com.codechallenge;

import android.content.Intent;
import android.example.com.codechallenge.client.Client;
import android.example.com.codechallenge.client.Pet;
import android.example.com.codechallenge.data.MyListViewAdapter;
import android.example.com.codechallenge.ui.DetailsActivity;
import android.media.tv.TvContract;
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

    private final String TAG = getClass().getSimpleName();
    private ListView mListView;
    private MyListViewAdapter mAdapter;
    private EndPointClient mClient;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mListView = (ListView) findViewById(R.id.main_list);
        mAdapter = new MyListViewAdapter(this, null);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplication(), DetailsActivity.class);
                Pet pet = mAdapter.getItem(i);
                intent.putExtra("PET_ID", pet.getId());
                intent.putExtra("PET_NAME", pet.getName());
                intent.putExtra("PET_CATEGORY", pet.getCategory().getName());
                //warning only the first photo
                intent.putExtra("PHOTO_URL", !pet.getPhotoUrls().isEmpty() ? pet.getPhotoUrls().get(0) : "");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClient.cancel(true);
    }

    private void fetchData() {
        mListView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mClient = new EndPointClient();
        mClient.execute();
    }


    private class EndPointClient extends AsyncTask<Void, Void, List<Pet>> {
        @Override
        protected List<Pet> doInBackground(Void... voids) {
            try {
                return new Client().build().getPets().execute().body();
            } catch (Exception e) {
                Log.i(TAG, "doInBackground: Exception" + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Pet> pets) {
            super.onPostExecute(pets);
            updateAdapter(pets);
        }
    }

    private void updateAdapter(List<Pet> pets) {
        if (pets != null) {
            mAdapter = new MyListViewAdapter(this, pets);
            mListView.setAdapter(mAdapter);
        } else {
            Toast.makeText(getBaseContext(), R.string.fetching_error, Toast.LENGTH_SHORT).show();
        }
        mListView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }
}
