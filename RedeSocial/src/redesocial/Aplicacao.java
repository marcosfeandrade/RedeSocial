package redesocial;

import java.util.ArrayList;
import java.util.Scanner;
import redesocial.controllers.GerenciamentoConta;
import redesocial.models.Conta;
import redesocial.models.Perfil;

import redesocial.models.Recado;
import redesocial.repository.ContaDao;

public class Aplicacao {


    public static void main(String[] args) {
        GerenciamentoConta gerenciamentoConta = new GerenciamentoConta();
        boolean sair = false;
        Conta conta = null;
        String login = "";
        String senha = "";

        do {
            System.out.println("Bem vindo!");
            System.out.println("1- Cadastrar");
            System.out.println("2- Login");
            System.out.println("3- Sair");

            Scanner in = new Scanner(System.in);

            switch (in.nextInt()) {
                case 1:
                    System.out.println("Digite o login:");
                    login = in.next();
                    System.out.println("Digite a senha:");
                    senha = in.next();
                    System.out.println("Digite o nome do usuario:");
                    String usuario = in.next();
                    gerenciamentoConta.cadastrar(login, senha, usuario);
                    break;
                case 2:
                    System.out.println("Digite o login:");
                    login = in.next();
                    System.out.println("Digite a senha:");
                    senha = in.next();
                    conta = gerenciamentoConta.logar(login, senha);
                    if (conta != null) {
                        menuLogado(gerenciamentoConta, conta, in);
                    } else {
                        System.out.println("Não foi possível realizar o login.");
                    }
                    break;
                case 3:
                    sair = true;
                    break;
            }
        } while (!sair);
    }

    public static void menuLogado(GerenciamentoConta gerenciamentoConta, Conta conta, Scanner in) {
        System.out.printf("Bem vindo %s\n", conta.getPerfil().getNome());
        boolean sair = false;
        Perfil perfil = conta.getPerfil();
        String usuario = "";
        String senha = "";

        do {
            System.out.println("1 - Adicionar Amigos");
            System.out.println("2 - Aceitar solicitações de amizades");
            System.out.println("3 - Listar recados");
            System.out.println("4 - Enviar recado");
            System.out.println("5 - Deslogar");

            switch (in.nextInt()) {
                case 1:
                    System.out.println("Qual o nome do usuario que você quer adicionar?");
                    usuario = in.next();
                    Conta c = ContaDao.getInstance().buscarLogin(usuario);
                    perfil.enviarConvite(c.getPerfil());
                    break;
                case 2:
                    ArrayList<Perfil> convites = perfil.getConvites();

                    System.out.println("Qual convite você quer aceitar?");
                    for (int i = 0; i < convites.size(); i++) {
                        System.out.printf("%d - %s\n", i + 1, convites.get(i).getNome());
                    }
                    int aceitar = in.nextInt();
                    perfil.aceitarConvite(convites.get(aceitar - 1));
                    break;
                case 3:
                    ArrayList<Recado> recados = perfil.getRecados();
                    for (int i = 0; i < recados.size(); i++) {
                        System.out.println(recados.get(i));
                    }
                    break;
                case 4:
                    System.out.println("Qual o nome do usuario que você quer enviar a mensagem?");
                    usuario = in.next();

                    Perfil amigo = conta.getPerfil();

                    System.out.println("Qual mensagem você quer enviar?");
                    String msg = in.next();

                    amigo.enviarRecado(msg, perfil.getNome());

                    break;
                case 5:
                    sair = true;
                    break;
            }
        } while (!sair);
    }
}
