package com.click.pc;

import java.util.List;

public class GenreModel {
    private String status;
    private String message;
    private List<String> Genere;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getGenere() {
        return Genere;
    }

    public void setGenere(List<String> genere) {
        Genere = genere;
    }
}
