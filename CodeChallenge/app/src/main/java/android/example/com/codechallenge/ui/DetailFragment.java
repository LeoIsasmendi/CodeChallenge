package android.example.com.codechallenge.ui;


import android.example.com.codechallenge.client.Address;
import android.example.com.codechallenge.client.Client;
import android.example.com.codechallenge.client.Detail;
import android.example.com.codechallenge.client.Phone;
import android.example.com.codechallenge.data.Item;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.codechallenge.R;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class DetailFragment extends Fragment {

    private Item mItem;
    private IguanaClient iguanaClient;

    private final String TAG = getClass().getSimpleName();

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItem = new Item()
                    .setUserId(getArguments().getLong("EXTRA_UID"))
                    .setFirstName(getArguments().getString("EXTRA_FIRST_NAME"))
                    .setLastName(getArguments().getString("EXTRA_LAST_NAME"))
                    .setPhoto(getArguments().getString("EXTRA_PHOTO"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iguanaClient.cancel(true);
    }

    private void fetchData() {
        if (mItem != null) {
            iguanaClient = new IguanaClient();
            iguanaClient.execute(mItem.getUserId());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                fetchData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateView(Detail detail) {
        setFirstName(detail.getFirstName());
        setLastName(detail.getLastName());
        setBirthDate(detail.getBirthDate());
        setPhones(detail.getPhones());
        setAddresses(detail.getAddresses());
        updatePhoto(detail.getPhoto());
    }

    private void setFirstName(String string) {
        setText(R.id.detail_item_first_name, string);
    }

    private void setLastName(String string) {
        setText(R.id.detail_item_last_name, string);
    }

    private void setBirthDate(String string) {
        setText(R.id.detail_item_birth_date, string);
    }

    private void setText(int resource, String string) {
        TextView textView = ((TextView) getView().findViewById(resource));
        if (string != null && !string.isEmpty()) {
            textView.setText(string);
        } else {
            textView.setText(R.string.default_text);
        }
    }

    private void setPhones(List<Phone> list) {
        // Ugly but works
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getType()) {
                case "Home":
                    setText(R.id.detail_item_phone_home, list.get(0).getNumber());
                    break;
                case "Cellphone":
                    setText(R.id.detail_item_cellphone, list.get(0).getNumber());
                    break;
                case "Office":
                    setText(R.id.detail_item_phone_office, list.get(0).getNumber());
                    break;
            }
        }
    }

    private void setAddresses(List<Address> list) {
        //Only the home address
        String address = "";
        if (list.size() != 0) {
            address = list.get(0).getHome();
        }
        setText(R.id.detail_item_address, address);
    }

    private void updatePhoto(String photoSrc) {
        ImageView photo = (ImageView) getView().findViewById(R.id.detail_item_photo);
        Glide.with(getActivity())
                .load(photoSrc)
                .fitCenter()
                .error(R.drawable.ic_default_photo)
                .into(photo);
    }


    private class IguanaClient extends AsyncTask<Long, Void, Detail> {
        @Override
        protected Detail doInBackground(Long... longs) {
            try {
                return new Client().build().fetchDetails(longs[0]).execute().body();
            } catch (Exception e) {
                Log.i(TAG, "doInBackground: Exception" + e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Detail detail) {
            super.onPostExecute(detail);
            updateView(detail);
            Log.d(TAG, "onPostExecute: ");
        }
    }
}
