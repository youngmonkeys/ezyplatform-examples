package org.youngmonkeys.sweet_bakery.test;

import com.tvd12.ezyfox.tool.EzyBuilderCreator;

public class BuilderClassGenerator {

    public static void main(String[] args) throws Exception {
        String script = new EzyBuilderCreator()
            .create(Object.class)
            .replace("\t", "    ");
        System.out.println(script);
    }
}
