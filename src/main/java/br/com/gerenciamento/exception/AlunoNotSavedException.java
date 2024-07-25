package br.com.gerenciamento.exception;

public class AlunoNotSavedException extends RuntimeException {
    public AlunoNotSavedException(String message) {
        super(message);
    }
}
