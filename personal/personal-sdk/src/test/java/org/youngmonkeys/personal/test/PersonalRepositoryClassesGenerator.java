package org.youngmonkeys.personal.test;

import org.youngmonkeys.devtools.repository.RepositoryClassesGenerator;
import org.youngmonkeys.personal.entity.PersonalPostWordCount;

public class PersonalRepositoryClassesGenerator {

    public static void main(String[] args) throws Exception {
        new RepositoryClassesGenerator(PersonalPostWordCount.class)
            .generate();
    }
}
