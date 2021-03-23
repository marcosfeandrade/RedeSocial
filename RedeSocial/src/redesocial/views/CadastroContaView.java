package redesocial.views;

import java.util.Scanner;
import redesocial.controllers.GerenciamentoConta;

public class CadastroContaView {

    private Scanner in;
    private GerenciamentoConta gerenciamentoConta;

    public CadastroContaView(GerenciamentoConta gerenciamentoConta) {
        this.in = new Scanner(System.in);
        this.gerenciamentoConta = gerenciamentoConta;
    }

    public void cadastrarConta() {
        System.out.println("Nome de usuário: ");
        String nomeUsuario = in.nextLine();
        if(nomeUsuario == null){
            nomeUsuario = "convidado";
        }
        System.out.println("Login:");
        String login = in.nextLine();
        System.out.println("Senha:");
        String Senha = in.nextLine();
        gerenciamentoConta.cadastrar(login, Senha, nomeUsuario);
    }
}
