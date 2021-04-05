
package redesocial;

import java.util.Scanner;
import redesocial.controllers.GerenciamentoConta;
import redesocial.models.Conta;

public class Aplicacao {
    public int menuLogado(Conta conta) {
        System.out.printf("Bem vindo %s\n", conta.getNomeUsuario());
    }

    public static void main(String[] args) {
        GerenciamentoConta gerenciamentoConta = new GerenciamentoConta();
        boolean sair = false;
        Conta conta = null;

        do {
            System.out.println("Bem vindo!");
            System.out.println("1- Cadastrar");
            System.out.println("2- Login");
            System.out.println("3- Sair");

            Scanner in = new Scanner(System.in);

            switch(in.nextInt()) {
                case 1:
                    System.out.println("Digite o login:");
                    String login = in.next();
                    System.out.println("Digite a senha:");
                    String senha = in.next();
                    System.out.println("Digite o nome do usuario:");
                    String usuario = in.next();
                    gerenciamentoConta.cadastrar(login, senha, usuario);
                    break;
                case 2:
                    System.out.println("Digite o login:");
                    String login = in.next();
                    System.out.println("Digite a senha:");
                    String senha = in.next();
                    conta = gerenciamentoConta.logar(login, senha);
                    sair = menuLogado(conta);
                    break;
                case 3:
                    sair = true;
                    break;
            }
        } while (!sair);
    }
    
}
