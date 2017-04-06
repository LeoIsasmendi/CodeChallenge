package android.example.com.codechallenge.ui;

import android.content.Context;
import android.example.com.codechallenge.client.Client;
import android.example.com.codechallenge.client.Contact;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.example.com.codechallenge.R;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

public class MainFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();
    private OnListFragmentInteractionListener mListener;
    private MyItemRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private IguanaClient iguanaClient;
    private ProgressBar mProgressBar;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mProgressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            mRecyclerView = (RecyclerView) view;
            mAdapter = new MyItemRecyclerViewAdapter(null, getActivity(), mListener);
            mRecyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        Log.d(TAG, "loadData: ");
        mProgressBar.setVisibility(View.VISIBLE);
        iguanaClient = new IguanaClient();
        iguanaClient.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                loadData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iguanaClient.cancel(true);
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Contact item);
    }

    private class IguanaClient extends AsyncTask<Void, Void, List<Contact>> {
        @Override
        protected List<Contact> doInBackground(Void... voids) {
            try {
                return new Client().build().fetchContacts().execute().body();
            } catch (Exception e) {
                Log.i(TAG, "doInBackground: Exception" + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Contact> contacts) {
            super.onPostExecute(contacts);
            Log.d(TAG, "onPostExecute: ");
            updateAdapter(contacts);
        }
    }

    private void updateAdapter(List<Contact> contacts) {
        if (contacts != null) {
            mAdapter = new MyItemRecyclerViewAdapter(contacts, getActivity(), mListener);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Toast.makeText(getActivity(), R.string.fetching_error, Toast.LENGTH_SHORT).show();
        }
        mProgressBar.setVisibility(View.GONE);
    }
}
