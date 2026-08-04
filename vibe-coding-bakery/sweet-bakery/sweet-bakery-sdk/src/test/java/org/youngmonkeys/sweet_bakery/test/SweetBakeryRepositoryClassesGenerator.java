package org.youngmonkeys.sweet_bakery.test;

import org.youngmonkeys.devtools.repository.RepositoryClassesGenerator;

public class SweetBakeryRepositoryClassesGenerator {

    public static void main(String[] args) throws Exception {
        new RepositoryClassesGenerator(Object.class)
            .generate();
    }
}
