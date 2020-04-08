package edu.temple.bookshelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements BookListFragment.OnBookSelectedListener {

    private BookListFragment bookListFragment;
    private BookDetailsFragment bookDetailsFragment;
    private static final String ACTIVITY_TAG = "MainActivity";
    private static final String FRAGMENT_LIST_TAG = "List";
    private static final String FRAGMENT_DETAILS_TAG = "Details";
    ArrayList<Book> books;
    Book activeBook;
    FragmentManager fm = getSupportFragmentManager();
    EditText editText;
    RequestQueue rq;

    @Override
    public void onBackPressed() {
        if(findViewById(R.id.container2)!=null&&fm.getBackStackEntryCount() > 0)
                fm.popBackStack();
        else
            super.onBackPressed();
    }

    public void onBookSelected(Book book) {
        activeBook = book;
        if (findViewById(R.id.container2)!=null)
        {
            if(bookDetailsFragment!=null)
            bookDetailsFragment.displayBook(book);
            else
                bookDetailsFragment = BookDetailsFragment.newInstance(book);
            fm.beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment,FRAGMENT_DETAILS_TAG).addToBackStack(null).commit();
        }
        else
        {
            bookDetailsFragment = BookDetailsFragment.newInstance(book);
            fm.beginTransaction()
                    .replace(R.id.container1, bookDetailsFragment,FRAGMENT_DETAILS_TAG).addToBackStack(null).commit();

        }

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        rq = Volley.newRequestQueue(MainActivity.this);
        Button button = findViewById(R.id.button);

        if (savedInstanceState == null && findViewById(R.id.container2)!=null)
        {
            bookDetailsFragment = BookDetailsFragment.newInstance(null);
            fm.beginTransaction().replace(R.id.container2, bookDetailsFragment, FRAGMENT_DETAILS_TAG).addToBackStack(null).commit();
        }




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String searchURL = "https://kamorris.com/lab/abp/booksearch.php?search=" + editText.getText().toString();
                books = new ArrayList<>();
                Thread thread = new Thread()
                {

                    @Override
                    public void run()
                    {
                        JsonArrayRequest objectRequest = new JsonArrayRequest(Request.Method.GET,searchURL, null,
                                new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray array) {
                                        try {
                                            JSONObject item;
                                            for (int i = 0; i < array.length(); i++) {
                                                item = array.getJSONObject(i);
                                                books.add(new Book(item.getInt("book_id"), item.getString("title"), item.getString("author"), item.getString("cover_url")));
                                            }
                                            bookListFragment = BookListFragment.newInstance(books);
                                            fm.beginTransaction().replace(R.id.container1, bookListFragment,FRAGMENT_LIST_TAG).addToBackStack(null).commit();
                                        } catch (JSONException e) {
                                            Log.e(ACTIVITY_TAG, "Exception: "+Log.getStackTraceString(e));
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error)
                                    {
                                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                        rq.add(objectRequest);
                    }
                };
                thread.start();
            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        if (fm.findFragmentById(R.id.container2) instanceof BookDetailsFragment)
            fm.putFragment(savedInstanceState,FRAGMENT_DETAILS_TAG,fm.findFragmentById(R.id.container2));
        if (fm.findFragmentById(R.id.container1) instanceof BookDetailsFragment)
            fm.putFragment(savedInstanceState,FRAGMENT_DETAILS_TAG,fm.findFragmentById(R.id.container1));
        if (fm.findFragmentById(R.id.container1) instanceof BookListFragment)
            fm.putFragment(savedInstanceState,FRAGMENT_LIST_TAG,fm.findFragmentById(R.id.container1));

    }

}