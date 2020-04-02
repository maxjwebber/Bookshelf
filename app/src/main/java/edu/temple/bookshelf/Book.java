package edu.temple.bookshelf;


import android.os.Parcel;
import android.os.Parcelable;

//thanks to parcelabler.com for making this implementation easier
public class Book implements Parcelable {
    int book_id;
    String title;
    String author;
    String coverURL;

    public Book(int book_id, String title, String author, String coverURL)
    {
        this.book_id=book_id;
        this.title=title;
        this.author=author;
        this.coverURL=coverURL;
    }

    protected Book(Parcel in) {
        book_id = in.readInt();
        title = in.readString();
        author = in.readString();
        coverURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(book_id);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(coverURL);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
