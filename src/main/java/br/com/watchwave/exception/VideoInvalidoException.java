package br.com.watchwave.exception;

public class VideoInvalidoException extends RuntimeException{
    public VideoInvalidoException(String mensagem){
        super(mensagem);
    }
}
