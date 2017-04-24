package android.example.com.codechallenge003.adapters;

import android.content.Context;
import android.example.com.codechallenge003.R;
import android.example.com.codechallenge003.Utils.Utils;
import android.example.com.codechallenge003.model.TvShowInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ShowListAdapter extends ArrayAdapter<TvShowInfo> {

    private List<TvShowInfo> mList;

    public ShowListAdapter(@NonNull Context context, List<TvShowInfo> objects) {
        super(context, 0, objects);
        mList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.show_item_list, parent, false);
        }
        TvShowInfo item = getItem(position);

        ((TextView) convertView.findViewById(R.id.show_name))
                .setText(item.getName());
        ((TextView) convertView.findViewById(R.id.show_popularity))
                .setText(Utils.DoubleToString(item.getPopularity()));

        ImageView mThumbnail = (ImageView) convertView.findViewById(R.id.show_thumbnail);

        Glide.with(getContext())
                .load(Utils.buildThumbnailPath(item.getPosterPath()))
                .into(mThumbnail);

        return convertView;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }
}
