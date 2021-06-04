/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;
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
        if (c == null || !c.isAtivo()) {
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.NOT_FOUND));
        } else if (!(c.getSenha().equals(cAbstrata.getSenha()))) {
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.UNAUTHORIZED));
        } else {
            conexaoUtils.enviar(new Protocolo(c, null, StatusCodigo.OK));
        }
    }

    public void adicionarAmigos(ConexaoUtils conexaoUtils,
            StringsDTO envioAmigo) throws IOException {
        ContaDao cd = ContaDao.getInstance();
        ContaAbstrata c = cd.buscarLogin(envioAmigo.getS1());
        ContaAbstrata cAmigo = cd.buscarLogin(envioAmigo.getS2());
        if (c == null || cAmigo == null || !c.isAtivo() || !cAmigo.isAtivo()) {
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.NOT_FOUND));
        } else {
            cAmigo.getPerfil().addConvite(c.getPerfil());
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.OK));
        }
    }

    public void aceitarSolicitacaoAmizade(ConexaoUtils conexaoUtils,
            StringsDTO stringsDTO) throws IOException {
        ContaDao cd = ContaDao.getInstance();
        ContaAbstrata cAbstrata = cd.buscarLogin(stringsDTO.getS1());
        if (cAbstrata == null) {
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.NOT_FOUND));
        } else {
            for (Perfil p : cAbstrata.getPerfil().getConvites()) {
                if (p.getNome().equals(stringsDTO.getS2())) {
                    cAbstrata.getPerfil().aceitarConvite(p);
                    conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.OK));

                }
            }
        }
    }

    public void getConvites(ConexaoUtils conexaoUtils,
            String login) throws IOException {
        ContaDao cd = ContaDao.getInstance();
        ContaAbstrata cAbstrata = cd.buscarLogin(login);
        if (cAbstrata == null) {
            conexaoUtils.enviar(new Protocolo(null,
                    null, StatusCodigo.NOT_FOUND));
        } else {
            ArrayList<Perfil> convites = cAbstrata.getPerfil().getConvites();
            for (Perfil convite : convites) {
                conexaoUtils.enviar(new Protocolo(convite,
                        null, StatusCodigo.OK));
            }
            conexaoUtils.enviar(new Protocolo(new Integer(-1), null, StatusCodigo.OK));
        }
    }

    public void listarRecados(ConexaoUtils conexaoUtils, ContaAbstrata conta)
            throws IOException {
        ContaDao cd = ContaDao.getInstance();
        ContaAbstrata cAbstrata = cd.buscarLogin(conta.getLogin());
        if (cAbstrata == null) {
            conexaoUtils.enviar(new Protocolo(null,
                    null, StatusCodigo.NOT_FOUND));
        } else {
            ArrayList<Recado> recados = cAbstrata.getPerfil().getRecados();
            for (Recado convite : recados) {
                conexaoUtils.enviar(new Protocolo(convite,
                        null, StatusCodigo.OK));
            }
            conexaoUtils.enviar(new Protocolo(new Integer(-1), null,
                    StatusCodigo.OK));
        }
    }

    public void enviarRecado(ConexaoUtils conexaoUtils, EnvioRecado envioRecado)
            throws IOException {
        ContaAbstrata conta = ContaDao.getInstance().buscarLogin(envioRecado.getLogin());
        ContaAbstrata contaAmigo = ContaDao.getInstance().buscarLogin(envioRecado.getLoginAmigo());
        if (conta == null || contaAmigo == null) {
            conexaoUtils.enviar(new Protocolo(null,
                    null, StatusCodigo.NOT_FOUND));
        } else {
            if (envioRecado.getSenha().equals("")) {
                if (envioRecado.getMural() == 1) {
                    contaAmigo.getPerfil().enviarRecadoMural(envioRecado.
                            getMensagem(), conta.getPerfil().getNome());
                } else {
                    contaAmigo.getPerfil().enviarRecado(envioRecado.
                            getMensagem(), conta.getPerfil().getNome());
                }
            } else {
                contaAmigo.getPerfil().enviarRecado(envioRecado.getMensagem(),
                        conta.getPerfil().getNome(), envioRecado.getSenha());
            }
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.OK));
        }
    }

    public void verificarAmigo(ConexaoUtils conexaoUtils, StringsDTO stringsContas)
            throws IOException {
        ContaAbstrata conta = ContaDao.getInstance().buscarLogin(stringsContas.getS1());
        ContaAbstrata contaAmigo = ContaDao.getInstance().buscarLogin(stringsContas.getS2());
        if (conta == null || contaAmigo == null) {
            conexaoUtils.enviar(new Protocolo(null,
                    null, StatusCodigo.NOT_FOUND));
        } else {
            for (Perfil p : conta.getPerfil().getAmigos()) {
                if (p.getNome().equals(contaAmigo.getPerfil().getNome())) {
                    conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.OK));
                }
            }
        }
    }

    public void aceitarRecadosMural(ConexaoUtils conexaoUtils,
            RecadoMural recadoMural)
            throws IOException {
        ContaAbstrata conta = ContaDao.getInstance().
                buscarLogin(recadoMural.getLogin());
        if (conta == null) {
            conexaoUtils.enviar(new Protocolo(null,
                    null, StatusCodigo.NOT_FOUND));
        } else {
            if (!recadoMural.isTodos()) {
                conta.getPerfil().aceitarMural(recadoMural.getPosicao());
            } else {
                for (int i = 0; i < conta.getPerfil().getRecados().size(); i++) {
                    conta.getPerfil().aceitarMural(i);
                }
            }
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.OK));
        }
    }

    public void adicionarMatch(ConexaoUtils conexaoUtils,
            StringsDTO envioMatch) throws IOException {
        ContaDao cd = ContaDao.getInstance();
        ContaAbstrata c = cd.buscarLogin(envioMatch.getS1());
        ContaAbstrata cAmigo = cd.buscarLogin(envioMatch.getS2());
        if (c == null || cAmigo == null || !c.isAtivo() || !cAmigo.isAtivo()) {
            conexaoUtils.enviar(new Protocolo(null, null,
                    StatusCodigo.NOT_FOUND));
        } else {
            cAmigo.getPerfil().addMatch(c.getPerfil());
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.OK));
        }
    }

    public void desativarConta(ConexaoUtils conexaoUtils,
            StringsDTO stringsDTO) throws IOException {
        ContaAbstrata c = ContaDao.getInstance().buscarLogin(stringsDTO.getS1());
        ContaAbstrata cExcluida = ContaDao.getInstance()
                .buscarLogin(stringsDTO.getS2());
        if (c == null || cExcluida == null || !(c instanceof ContaAdmin)) {
            conexaoUtils.enviar(new Protocolo(null, null,
                    StatusCodigo.NOT_FOUND));
        } else {
            cExcluida.setAtivo(false);
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.OK));
        }
    }

    public void editarSenha(ConexaoUtils conexaoUtils, StringsDTO stringsDTO,
            String novaSenha) throws IOException {
        ContaAbstrata c = ContaDao.getInstance().buscarLogin(stringsDTO.getS1());
        ContaAbstrata cEditada = ContaDao.getInstance()
                .buscarLogin(stringsDTO.getS2());
        if (c == null || !(c instanceof ContaAdmin)) {
            conexaoUtils.enviar(new Protocolo(null, null,
                    StatusCodigo.NOT_FOUND));
        } else {
            cEditada.setSenha(novaSenha);
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.OK));
        }
    }

    public void editarUsuario(ConexaoUtils conexaoUtils, StringsDTO stringsDTO,
            String novoUsuario) throws IOException {
        ContaAbstrata c = ContaDao.getInstance().buscarLogin(stringsDTO.getS1());
        ContaAbstrata cEditada = ContaDao.getInstance()
                .buscarLogin(stringsDTO.getS2());
        if (c == null || !(c instanceof ContaAdmin)) {
            conexaoUtils.enviar(new Protocolo(null, null,
                    StatusCodigo.NOT_FOUND));
        } else {
            cEditada.getPerfil().setNome(novoUsuario);
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.OK));
        }
    }

    public void reativarConta(ConexaoUtils conexaoUtils,
            StringsDTO stringsDTO) throws IOException {
        ContaAbstrata c = ContaDao.getInstance().buscarLogin(stringsDTO.getS1());
        ContaAbstrata cExcluida = ContaDao.getInstance()
                .buscarLogin(stringsDTO.getS2());
        if (c == null || cExcluida == null || !(c instanceof ContaAdmin)) {
            conexaoUtils.enviar(new Protocolo(null, null,
                    StatusCodigo.NOT_FOUND));
        } else {
            cExcluida.setAtivo(true);
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.OK));
        }
    }
}
