package org.youngmonkeys.sweet_bakery.admin.test;

import org.youngmonkeys.devtools.swagger.SwaggerGenerator;

import java.io.File;

public class SweetBakeryAdminPluginSwaggerGenerator {

    public static void main(String[] args) throws Exception {
        SwaggerGenerator swaggerGenerator = new SwaggerGenerator(
            "org.youngmonkeys.sweet_bakery.admin.controller"
        );
        String filePath = "src/test/resources/static/files/swagger.yaml";
        File srcFolder = new File("src");
        if (!srcFolder.exists()) {
            filePath = "sweet-bakery-admin-plugin/" + filePath;
        }
        swaggerGenerator.generateToFile(filePath);
    }
}
