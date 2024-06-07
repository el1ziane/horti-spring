package com.projeto.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class EstadoConverter implements AttributeConverter<Estado, String>{

    @Override
    public String convertToDatabaseColumn(Estado estado) {
        if(estado == null){
            return null;
        }
        return estado.getValue();
    }

    @Override
    public Estado convertToEntityAttribute(String value) {
        if(value == null){
            return null;
        }
        return Stream.of(Estado.values())
                .filter(estado -> estado.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
    
}
