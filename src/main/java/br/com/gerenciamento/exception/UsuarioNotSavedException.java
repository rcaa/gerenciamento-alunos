package br.com.gerenciamento.exception;

public class UsuarioNotSavedException extends RuntimeException {
    public UsuarioNotSavedException(String message) {
        super(message);
    }
}
