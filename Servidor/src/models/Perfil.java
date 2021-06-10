/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.io.Serializable;
import java.util.ArrayList;

public class Perfil implements Comparable<Perfil>, Serializable {
    private static final long serialVersionUID = 1L;
    //atributos
    private String nomePerfil;
    private ArrayList<Recado> recados;
    private ArrayList<Recado> mural;
    private ArrayList<Perfil> convites;
    private ArrayList<Perfil> amigos;
    private ArrayList<Perfil> matchs;

    public Perfil(String nomePerfil) {
        this.nomePerfil = nomePerfil;
        this.recados = new ArrayList<>();
        this.convites = new ArrayList<>();
        this.amigos = new ArrayList<>();
        this.matchs = new ArrayList<>();
        this.mural = new ArrayList<>();
    }

    //metodos set e get
    public void setNome(String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    public String getNome() {
        return nomePerfil;
    }

    @Override
    public String toString() {
        return "Nome: " + this.nomePerfil;
    }

    @Override
    public int compareTo(Perfil p) {
        return this.getNome().compareTo(p.getNome());
    }

    public void enviarRecado(String msg, String autor) {
        Recado recado = new Recado(msg, autor);
        recados.add(recado);
    }

    public void enviarRecadoMural(String msg, String autor) {
        Recado recado = new Recado(msg, autor, true);
        this.mural.add(recado);
    }

    public ArrayList<Recado> getMural() {
        return this.mural;
    }

    public void aceitarMural(int i) {
        Recado recado = this.mural.get(i);
        this.recados.add(recado);
    }

    public void enviarRecado(String msg, String autor, String senha) {
        Recado recado = new Recado(msg, autor, senha);
        recados.add(recado);
    }

    public ArrayList<Recado> getRecados() {
        return this.recados;
    }

    public ArrayList<Perfil> getAmigos() {
        return amigos;
    }

    public void addConvite(Perfil solicitante) {
        this.convites.add(solicitante);
    }

    public ArrayList<Perfil> getConvites() {
        return this.convites;
    }

    public void addMatch(Perfil solicitante) {
        this.matchs.add(solicitante);
    }

    public ArrayList<Perfil> getMatchs() {
        return this.matchs;
    }

    public boolean verificaMatch(Perfil p) {
        boolean v = false;
        for (int i = 0; i < this.matchs.size(); i++) {
            if (this.matchs.get(i).getNome().equals(p.getNome()) == true) {
                v = true;
                break;
            }
        }
        if (v) {
            v = false;
        }
        return v;
    }

    public void aceitarConvite(Perfil perfil) {
        boolean achouConvite = false;
        int index = 0;
        for (int i = 0; i < this.convites.size(); i++) {
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

