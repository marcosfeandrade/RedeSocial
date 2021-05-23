/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import java.io.IOException;
import utils.*;
import repository.ContaDao;
import utils.ConexaoUtils;
import utils.Protocolo;
import utils.StatusCodigo;

public class ContaController {

    private static ContaController contaController;
    
    private ContaController() {
    }

    public static ContaController getInstance() {
        if (contaController == null) {
            contaController = new ContaController();
        }
        return contaController;
    }

    public void cadastrar(ConexaoUtils conexaoUtils, ContaAbstrata conta) throws IOException {
        ContaDao cd = ContaDao.getInstance();
        if (cd.buscarLogin(conta.getLogin()) != null) {
            System.out.println("Login já utilizado!");
            conexaoUtils.enviar(new Protocolo(null, null,
                    StatusCodigo.CONFLICT));

        } else {
            cd.cadastrar(conta);
            //return via socket ok
            conexaoUtils.enviar(new Protocolo(null, null,
                    StatusCodigo.OK));

        }

    }

    public void logar(ConexaoUtils conexaoUtils, ContaAbstrata cAbstrata) throws IOException {
        ContaDao cd = ContaDao.getInstance();
        ContaAbstrata c = cd.buscarLogin(cAbstrata.getLogin());
        if (c == null) {
            conexaoUtils.enviar
        (new Protocolo(null, null, StatusCodigo.NOT_FOUND));
        } else if (!(c.getSenha().equals(cAbstrata.getSenha()))) {
            conexaoUtils.enviar
        (new Protocolo(null, null, StatusCodigo.UNAUTHORIZED));
        } else {
            conexaoUtils.enviar
        (new Protocolo(null, null, StatusCodigo.OK));
        }
    }
    
    public void adicionarAmigos(ConexaoUtils conexaoUtils, ContaAbstrata contaAbstrata){
        //Pegar conta no ContaDao
        //Fazer as validações na conta(se existe ou nao por exemplo)
        //Adicionar amigos aquela conta
        //retornar ao frontend a resposta(Protocolo) adequado.
    }

    /*public ContaUsuario buscarLogin(String login) {
        return ContaDao.getInstance().buscarLogin(login);
    }

    public boolean excluirConta(String login) {
        ContaUsuario c = buscarLogin(login);
        if (c == null) {
            return false;
        }
        c.setAtivo(false);
        return true;
    }

    public void editarContaADM(String login, int num, String alteracao) {
        ContaUsuario c = buscarLogin(login);
        switch (num) {
            case 1:
                if (c != null) {
                    c.setSenha(alteracao);
                }
                break;
            case 2:
                if (c != null) {
                    c.getPerfil().setNome(alteracao);
                }
                break;
            default:
                if (!c.isAtivo()) {
                    c.setAtivo(true);
                }
                break;
        }
    }

    public List<ContaUsuario> listarContas() {
        return ContaDao.getInstance().getContas();
    }*/
}
