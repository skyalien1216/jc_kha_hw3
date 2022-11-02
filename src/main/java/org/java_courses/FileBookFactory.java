package org.java_courses;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public final class FileBookFactory implements BookFactory {
    @NotNull
    private static final Type listBooksType = new TypeToken<ArrayList<Book>>() {}.getType();

    @NotNull
    private final String fileName;

    @AssistedInject
    public FileBookFactory(@Assisted @NotNull String fileName) {
        this.fileName = fileName;
    }

    @NotNull
    @Override
    public Collection<Book> getBooks() {
        try {
            return new Gson().fromJson(new BufferedReader(new FileReader(fileName)), listBooksType);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}