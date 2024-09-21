package com.projeto.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    PAGO("Pago"), 
    PENDENTE("Pendente"), 
    RECEBIDO("Recebido"), 
    PESO("PESO"), 
    CAIXA("CAIXA");

    private String value;

    private Status(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Status: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}

