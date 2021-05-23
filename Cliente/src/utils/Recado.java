/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.io.Serializable;

public class Recado implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mensagem;
    private String autor;
    private String senha;
    private boolean secreta;
    private boolean mural;
    
    public Recado(String mensagem, String autor) {
        this.mensagem = mensagem;
        this.autor = autor;
        this.senha = "";
        this.secreta = false;
    }

    public Recado(String mensagem, String autor, String senha) {
        this.mensagem = mensagem;
        this.autor = autor;
        this.senha = senha;
        this.secreta = true;
    }

    public Recado(String mensagem, String autor, boolean mural){
        this.mensagem = mensagem;
        this.autor = autor;
        this.mural = mural;
    }

    public String getMensagem() {
        if (!secreta) {
            return this.mensagem;
        } else {
            return "";
        }
    }

    public String getMensagem(String senha){
        if(this.senha == senha) {
            return this.mensagem;
        } else {
            return "";
        }
    }

    public String getAutor() {
        return this.autor;
    }

    public String toString() {
        return "Mensagem: " + this.mensagem + "Enviada por: " + this.autor;
    }

    public boolean ehSecreta(){
        return this.secreta;
    }

    public boolean abrirMensagemSecreta(String senha) {
        return (this.senha == senha);
    }

    public boolean exibirNoMural(){
        return this.mural;
    }
}
