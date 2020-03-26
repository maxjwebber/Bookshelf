package edu.temple.bookshelf;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

public class BookShelfBuilder {

    public static ArrayList<HashMap<String,String>> generateBookshelf(
    Context context)
    {
        ArrayList<HashMap<String,String>> books= new ArrayList<>();
        String[] booksArray = context.getResources().getStringArray(R.array.books);
        String[] authorsArray = context.getResources().getStringArray(R.array.authors);
        HashMap<String,String> hm;
        for (int i = 0; i < booksArray.length; i++)
        {
            hm = new HashMap<String, String>() {};
            hm.put(booksArray[i],authorsArray[i]);
            books.add(hm);
        }
        return books;
    }
}
