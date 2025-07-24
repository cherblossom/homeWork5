package ru.vasilev.homeWork5.exception;

import lombok.Getter;

@Getter
public class TokenProcessingException extends RuntimeException {
    private final String code;

    public TokenProcessingException(String code) {
        super(code);
        this.code = code;
    }

}
