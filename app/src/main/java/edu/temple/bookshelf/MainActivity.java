package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnBookSelectedListener{

    ArrayList books;
    FragmentManager fm = getSupportFragmentManager();
    @Override
    public void onBackPressed() {

        if (fm.findFragmentById(R.id.container1) instanceof BookDetailsFragment)
        {
            fm.beginTransaction()
                .replace(R.id.container1,BookListFragment.newInstance(books))
                .commit();
        }
        else super.onBackPressed();
    }

    public void onBookSelected(HashMap book)
    {
        if (fm.findFragmentById(R.id.container2) instanceof BookDetailsFragment)
        {
            ((BookDetailsFragment) fm.findFragmentById(R.id.container2)).displayBook(book);
        }
        else
        fm.beginTransaction()
                        .replace(R.id.container1,BookDetailsFragment.newInstance(book))
                        .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BookShelfBuilder.generateBookshelf(this);
        books = BookShelfBuilder.generateBookshelf(this);
        BookListFragment bookListFragment = BookListFragment.newInstance(books);
        if (findViewById(R.id.container2)==null)
        fm
                .beginTransaction()
                .add(R.id.container1, bookListFragment)
                .commit();
        else
            fm
                    .beginTransaction()
                    .add(R.id.container1, bookListFragment)
                    .add(R.id.container2, BookDetailsFragment.newInstance(null))
                    .commit();
    }
}
