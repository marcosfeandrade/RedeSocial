package redesocial;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import redesocial.controllers.GerenciamentoConta;
import redesocial.models.Conta;
import redesocial.models.Perfil;

import redesocial.models.Recado;
import redesocial.models.TipoContaEnum;
import redesocial.repository.ContaDao;

public class Aplicacao {

    public static void main(String[] args) {
        GerenciamentoConta gerenciamentoConta = new GerenciamentoConta();
        boolean sair = false;
        Conta conta = null;
        char resp;
        String login = "";
        String senha = "";

        do {
            System.out.println("Bem vindo!");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Login");
            System.out.println("3 - Sair");

            Scanner in = new Scanner(System.in);

            switch (in.nextInt()) {
                case 1:
                    System.out.println("Qual tipo de conta deseja cadastar: ");
                    System.out.println("1 - Conta de usuário\n"
                            + "2 - Conta de administrador");
                    resp = in.next().charAt(0);
                    while (resp != '1' && resp != '2') { //tratamento básico
                        System.out.println("Resposta inválida.");
                    }
                    System.out.println("Digite o login:");
                    login = in.next();
                    System.out.println("Digite a senha:");
                    senha = in.next();
                    System.out.println("Digite o nome do usuario:");
                    String usuario = in.next();
                    if (resp == '1') { // conta usuario
                        gerenciamentoConta.cadastrar(login, senha, usuario,
                                TipoContaEnum.USER);
                    } else { // conta admin
                        gerenciamentoConta.cadastrar(login, senha, usuario,
                                TipoContaEnum.ADMIN);
                    }
                    break;
                case 2:
                    System.out.println("Digite o login:");
                    login = in.next();
                    System.out.println("Digite a senha:");
                    senha = in.next();
                    conta = gerenciamentoConta.logar(login, senha);
                    if (conta != null && conta.isAtivo() == true) {
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

    public static void menuLogado(GerenciamentoConta gerenciamentoConta,
            Conta conta, Scanner in) {
        System.out.println("Bem vindo");
        boolean sair = false;
        Perfil perfil = conta.getPerfil();
        String usuario = "";
        String senha = "";

        do {
            System.out.println("perfil - convites "
                    + perfil.getConvites().size());
            System.out.println("1 - Adicionar Amigos");
            System.out.println("2 - Aceitar solicitações de amizades");
            System.out.println("3 - Listar recados");
            System.out.println("4 - Enviar recado");
            if (conta.getTipoContaEnum() == TipoContaEnum.ADMIN) {
                System.out.println("5 - Listar informações dos usuarios");
                System.out.println("6 - Excluir outras contas");
                System.out.println("7 - Editar outras contas");
            }
            System.out.println("0 - Deslogar");
            int respMenu = in.nextInt();
            switch (respMenu) {
                case 1:
                    System.out.println("Qual o login do usuario que você quer adicionar?");
                    usuario = in.next();
                    Conta c = ContaDao.getInstance().buscarLogin(usuario);
                    if (c == null) {
                        System.out.println("Usuário não encontrado.");
                    } else {
                        c.getPerfil().addConvite(perfil);
                    }
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
                    System.out.println("Qual o login do usuario que você quer enviar a mensagem?");
                    usuario = in.next();
                    Conta amigo = ContaDao.getInstance().buscarLogin(usuario);
                    if (amigo == null) {
                        System.out.println("Usuario inexistente.");
                    } else {
                        Perfil perfilAmigo = amigo.getPerfil();
                        System.out.println("Qual mensagem você quer enviar?");
                        String msg = in.next();
                        perfilAmigo.enviarRecado(msg, perfil.getNome());
                    }
                    break;
                case 5:
                    if (conta.getTipoContaEnum() == TipoContaEnum.ADMIN) {
                        List<Conta> contaPrintInfo = gerenciamentoConta.
                                listarContas();
                        System.out.println("===============");
                        for (Conta cList : contaPrintInfo) {
                            System.out.println(cList);
                        }
                    } else {
                        System.out.println("Você não tem acesso a esta opção");
                    }
                    break;
                case 6:
                    if (conta.getTipoContaEnum() == TipoContaEnum.ADMIN) {
                        in.nextLine();
                        System.out.println("Login da conta a ser excluída: ");
                        String login = in.nextLine();
                        if (!gerenciamentoConta.excluirConta(login)) {
                            System.out.println("Conta não encontrada.");
                        } else {
                            System.out.println("Conta removida!");
                        }

                    } else {
                        System.out.println("Você não tem acesso a esta opção");
                    }
                    break;
                case 7:
                    if (conta.getTipoContaEnum() == TipoContaEnum.ADMIN) {
                        in.nextLine();
                        System.out.println("Login da conta a ser editada: ");
                        String loginEditada = in.nextLine();
                        System.out.println("1 - Alterar senha\n"
                                + "2 - Alterar usuário\n"
                                + "3 - Reativar conta");
                        int num = in.nextInt();
                        while (num != 1 && num != 2 && num != 3) {
                            System.out.println("Resposta inválida."
                                    + " Insira novamente");
                            num = in.nextInt();
                        }
                        if (num == 1) {
                            in.nextLine();
                            System.out.println("Nova senha:");
                            String novaSenha = in.nextLine();
                            gerenciamentoConta.editarContaADM(loginEditada, num,
                                    novaSenha);
                        } else if (num == 2) {
                            in.nextLine();
                            System.out.println("Novo usuário:");
                            String novaUsuario = in.nextLine();
                            gerenciamentoConta.editarContaADM(loginEditada, num,
                                    novaUsuario);
                        } else {
                            System.out.println("Reativando conta...");
                            gerenciamentoConta.editarContaADM(loginEditada, num,
                                    null);
                        }
                    } else {
                        System.out.println("Você não tem acesso a esta opção");
                    }
                    break;
                case 0:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (!sair);
    }
}
