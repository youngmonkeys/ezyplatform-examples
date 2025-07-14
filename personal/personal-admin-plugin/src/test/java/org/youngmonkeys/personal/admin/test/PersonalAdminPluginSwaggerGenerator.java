package org.youngmonkeys.personal.admin.test;

import org.youngmonkeys.devtools.swagger.SwaggerGenerator;

public class PersonalAdminPluginSwaggerGenerator {

    public static void main(String[] args) throws Exception {
        SwaggerGenerator swaggerGenerator = new SwaggerGenerator(
            "org.youngmonkeys.personal.admin.controller"
        );
        swaggerGenerator.generateToDefaultFile();
    }
}
