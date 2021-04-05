
package redesocial;

import java.util.Scanner;
import redesocial.controllers.GerenciamentoConta;
import redesocial.models.Conta;

public class Aplicacao {
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
                    break;
                case 2:
                    String login = in.next();
                    String senha = in.next();
                    conta = gerenciamentoConta.logar(login, senha);
                    break;
                case 3:
                    sair = true;
                    break;
            }
        } while (!sair);
    }
    
}
