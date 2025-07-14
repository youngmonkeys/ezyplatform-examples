package org.youngmonkeys.personal.web.test;

import org.youngmonkeys.devtools.swagger.SwaggerGenerator;

public class PersonalThemeSwaggerGenerator {

    public static void main(String[] args) throws Exception {
        SwaggerGenerator swaggerGenerator = new SwaggerGenerator(
            "org.youngmonkeys.personal.web.controller"
        );
        swaggerGenerator.generateToDefaultFile();
    }
}
