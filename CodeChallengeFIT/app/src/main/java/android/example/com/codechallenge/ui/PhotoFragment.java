package android.example.com.codechallenge.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.codechallenge.R;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PhotoFragment extends Fragment {

    private static final String ARG_PARAM1 = "PHOTO_URL";

    private String mParam1;
    private ImageView mImage;


    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        mImage = (ImageView) view.findViewById(R.id.photo);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPhoto();
    }

    private void loadPhoto() {
        Glide.with(getActivity())
                .load(mParam1)
                .error(R.drawable.ic_default_picture)
                .into(mImage);
    }
}
