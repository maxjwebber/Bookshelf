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



public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private final ArrayList<Book> mValues;
    private final OnBookSelectedListener mListener;

    public BookListAdapter(ArrayList<Book> items, OnBookSelectedListener listener) {
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
        holder.mBookView.setText(holder.mItem.title);
        holder.mAuthorView.setText(holder.mItem.author);

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
        public Book mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mBookView = view.findViewById(R.id.book_title);
            mAuthorView = view.findViewById(R.id.author_name);
        }

        @Override
        public String toString() {
            return mBookView.getText() + " by "+mAuthorView.getText();
        }
    }
}
