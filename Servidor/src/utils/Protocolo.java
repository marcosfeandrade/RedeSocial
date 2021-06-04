/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;

public class Protocolo <T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T obj;
    private String url;
    private StatusCodigo statusCodigo;

    public Protocolo(T obj, String url, StatusCodigo statusCodigo) {
        this.obj = obj;
        this.url = url;
        this.statusCodigo = statusCodigo;
    }

    public Protocolo(T obj, String url) {
        this.obj = obj;
        this.url = url;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public StatusCodigo getStatusCodigo() {
        return statusCodigo;
    }

    public void setStatusCodigo(StatusCodigo statusCodigo) {
        this.statusCodigo = statusCodigo;
    }

}

