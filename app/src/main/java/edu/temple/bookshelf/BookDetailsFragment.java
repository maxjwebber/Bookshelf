package edu.temple.bookshelf;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailsFragment extends Fragment {

    private HashMap<String,String> mBook;


    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(HashMap<String,String> book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        if (book==null)
            fragment.setArguments(null);
        else
        {
            Bundle args = new Bundle();
            args.putSerializable("HASHMAP",book);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBook = (HashMap<String,String>) getArguments().getSerializable("HASHMAP");
        }
        else
            mBook = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_book_details, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mBook!=null)
        displayBook(mBook);
    }

    public void displayBook(HashMap<String,String> book)
    {
        TextView authorDisplay = (TextView) getView().findViewById(R.id.author_display);
        TextView titleDisplay = (TextView) getView().findViewById(R.id.title_display);
        for (Map.Entry<String, String> entry : book.entrySet())
        {
            titleDisplay.setText(entry.getKey());
            authorDisplay.setText(entry.getValue());
        }
    }
}
