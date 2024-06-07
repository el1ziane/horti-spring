package com.projeto.enums;

public enum Estado {
    ATIVO("Ativo"), INATIVO("Inativo");

    private String value;

    private Estado(String value) {
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
