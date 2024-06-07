package com.projeto.enums;

public enum Status {
    PAGO("Pago"), PENDENTE("Pendente");

    private String value;

    private Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
        

    }

    // toString
    @Override
    public String toString() {
        return value;
    }

    
}
