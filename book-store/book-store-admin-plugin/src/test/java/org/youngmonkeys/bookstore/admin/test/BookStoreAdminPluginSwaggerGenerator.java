package org.youngmonkeys.bookstore.admin.test;

import org.youngmonkeys.devtools.swagger.SwaggerGenerator;

public class BookStoreAdminPluginSwaggerGenerator {

    public static void main(String[] args) throws Exception {
        SwaggerGenerator swaggerGenerator = new SwaggerGenerator(
            "org.youngmonkeys.bookstore.admin.controller"
        );
        swaggerGenerator.generateToDefaultFile();
    }
}
