package redesocial.controllers;

import redesocial.models.*;

public class GerenciamentoConta {

    private Conta[] contas;
    private int qtd;

    public GerenciamentoConta() {
        this.contas = new Conta[100];
        this.qtd = 0;
    }

    public void cadastrar(String login, String senha, String nomeUsuario) {
        if (buscarLogin(login) == -1) { //login não utilizado
            contas[qtd] = new Conta(login, senha, nomeUsuario); //cadastro
            ++qtd;
        } else {
            System.out.println("Login já utilizado!");
        }
    }

    public int buscarLogin(String login) {
        for (int i = 0; i < qtd; i++) {
            if (contas[i].getLogin().compareTo(login) == 0) {
                return i;
            }
        }
        return -1;
    }

    public int buscarSenha(String senha) {
        for (int i = 0; i < qtd; i++) {
            if (contas[i].getSenha().compareTo(senha) == 0) {
                return i;
            }
        }
        return -1;
    }

    public Conta logar(String login, String senha) {
        int buscarLogin = buscarLogin(login);
        int buscarSenha = buscarSenha(senha);
        if (buscarLogin == -1) {
            System.out.println("Login inexistente.");
            return null;
        } else if (buscarSenha == -1) {
            System.out.println("Senha inexistente.");
            return null;
        } else {
            if (buscarLogin != buscarSenha) {
                System.out.println("Login ou senha inválido.");
                return null;
            } else {
                System.out.println("Usuário "
                        + contas[buscarLogin].getNomeUsuario()
                        + " logado com sucesso!");
            }
        }
        return contas[buscarLogin];
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
