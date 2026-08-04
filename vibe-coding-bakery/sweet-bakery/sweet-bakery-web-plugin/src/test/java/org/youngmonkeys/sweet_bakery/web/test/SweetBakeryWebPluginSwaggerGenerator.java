package org.youngmonkeys.sweet_bakery.web.test;

import org.youngmonkeys.devtools.swagger.SwaggerGenerator;

import java.io.File;

public class SweetBakeryWebPluginSwaggerGenerator {

    public static void main(String[] args) throws Exception {
        SwaggerGenerator swaggerGenerator = new SwaggerGenerator(
            "org.youngmonkeys.sweet_bakery.web.controller"
        );
        String filePath = "src/test/resources/static/files/swagger.yaml";
        File srcFolder = new File("src");
        if (!srcFolder.exists()) {
            filePath = "sweet-bakery-web-plugin/" + filePath;
        }
        swaggerGenerator.generateToFile(filePath);
    }
}
