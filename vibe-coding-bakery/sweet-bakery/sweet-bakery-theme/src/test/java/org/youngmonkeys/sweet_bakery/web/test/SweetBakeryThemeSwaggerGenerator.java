package org.youngmonkeys.sweet_bakery.web.test;

import org.youngmonkeys.devtools.swagger.SwaggerGenerator;

import java.io.File;

public class SweetBakeryThemeSwaggerGenerator {

    public static void main(String[] args) throws Exception {
        SwaggerGenerator swaggerGenerator = new SwaggerGenerator(
            "com.example.hello_ezyplatform.web.controller"
        );
        String filePath = "src/test/resources/static/files/swagger.yaml";
        File srcFolder = new File("src");
        if (!srcFolder.exists()) {
            filePath = "sweet-bakery-theme/" + filePath;
        }
        swaggerGenerator.generateToFile(filePath);
    }
}
