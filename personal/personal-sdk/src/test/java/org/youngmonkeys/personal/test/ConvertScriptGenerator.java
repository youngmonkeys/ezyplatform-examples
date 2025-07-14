package org.youngmonkeys.personal.test;

import com.tvd12.ezyfox.tool.EzySameObjectScriptCreator;

public class ConvertScriptGenerator {

    public static void main(String[] args) {
        String script = new EzySameObjectScriptCreator()
            .originClass(Object.class)
            .originObjectName("entity")
            .targetClass(Object.class)
            .targetObjectName("model")
            .generateBuildFuncScript();
        System.out.println(script);
    }
}
