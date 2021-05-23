/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import java.io.Serializable;
import java.util.ArrayList;

public class Perfil implements Comparable <Perfil>, Serializable {
    private static final long serialVersionUID = 1L;
    //atributos
    private String nomePerfil;
    private ArrayList<Recado> recados;
    private ArrayList<Recado> muralPendente;
    private ArrayList<Perfil> convites;
    private ArrayList<Perfil> amigos;
    private ArrayList<Perfil> matchs;
    
    public Perfil (String nomePerfil) {
        this.nomePerfil = nomePerfil;
        this.recados = new ArrayList<Recado>();
        this.convites = new ArrayList<Perfil>();
        this.amigos = new ArrayList<Perfil>();
        this.matchs = new ArrayList<Perfil>();
    }
    //metodos set e get
    public void setNome (String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }
    public String getNome () {
        return nomePerfil;
    }
    public String toString() {
        return "Nome: " + this.nomePerfil;
    }
    @Override
    public int compareTo (Perfil p) {
        return this.getNome().compareTo(p.getNome());
    }

    public void enviarRecado(String msg, String autor){
        Recado recado = new Recado(msg, autor);
        recados.add(recado);
    }
<<<<<<< HEAD:Cliente/src/utils/Perfil.java
    public void enviarRecadoMural(String msg, String autor){
        Recado recado = new Recado(msg, autor, true);
=======
    public void enviarRecadoMural(String msg, string autor){
        Recado recado = new Reacado(msg, autor, true);
>>>>>>> b5b92727f53d555d69cc07ab45d78709579555e7:RedeSocial/src/redesocial/models/Perfil.java
        this.muralPendente.add(recado);
    }

    public ArrayList<Recado> getMuralPendente() {
        return this.muralPendente;
    }

    public void aceitarMural(int i){
        Recado recado = this.muralPendente.get(i);
        this.recados.add(recado);
    }
    public void enviarRecado(String msg, String autor, String senha){
        Recado recado = new Recado(msg, autor, senha);
        recados.add(recado);
    }

    public ArrayList<Recado> getRecados() {
        return this.recados;
    }

    public ArrayList<Recado> getRecadosMuralParaAceitar() {
        return this.muralPendente;
    }
    public void addConvite(Perfil solicitante) {
        this.convites.add(solicitante);
    }

    public ArrayList<Perfil> getConvites(){
        return this.convites;
    }

    public void addMatch(Perfil solicitante) {
        this.matchs.add(solicitante);
    }

    public ArrayList<Perfil> getMatchs () {
        return this.matchs;
    }

    public boolean verificaMatch (Perfil p) {
        boolean v = false;
        for(int i = 0; i < this.matchs.size(); i++) {
            if(this.matchs.get(i).getNome().equals(p.getNome()) == true) {
                v = true;
                break;
            }
        }
        if(v) {
            v = false;
        }
        return v;
    }

    public void aceitarConvite(Perfil perfil) {
        boolean achouConvite = false;
        int index = 0;
        for (int i=0; i < this.convites.size(); i++){
            if (this.convites.get(i).getNome().equals(perfil.getNome())) {
                achouConvite = true;
                index = i;
                break;
            }
        }

        if (achouConvite) {
            this.convites.remove(index);
            this.amigos.add(perfil);
            perfil.amigos.add(this);
        }
    }


    
}