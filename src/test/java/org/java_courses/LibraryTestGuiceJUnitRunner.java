package org.java_courses;

import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import net.lamberto.junit.GuiceJUnitRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(GuiceJUnitRunner.class)
class LibraryTestGuiceJUnitRunner {

    private static final ArrayList<Book> al = new ArrayList<>(Arrays.asList(new Book("test1", new Author("test1")), new Book("test2", new Author("test2"))));

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

    @Test
    void capacityLessThanNeeded(){
        final Injector injector = Guice.createInjector(new TestModule());
        assertThrows(Exception.class, () -> { injector.getInstance(LibraryFactory.class).createLibrary(1);});
    }

    @Test
    void constructorExactTest(){
        final Injector injector = Guice.createInjector(new TestModule());
        try {
            Library lib = injector.getInstance(LibraryFactory.class).createLibrary(3);
            var f = lib.getClass().getDeclaredField("books");
            f.setAccessible(true);
            al.add(null);
            assertEquals(al, f.get(lib));
        } catch (Exception e) {
            Assertions.fail();
        }
    }

    @Test
    void addBookCellTest() {
        final Injector injector = Guice.createInjector(new TestModule());
        try {
            var lib = injector.getInstance(LibraryFactory.class).createLibrary(5);
            lib.addBook(Mockito.mock(Book.class));
            lib.takeBook(1);
            var b  = new Book("test", new Author("tests"));
            lib.addBook(b);
            assertEquals("1 cell has:" + new GsonBuilder().setPrettyPrinting().create().toJson(b),
                    lib.takeBook(1));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void addBookNoEmptyCells() {
        final Injector injector = Guice.createInjector(new TestModule());
        try {
            var lib = injector.getInstance(LibraryFactory.class).createLibrary(2);
            assertThrows(Exception.class, () -> { lib.addBook(Mockito.mock(Book.class));});
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void printLibraryContents() {
        final Injector injector = Guice.createInjector(new TestModule());
        try {
            Library lib = injector.getInstance(LibraryFactory.class).createLibrary(2);
            ArrayList<Book> al = new ArrayList<>(Arrays.asList(new Book("test1", new Author("test1")), new Book("test2", new Author("test2"))));
            String str = new GsonBuilder().setPrettyPrinting().create().toJson(al);
            assertEquals(str, lib.printLibraryContents());
        } catch (Exception e) {
            fail();
        }
    }
}