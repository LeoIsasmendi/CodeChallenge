package android.example.com.codechallenge;

import android.example.com.codechallenge.client.Contact;
import android.example.com.codechallenge.ui.DetailFragment;
import android.example.com.codechallenge.ui.MainFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity implements MainFragment.OnListFragmentInteractionListener {

    private FragmentManager fragmentManager;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    private void initFragments() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .replace(R.id.fragment_container, new MainFragment(), MainFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onListFragmentInteraction(Contact item) {
        showDetails(item);
    }

    private void showDetails(Contact item) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailFragment detailFragment = new DetailFragment();
        fragmentTransaction
                .replace(R.id.fragment_container, detailFragment, MainFragment.class.getSimpleName())
                .addToBackStack(DetailFragment.class.getSimpleName())
                .commit();
        Bundle bundle = new Bundle();
        bundle.putLong("EXTRA_UID", item.getUserId());
        bundle.putString("EXTRA_FIRST_NAME", item.getFirstName());
        bundle.putString("EXTRA_LAST_NAME", item.getLastName());
        bundle.putString("EXTRA_PHOTO", item.getPhoto());
        detailFragment.setArguments(bundle);
    }
}
