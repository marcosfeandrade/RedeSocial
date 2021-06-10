package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConexaoUtils {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket s;

    private static ConexaoUtils conexaoUtils;

    private ConexaoUtils() {
        try {
            this.s = new Socket("localhost", 5555);
            out = new ObjectOutputStream(this.s.getOutputStream());
            in = new ObjectInputStream(this.s.getInputStream());
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public static ConexaoUtils getInstance() {
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

    public <T> void enviar(T obj, String url) {
        Protocolo p = new Protocolo(obj, url);
        try {
            out.writeObject(p);
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public Protocolo receber() {
        Protocolo p = null;
        try {
            p = (Protocolo) in.readObject();
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } finally {
            return p;
        }
    }

    public <T> Protocolo enviarReceber(T obj, String url) {
        enviar(obj, url);
        Protocolo p = receber();
        return p;
    }

    public boolean protocoloEhValido(Protocolo p) {
        if (p == null) {
            System.out.println(StatusCodigo.INTERNAL_SERVER_ERRROR.
                    enumToString());
            return false;
        }
        return true;
    }
}
