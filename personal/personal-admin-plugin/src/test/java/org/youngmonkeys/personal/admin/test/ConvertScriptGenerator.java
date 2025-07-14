package org.youngmonkeys.personal.admin.test;

import com.tvd12.ezyfox.tool.EzySameObjectScriptCreator;

public class ConvertScriptGenerator {

    public static void main(String[] args) {
        String script = new EzySameObjectScriptCreator()
            .originClass(Object.class)
            .originObjectName("model")
            .targetClass(Object.class)
            .targetObjectName("response")
            .generateBuildFuncScript();
        System.out.println(script);
    }
}
