package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnBookSelectedListener{


    public void onBookSelected(HashMap book)
    {
        //getSupportFragmentManager()
        //                .beginTransaction()
        //                //.add() add something
        //                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList books = BookShelfBuilder.generateBookshelf(this);
        BookListFragment bookListFragment = BookListFragment.newInstance(books);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container1, bookListFragment)
                .commit();

    }
}
