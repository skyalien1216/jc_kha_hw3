package org.java_courses;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;

@Singleton
public class GuiceModule extends AbstractModule {
    private final String filepath;

    public GuiceModule(String filepath) {
        this.filepath = filepath;
    }

    @Override
    protected void configure() {
        bind(String.class)
                .annotatedWith(Names.named("filepath"))
                .toInstance(filepath);

        install(new FactoryModuleBuilder()
                .implement(BookFactory.class, FileBookFactory.class)
                .build(BookFactoryFactory.class));
    }
}
