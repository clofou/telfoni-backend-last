package org.bamappli.telfonibackendspring.Constants;

public class VariableEnv {
    public static String get(String key) {
        return System.getProperty(key);
    }
}
