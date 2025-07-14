package org.youngmonkeys.personal.test;

import org.youngmonkeys.devtools.repository.RepositoryClassesGenerator;

public class PersonalRepositoryClassesGenerator {

    public static void main(String[] args) throws Exception {
        new RepositoryClassesGenerator(Object.class)
            .generate();
    }
}
