package redesocial.controllers;

import java.util.List;
import redesocial.models.*;
import redesocial.repository.ContaDao;

public class GerenciamentoConta {

    public void cadastrar(String login, String senha, String nomeUsuario,
            TipoContaEnum tipoConta) {
        ContaDao cd = ContaDao.getInstance();
        if (cd.buscarLogin(login) == null) {
            cd.cadastrar(new Conta(login, senha, new Perfil(nomeUsuario),
                    tipoConta));
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

    public Conta buscarLogin(String login) {
        return ContaDao.getInstance().buscarLogin(login);
    }

    public boolean excluirConta(String login) {
        Conta c = buscarLogin(login);
        if (c == null) {
            return false;
        }
        c.setAtivo(false);
        /*ContaDao.getInstance().removerConta(c);*/
        //código para REALMENTE remover conta
        return true;
    }

    public void editarContaADM(String login, int num, String alteracao) {
        Conta c = buscarLogin(login);
        switch (num) {
            case 1:
                if(c != null){
                c.setSenha(alteracao);
                }
                break;
            case 2:
                if(c != null){
                c.getPerfil().setNome(alteracao);
                }
                break;
            default:
                if(!c.isAtivo()){
                    c.setAtivo(true);
                }
                break;
        }
    }

    public List<Conta> listarContas() {
        return ContaDao.getInstance().getContas();
    }
}
