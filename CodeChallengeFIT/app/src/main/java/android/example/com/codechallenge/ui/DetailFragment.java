package android.example.com.codechallenge.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.codechallenge.R;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    private Bundle mBundle;
    private TextView mPetName;
    private TextView mPetCategory;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBundle = getArguments();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mPetName = (TextView) view.findViewById(R.id.pet_name);
        mPetCategory = (TextView) view.findViewById(R.id.pet_category);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView() {
        if (mBundle != null) {
            mPetName.setText(mBundle.getString("PET_NAME"));
            mPetCategory.setText(mBundle.getString("PET_CATEGORY"));
        } else {
            mPetName.setText(getText(R.string.default_detail_text));
            mPetCategory.setText(getText(R.string.default_detail_text));
        }
    }
}
