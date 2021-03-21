
package redesocial.controllers;

import redesocial.models.Conta;

public class GerenciamentoConta {

    private Conta[] contas;
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

    public Conta buscarLogin(String login) {
        for (int i = 0; i < qtd; i++) {
            if (contas[i].getLogin().compareTo(login) == 0) {
                return contas[i];
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
