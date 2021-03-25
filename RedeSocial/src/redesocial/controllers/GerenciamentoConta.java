
package redesocial.controllers;

import redesocial.models.*;

public class GerenciamentoConta {

    private Conta[] contas;
    private Perfil[] perfis;
    private int qtd;

    public GerenciamentoConta() {
        this.contas = new Conta[100];
        this.qtd = 0;
    }

    public void cadastrar(String login, String senha, String nomeUsuario) {
        if (buscarLogin(login) == null) { //login não utilizado
            contas[qtd] = new Conta(login, senha, nomeUsuario); //cadastro
            ++qtd;
        } else {
            System.out.println("Login já utilizado!");
        }
    }
    
    public void cadastrarPerfil (String nomePerfil, String local, int idade, String fone) {
        if(buscarNomePerfil(nomePerfil) == null) {
            perfis[qtd] = new Perfil(nomePerfil, local, fone, idade);
            qtd++;
        }
        else {
            System.out.println("Nome de Usuário já está sendo ultilizado!");
        }
    }

    public Conta buscarLogin(String login) {
        for (int i = 0; i < qtd; i++) {
            if (contas[i].getLogin().compareTo(login) == 0) {
                return contas[i];
            }
        }
        return null;
    }

    public Perfil buscarNomePerfil(String nomePerfil) {
        for (int i = 0; i < qtd; i++) {
            if (perfis[i].getNome().compareTo(nomePerfil) == 0) {
                return perfis[i];
            }
        }
        return null;
    }
    
    /*public void alterarDados(Conta c){
        
    }
    
    public void excluir(String login){
        
    }
    
    public Conta buscarNomeUsuario(String nomeUsuario) {
        for (int i = 0; i < qtd; i++) {
            if (contas[i].getNomeUsuario().compareTo(nomeUsuario) == 0) {
                return contas[i];
            }
        }
        return null;
    }*/
    }
