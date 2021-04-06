package redesocial.controllers;

import redesocial.models.*;
import redesocial.repository.ContaDao;

public class GerenciamentoConta {

    public void cadastrar(String login, String senha, String nomeUsuario) {
        ContaDao cd = ContaDao.getInstance();
        if (cd.buscarLogin(login) == null) {
            cd.cadastrar(new Conta(login, senha, new Perfil(nomeUsuario)));
        } else {
            System.out.println("Login já utilizado!");
        }
    }

    public Conta logar(String login, String senha) {
        ContaDao cd = ContaDao.getInstance();
        Conta c = cd.buscarLogin(login);
        if (c == null) {
            return null;
        } else if (!(c.getSenha().equals(senha))) {
            return null;
        } else {
            return c;
        }
    }
    
    public Conta[] listarContas() {
        return ContaDao.getInstance().getContas();
    }
}
