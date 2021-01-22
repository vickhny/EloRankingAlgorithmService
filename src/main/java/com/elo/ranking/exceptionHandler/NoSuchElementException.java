package com.elo.ranking.exceptionHandler;

public class NoSuchElementException extends RuntimeException{

    public NoSuchElementException(Long id) {
        super("Plaer with id - "+id+" not found!!");
    }
}
