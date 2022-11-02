package org.java_courses;

import com.google.inject.Inject;

import javax.inject.Named;

public final class LibraryFactory {
    @Inject @Named("filepath")
    private String filepath;
    @Inject
    private BookFactoryFactory bff;
    public Library createLibrary(int n) throws Exception {
        return new Library(n, bff.create(filepath));
    }
}
