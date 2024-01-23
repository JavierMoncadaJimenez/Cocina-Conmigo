package com.javi.cocinaconmigo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nuca_ on 04/04/2018.
 */

public class Receta implements Serializable{

    private String uidAtuor;
    private String cuerpo;
    private String titulo;
    private String ingredientes;
    private String nombreFoto;
    private long fechaSubida;
    private ArrayList <Comentario> comentarios;
    private String key;

    public void Receta () {
        comentarios = new ArrayList<>();
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombreFoto) {
        this.nombreFoto = nombreFoto;
    }

    public String getUidAtuor() {
        return uidAtuor;
    }

    public void setUidAtuor(String uidAtuor) {
        this.uidAtuor = uidAtuor;
    }

    public void setFechaSubida(long fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public long getFechaSubida() {
        return fechaSubida;
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}


