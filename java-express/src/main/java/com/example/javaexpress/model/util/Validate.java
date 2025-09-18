package com.example.javaexpress.model.util;

public class Validate {

    //depois uso regex pra validar essa bomba
    public static boolean validarEmail(String email){
        return email != null && email.contains("@");
    }
}
