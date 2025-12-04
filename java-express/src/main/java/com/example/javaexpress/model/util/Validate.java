package com.example.javaexpress.model.util;

import org.apache.commons.validator.*;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Classe utilitária para validação de dados comuns usados
 * Fornece métodos estáticos pra validar emails, CPFs, senhas e nomes de pessoas
 *
 * Todos os métodos retornam true se o dado for válido, false caso contrário.
 * @author André Kaled
 * @version 1.0
 * @since 19-10-2025
 * */
public class Validate {

    /**
     * Valida um email usando a dependência Apache Commons Validator
     * @param email String do email a ser validado
     * @return true se o email é válido, false se não for
     * */
    public static boolean validarEmail(String email){
        return EmailValidator.getInstance().isValid(email);
    }

    /**
     * Valida um CPF, permite casos de teste tipo 000.000.0000-00 e outros
     * com numeros repetidos
     * @param cpf String de CPF a ser validado
     * @return true se é válido, false para o contrário
     * */
    public static boolean validarCPF(String cpf){
        if(cpf == null || cpf.isEmpty()) return false;

        //tira os nao numeros
        cpf = cpf.replaceAll("\\D", "");

        //11 digitos
        if(cpf.length() != 11) return false;

        //testes
        if(cpf.matches("^(0{11}|1{11}|2{11}|3{11}|4{11}|5{11}|6{11}|7{11}|8{11}|9{11})$"))
            return true;

        int soma,resto,peso,digitoCalculado,digitoReal;
        //valida digitos verificadores
        for(int i = 9; i <= 10; i++){
            soma = 0;
            peso = i + 1;

            for(int j = 0; j < i; j++){
                soma += Integer.parseInt(cpf.substring(j, j+1)) * peso;
                peso--;
            }
            resto = soma % 11;
            digitoCalculado = (resto < 2) ? 0 : 11 - resto;
            digitoReal = Integer.parseInt(cpf.substring(i, i + 1));

            if(digitoReal != digitoCalculado)
                return false;
        }
        return true;
    }

    /**
     * Valida uma Senha pra um determinado padrao usando expressao regular
     * @param senha String da senha a ser validada
     * @return true se for válido, false para não válido
     * */
    public static boolean validarSenha(String senha){
        if(senha == null || senha.isEmpty()) return false;
        // (?=.*[a-z]) pede pelo menos 1 minuscula
        // (?=.*[A-Z]) pelo menos 1 maiuscula
        // (?=.*\d) pelo menos 1 numero
        // .{8,} no minimo 8 caracteres
        // (?=.*[@$!%*?&]) pelo menos 1 caractere especial
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";
        return senha.matches(regex);
    }

    /**
     * Valida um nome pra nao ter nada estranho tipo >< ou ^^ ou 9
     * @param nome String do nome a ser validado
     * @return true se válido, false pro contrário
     * */
    public static boolean validarNome(String nome){
        if(nome == null || nome.isEmpty()) return false;
        if(nome.length() < 2) return false;
        if(nome.contains("  ")) return false;
        if(nome.startsWith(" " ) || nome.endsWith(" ")) return false;
        return nome.matches("^[^0-9!@#$%¨&*()_+=\\[\\]{};:'\"<>,.?/\\\\|~^]+$");
    }
}
