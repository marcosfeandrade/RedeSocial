
package models;

import controllers.ContaController;
import java.io.IOException;
import java.net.Socket;

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
        } else if (p.getUrl().equals("conta/adicionarAmigos")) {
            if (!(p.getObj() instanceof StringsDTO)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().adicionarAmigos(conexaoUtils,
                        (StringsDTO) p.getObj());
            }
        } else if (p.getUrl().equals("conta/aceitarSolicitacaoAmigo")) {
            if (!(p.getObj() instanceof StringsDTO)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().aceitarSolicitacaoAmizade(conexaoUtils,
                        (StringsDTO) p.getObj());
            }
        } else if (p.getUrl().equals("conta/getConvites")) {
            if (!(p.getObj() instanceof String)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().getConvites(conexaoUtils,
                        (String) p.getObj());
            }
        } else if (p.getUrl().equals("conta/getRecados")) {
            if (!(p.getObj() instanceof ContaAbstrata)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().getRecados(conexaoUtils,
                        (ContaAbstrata) p.getObj());
            }
        } else if (p.getUrl().equals("conta/enviarRecado")) {
            if (!(p.getObj() instanceof EnvioRecado)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().enviarRecado(conexaoUtils,
                        (EnvioRecado) p.getObj());
            }
        }  else if (p.getUrl().equals("conta/verificarAmigo")){
            if (!(p.getObj() instanceof StringsDTO)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().verificarAmigo(conexaoUtils,
                        (StringsDTO) p.getObj());
            }
        } else if (p.getUrl().equals("conta/aceitarRecadoMural")){
            if(!(p.getObj() instanceof RecadoMural)){
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().
                        aceitarRecadosMural(conexaoUtils,
                                (RecadoMural) p.getObj());
            }
        } else if (p.getUrl().equals("conta/adicionarMatch")) {
            if (!(p.getObj() instanceof StringsDTO)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().adicionarMatch(conexaoUtils,
                        (StringsDTO) p.getObj());
            }
        } else if(p.getUrl().equals("conta/desativarConta")){
            if (!(p.getObj() instanceof StringsDTO)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().desativarConta(conexaoUtils,
                        (StringsDTO) p.getObj());
            }
        } else if(p.getUrl().equals("conta/editarSenha")){
            if (!(p.getObj() instanceof StringsDTO)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().editarSenha(conexaoUtils,
                        (StringsDTO) p.getObj());
            }
        } else if(p.getUrl().equals("conta/editarUsuario")){
            if (!(p.getObj() instanceof StringsDTO)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().editarUsuario(conexaoUtils,
                        (StringsDTO) p.getObj());
            }
        } else if(p.getUrl().equals("conta/reativarConta")){
            if (!(p.getObj() instanceof StringsDTO)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().reativarConta(conexaoUtils,
                        (StringsDTO) p.getObj());
            }
        } else if(p.getUrl().equals("conta/verOutroMural")){
            if (!(p.getObj() instanceof String)) {
                conexaoUtils.enviar(new Protocolo(null,
                        null, StatusCodigo.BAD_REQUEST));
            } else {
                ContaController.getInstance().getMural(conexaoUtils,
                        (String) p.getObj());
            }
        } /*else if(p.getUrl().equals("conta/getAlgumaCoisa")){
                ContaController.getInstance().getAlgumaCoisa(conexaoUtils);
        }*/
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
        } catch (IOException e) {
            conexaoUtils.fecharConexao();
        } catch (ClassNotFoundException e) {
            conexaoUtils.fecharConexao();
        }
    }
}
