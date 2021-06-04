package redesocial;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;
import utils.Conexao;

public class Aplicacao {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ServerSocket serverSocket = new ServerSocket(5555);

        while (true) {
            System.out.println("Escutando");
            Socket s = serverSocket.accept();
            System.out.println("Aceito");
            Thread t = new Thread(new Conexao(s));
            t.start();
        }

    }

   
}
