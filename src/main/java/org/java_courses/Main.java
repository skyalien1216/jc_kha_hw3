package org.java_courses;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jetbrains.annotations.NotNull;

public class Main {
    public static void main(@NotNull String[] args) throws Exception {
        int capacity = Integer.parseInt(args[0]);
        String filepath = args[1];

        final Injector injector = Guice.createInjector(new GuiceModule(filepath));
        Library lib  = injector.getInstance(LibraryFactory.class).createLibrary(capacity);
        System.out.println(lib.printLibraryContents());
    }
}