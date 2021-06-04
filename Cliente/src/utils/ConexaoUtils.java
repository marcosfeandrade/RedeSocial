
package utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConexaoUtils {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket s;

    private static ConexaoUtils conexaoUtils;

    private ConexaoUtils() throws IOException {
        this.s = new Socket("localhost", 5555);
        out = new ObjectOutputStream(this.s.getOutputStream());
        in = new ObjectInputStream(this.s.getInputStream());

    }

    public static ConexaoUtils getInstance() throws IOException {
        if (conexaoUtils == null) {
            conexaoUtils = new ConexaoUtils();
        }
        return conexaoUtils;
    }

    public void fecharConexao() {
        try {
            in.close();
            out.close();
            s.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar conexão!");
        }
    }

    public void enviar(Protocolo obj) throws IOException {
        out.writeObject(obj);
        out.flush();

    }

    public Protocolo receber() throws IOException, ClassNotFoundException {
        Protocolo p = (Protocolo) in.readObject();
        return p;
    }
}
