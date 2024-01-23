package com.javi.cocinaconmigo;

import java.io.Serializable;

/**
 * Created by nuca_ on 15/05/2018.
 */

public class Comentario implements Serializable {

    private String autor;
    private String mensaje;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
