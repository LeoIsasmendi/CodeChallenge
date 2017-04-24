package android.example.com.codechallenge003.adapters;


import android.content.Context;
import android.example.com.codechallenge003.R;
import android.example.com.codechallenge003.Utils.Utils;
import android.example.com.codechallenge003.model.Character;
import android.net.Uri;
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

public class CharacterListAdapter extends ArrayAdapter<Character> {

    private List<Character> mList;

    public CharacterListAdapter(@NonNull Context context, List<Character> objects) {
        super(context, 0, objects);
        mList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cast_item_list, parent, false);
        }
        Character item = getItem(position);

        ((TextView) convertView.findViewById(R.id.cast_character_name))
                .setText(item.getCharacter());

        ((TextView) convertView.findViewById(R.id.cast_real_name))
                .setText(item.getName());

        ImageView mThumbnail = (ImageView) convertView.findViewById(R.id.cast_thumbnail);
        Glide.with(getContext())
                .load(Utils.buildThumbnailPath(item.getProfilePath()))
                .into(mThumbnail);

        return convertView;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }
}
