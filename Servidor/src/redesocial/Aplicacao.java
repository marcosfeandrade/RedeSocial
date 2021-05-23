package redesocial;

import java.io.IOException;
import java.net.Socket;
import controllers.ContaController;
import java.net.ServerSocket;
import utils.Conexao;
import utils.ContaAbstrata;
import utils.ConexaoUtils;
import utils.Protocolo;
import utils.StatusCodigo;

public class Aplicacao {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ServerSocket serverSocket = new ServerSocket(888);

        while (true) {
            System.out.println("Escutando");
            Socket s = serverSocket.accept();
            System.out.println("Aceito");
            Thread t = new Thread(new Conexao(s));
            t.start();
        }

    }

   
}
