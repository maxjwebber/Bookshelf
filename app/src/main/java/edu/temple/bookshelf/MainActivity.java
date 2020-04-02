package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements BookListFragment.OnBookSelectedListener {

    ArrayList<Book> books;
    FragmentManager fm = getSupportFragmentManager();
    EditText editText;
    RequestQueue rq;

    @Override
    public void onBackPressed() {

        if (fm.findFragmentById(R.id.container1) instanceof BookDetailsFragment) {
            fm.beginTransaction()
                    .replace(R.id.container1, BookListFragment.newInstance(books))
                    .commit();
        } else super.onBackPressed();
    }

    public void onBookSelected(Book book) {
        if (fm.findFragmentById(R.id.container2) instanceof BookDetailsFragment) {
            ((BookDetailsFragment) fm.findFragmentById(R.id.container2)).displayBook(book);
        } else
            fm.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment.newInstance(book))
                    .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        rq = Volley.newRequestQueue(this);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchURL = "https://kamorris.com/lab/abp/booksearch.php?search=" + editText.getText().toString();
                books = new ArrayList<>();
                JsonObjectRequest objectRequest = new JsonObjectRequest(searchURL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    JSONObject item;
                                    for (int i = 0; i < array.length(); i++) {
                                        item = array.getJSONObject(i);
                                        books.add(new Book(item.getInt("book_id"), item.getString("title"), item.getString("author"), item.getString("cover_url")));
                                    }
                                    BookListFragment bookListFragment = BookListFragment.newInstance(books);
                                    fm.beginTransaction().replace(R.id.container1, bookListFragment).commit();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(MainActivity.this,"It's borked!",Toast.LENGTH_SHORT).show();
                            }
                        });
                rq.add(objectRequest);
            }
        });
    }
}