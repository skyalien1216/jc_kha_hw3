package org.java_courses;

import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class LibraryTestSelfmade {
    //without guice runner

    public static class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            BookFactoryFactory m = Mockito.mock(BookFactoryFactory.class);
            BookFactory bf = Mockito.mock(BookFactory.class);
            Mockito.when(bf.getBooks()).thenReturn(new ArrayList<>(al));
            Mockito.when(m.create(any())).thenReturn(bf);
            bind(BookFactoryFactory.class).toInstance(m);
            bind(String.class).annotatedWith(Names.named("filepath")).toInstance("");
        }
    }

    private static final ArrayList<Book> al = new ArrayList<>(Arrays.asList(new Book("test1", new Author("test1")), new Book("test2", new Author("test2"))));

    private static Library lib;

    @BeforeAll
    static void prepare(){
        final Injector injector = Guice.createInjector(new TestModule());
        try {
            lib = injector.getInstance(LibraryFactory.class).createLibrary(3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void takeBookPrint() {
        try {
            assertFalse(lib.takeBook(1).isEmpty());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void takeBookFromEmptyCell(){
        assertThrows(Exception.class, () -> { lib.takeBook(2);});
    }

    @Test
    void takeBookExactBook() {
        try {
            int i = 0;
            String book =  lib.takeBook(i);
            String b = i + " cell has:" + new GsonBuilder().setPrettyPrinting().create().toJson(al.get(i));
            assertEquals(b, book);
        } catch (Exception e) {
            fail();
        }
    }
}