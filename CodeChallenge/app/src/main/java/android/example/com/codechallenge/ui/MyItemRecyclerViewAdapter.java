package android.example.com.codechallenge.ui;

import android.content.Context;
import android.example.com.codechallenge.R;
import android.example.com.codechallenge.client.Contact;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.example.com.codechallenge.ui.MainFragment.OnListFragmentInteractionListener;

import com.bumptech.glide.Glide;

import java.util.List;

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Contact> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public MyItemRecyclerViewAdapter(List<Contact> items, Context context, OnListFragmentInteractionListener listener) {
        mContext = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mFirstNameView.setText(mValues.get(position).getFirstName());
        holder.mLastNameView.setText(mValues.get(position).getLastName());

        Glide.with(mContext)
                .load(holder.mItem.getThumb())
                .error(R.drawable.ic_default_user)
                .into(holder.mThumb);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues != null ? mValues.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mFirstNameView;
        public final TextView mLastNameView;
        public ImageView mThumb;
        public Contact mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mFirstNameView = (TextView) view.findViewById(R.id.item_first_name);
            mLastNameView = (TextView) view.findViewById(R.id.item_last_name);
            mThumb = (ImageView) view.findViewById(R.id.item_thumb);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLastNameView.getText() + "'";
        }
    }
}