package org.youngmonkeys.personal.socket.app.test;

import org.youngmonkeys.devtools.UnitTestClassGenerator;

public class UnitTestGenerator {

    public static void main(String[] args) {
        new UnitTestClassGenerator(
            Object.class
        ).printContent();
    }
}
