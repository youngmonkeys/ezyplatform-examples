package org.youngmonkeys.personal.web.test;

import org.youngmonkeys.devtools.UnitTestClassGenerator;

public class UnitTestGenerator {

    public static void main(String[] args) {
        new UnitTestClassGenerator(
            Object.class
        ).printContent();
    }
}
