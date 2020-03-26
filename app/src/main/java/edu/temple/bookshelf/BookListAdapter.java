package edu.temple.bookshelf;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.temple.bookshelf.BookListFragment.OnBookSelectedListener;


import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ArrayList< HashMap >} and makes a call to the
 * specified {@link OnBookSelectedListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private final ArrayList<HashMap> mValues;
    private final OnBookSelectedListener mListener;

    public BookListAdapter(ArrayList<HashMap> items, OnBookSelectedListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_book_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String title ="";
        String author="";
        for (Map.Entry<String, String> entry : holder.mItem.entrySet()) {
            title = entry.getKey();
            author = entry.getValue();
        }
        holder.mBookView.setText(title);
        holder.mAuthorView.setText(author);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onBookSelected(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mBookView;
        public final TextView mAuthorView;
        public HashMap<String,String> mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mBookView = (TextView) view.findViewById(R.id.book_title);
            mAuthorView = (TextView) view.findViewById(R.id.author_name);
        }

        @Override
        public String toString() {
            return mBookView.getText() + " by "+mAuthorView.getText();
        }
    }
}
