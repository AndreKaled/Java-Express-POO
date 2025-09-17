package com.example.javaexpress.model.model;

public enum StatusEncomenda {
    ENTREGUE("ENTREGUE"),
    ENVIADO("ENVIADO"),
    EM_ROTA("EM ROTA"),;

    private String status;

    StatusEncomenda(String status){
        this.status = status;
    }

    public String getStatus(){return status;}
}
