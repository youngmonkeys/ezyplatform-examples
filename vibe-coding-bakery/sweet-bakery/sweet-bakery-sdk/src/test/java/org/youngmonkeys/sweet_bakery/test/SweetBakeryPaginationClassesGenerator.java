package org.youngmonkeys.sweet_bakery.test;

import org.youngmonkeys.devtools.pagination.PaginationClassesGenerator;

public class SweetBakeryPaginationClassesGenerator {

    public static void main(String[] args) throws Exception {
        new PaginationClassesGenerator(Object.class)
            .generate();
    }
}
