package org.youngmonkeys.personal.test;

import org.youngmonkeys.devtools.pagination.PaginationClassesGenerator;

public class PersonalPaginationClassesGenerator {

    public static void main(String[] args) throws Exception {
        new PaginationClassesGenerator(Object.class)
            .generate();
    }
}
