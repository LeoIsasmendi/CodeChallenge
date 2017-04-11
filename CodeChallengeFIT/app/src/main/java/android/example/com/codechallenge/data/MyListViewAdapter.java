package android.example.com.codechallenge.data;

import android.content.Context;
import android.example.com.codechallenge.R;
import android.example.com.codechallenge.client.Pet;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyListViewAdapter extends ArrayAdapter<Pet> {

    private List<Pet> mValues;
    private Context mContext;

    public MyListViewAdapter(Context context, List<Pet> items) {
        super(context, 0, items);
        mContext = context;
        mValues = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }
        TextView mTextView = (TextView) convertView.findViewById(R.id.item_view);
        mTextView.setText(getItem(position).getName());
        return convertView;
    }

    @Override
    public int getCount() {
        return mValues != null ? mValues.size() : 0;
    }
}
