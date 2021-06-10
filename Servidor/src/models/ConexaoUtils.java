/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConexaoUtils {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket s;

    public ConexaoUtils(Socket s) throws IOException {
        this.s = s;
        out = new ObjectOutputStream(this.s.getOutputStream());
        in = new ObjectInputStream(this.s.getInputStream());

    }

    public void fecharConexao() {
        try {
            in.close();
            out.close();
            s.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar conexão.");
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
