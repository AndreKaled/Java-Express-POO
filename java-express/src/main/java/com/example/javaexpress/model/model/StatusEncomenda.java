package com.example.javaexpress.model.model;

public enum StatusEncomenda {
    ENTREGUE(0),
    ENVIADO(1),
    EM_ROTA(2);

    private int status;

    StatusEncomenda(int status){
        this.status = status;
    }

    public int getStatus(){return status;}
}
