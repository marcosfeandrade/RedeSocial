/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import controllers.ContaController;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Marcos
 */
public class Conexao implements Runnable {

    private ConexaoUtils conexaoUtils;

    public Conexao(Socket s) throws IOException {
        this.conexaoUtils = new ConexaoUtils(s);

    }

    private void validarRequisicao() throws IOException, ClassNotFoundException {
        Protocolo p = (Protocolo) conexaoUtils.receber();
        if (p == null) {
            conexaoUtils.enviar(new Protocolo(null,
                    null, StatusCodigo.INTERNAL_SERVER_ERRROR));
        }
        if (p.getUrl() == null || p.getUrl().isEmpty()) {
        } else {
            roteamento(p);
        }
    }

    private void roteamento(Protocolo p) throws IOException {
        if (isObjectNull(p)) {
            return;
        }
        if (p.getUrl().equals("conta/cadastrar")) {
            if (!(p.getObj() instanceof ContaAbstrata)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaAbstrata c = (ContaAbstrata) p.getObj();
                ContaController.getInstance().cadastrar(conexaoUtils, c);
            }
        } else if (p.getUrl().equals("conta/logar")) {
            if (!(p.getObj() instanceof ContaAbstrata)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().logar(conexaoUtils, (ContaAbstrata) p.getObj());
            }
        } else if(p.getUrl().equals("conta/adicionarAmigos")){
            
        }
    }

    private boolean isObjectNull(Protocolo p) throws IOException {
        if (p.getObj() == null) {
            System.out.println("Envio de objeto vazio.");
            //retorna via socket erro
            conexaoUtils.enviar(new Protocolo(null, null, StatusCodigo.BAD_REQUEST));
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        try {
            while (true) {
                validarRequisicao();
            }
        }catch(IOException e){
            conexaoUtils.fecharConexao();
        } catch(ClassNotFoundException e){
            conexaoUtils.fecharConexao();
        }
    }
}
