/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;

public class ContaAbstrata implements Comparable<ContaAbstrata>, Serializable {
    private static final long serialVersionUID = 1L;
    private String login;
    private String senha;
    private boolean ativo;
    private Perfil perfil;

    public ContaAbstrata(String login, String senha, Perfil perfil) {
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
        ativo = true;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int compareTo(ContaAbstrata c) {
        return this.login.compareTo(c.login);
    }

    @Override
    public String toString() {
        return "Conta{" + "login=" + login + ", senha=" + senha + ", perfil="
                + perfil + ", ativa=" + ativo + "}";
    }
}

