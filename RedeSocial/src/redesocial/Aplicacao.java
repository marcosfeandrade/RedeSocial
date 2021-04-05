
package redesocial;

import java.util.Scanner;
import redesocial.controllers.GerenciamentoConta;
import redesocial.models.Conta;
import redesocial.models.Perfil;

public class Aplicacao {
    public void menuLogado(GerenciamentoConta gerenciamentoConta, Conta conta, Scanner in) {
        System.out.printf("Bem vindo %s\n", conta.getNomeUsuario());
        boolean sair = false;
        Perfil perfil = conta.getPerfil();

        do {
            System.out.println("1 - Adicionar Amigos");
            System.out.println("2 - Aceitar solicitações de amizades");
            System.out.println("3 - Listar recados");
            System.out.println("4 - Enviar recado");
            System.out.println("5 - Deslogar");

            switch(in.nextInt()){
                case 1:
                    System.out.println("Qual o nome do usuario que você quer adicionar?");
                    System usuario = in.next();
                    Perfil amigo = gerenciamentoConta.getConta(usuario).getPerfil();
                    amigo.enviarConvite(conta.getLogin());
                    break;
                case 2:
                    ArrayList<String> convites = perfil.getConvites();

                    System.out.println("Qual convite você quer aceitar?");
                    for (int i = 0; convites.sizes(); i++) {
                        System.out.printf("%d - %s\n", i+1, convites.get(i));
                        String aceitar = in.next();
                        perfil.aceitarConvite(convites.get(aceitar-1));
                    }
                    break;
                case 5:
                    sair = true;
                    break;
            }
        } while(!sair);
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
                    menuLogado(gerenciamentoConta, conta, in);
                    break;
                case 3:
                    sair = true;
                    break;
            }
        } while (!sair);
    }
    
}
