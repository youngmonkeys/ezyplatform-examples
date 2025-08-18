package org.youngmonkeys.bookstore.web.test;

import org.youngmonkeys.devtools.swagger.SwaggerGenerator;

public class BookStoreWebPluginSwaggerGenerator {

    public static void main(String[] args) throws Exception {
        SwaggerGenerator swaggerGenerator = new SwaggerGenerator(
            "org.youngmonkeys.bookstore.web.controller"
        );
        swaggerGenerator.generateToDefaultFile();
    }
}
