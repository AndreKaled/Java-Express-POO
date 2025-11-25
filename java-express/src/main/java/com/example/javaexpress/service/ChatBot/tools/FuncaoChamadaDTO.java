package com.example.javaexpress.service.ChatBot.tools;

import java.util.Map;

public class FuncaoChamadaDTO {

    private String funcao;
    private Map<String, String> parametros;

    public FuncaoChamadaDTO() {
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Map<String, String> getParametros() {
        return parametros;
    }

    public void setParametros(Map<String, String> parametros) {
        this.parametros = parametros;
    }
}