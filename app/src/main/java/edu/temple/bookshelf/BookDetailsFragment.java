package edu.temple.bookshelf;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailsFragment extends Fragment {

    private Book mBook;


    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        if (book==null)
            fragment.setArguments(null);
        else
        {
            Bundle args = new Bundle();
            args.putParcelable("BOOK",book);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("BOOK",mBook);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBook = getArguments().getParcelable("BOOK");
        }
        else
            mBook = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null)
            mBook = savedInstanceState.getParcelable("BOOK");
        View v= inflater.inflate(R.layout.fragment_book_details, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mBook!=null)
        displayBook(mBook);
    }

    public void displayBook(Book book)
    {
        TextView authorDisplay = Objects.requireNonNull(getView()).findViewById(R.id.author_display);
        TextView titleDisplay = getView().findViewById(R.id.title_display);
        ImageView coverDisplay = getView().findViewById(R.id.cover_display);
        Picasso.get().load(book.coverURL).into(coverDisplay);
        titleDisplay.setText(book.title);
        authorDisplay.setText(book.author);
    }
}
