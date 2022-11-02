package org.java_courses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;

import java.util.ArrayList;

public final class Library {
    private final ArrayList<Book> books;
    @Inject
    public Library(int n, BookFactory bf) throws Exception {
        books = new ArrayList<>();
        books.ensureCapacity(n);

        ArrayList<Book> b = (ArrayList<Book>) bf.getBooks();
        if(b.size() > n)
            throw new Exception("LIBRARY::Not enough space for books");
        for(int i=0; i < n; i++) {
            if (b.size() > 0)
                books.add(b.remove(0));
            else
                books.add(null);
        }
    }

    public String takeBook(int i) throws Exception {
        if (i > books.size() || i < 0)
            throw new Exception("TAKEBOOK::Out of bounds i");
        Book b = books.get(i);
        if (b == null)
            throw new Exception("TAKEBOOK::Empty cell");
        books.set(i, null);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return i + " cell has:" + gson.toJson(b);
    }

    public void addBook(Book b) throws Exception {
        var i =  books.indexOf(null);
        if(i == -1)
            throw new Exception("ADDBOOK::No space for a new book");
        books.set(i,b);
    }
    public String printLibraryContents() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return  gson.toJson(books);
    }
}
