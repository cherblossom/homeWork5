package ru.vasilev.homeWork5.exception;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {
    private final String code;

    public InvalidTokenException(String code) {
        super(code);
        this.code = code;
    }
}

