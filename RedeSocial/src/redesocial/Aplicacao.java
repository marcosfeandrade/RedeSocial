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
            System.out.println("5 - Aceitar recado no mural");
            System.out.println("6 - Ver mural de outro usuario");
            System.out.println("7 - Adicionar um Match");
            System.out.println("0 - Deslogar");
            if (conta.getTipoContaEnum() == TipoContaEnum.ADMIN) {
                System.out.println("8 - Listar informações dos usuarios");
                System.out.println("9 - Excluir outras contas");
                System.out.println("10 - Editar outras contas");
            }
          
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
                        boolean exibirRecado = true;
                        Recado recado = recados.get(i); 
                        if (recado.ehSecreta()){
                            System.out.Printf("Você tem um recado secreto de %s, digite a senha para exibir o recado.\n", recado.getAutor());
                            String senhaRecado = in.next();
                            if (!recado.abrirMensagemSecreta(senha)){
                                System.out.println("Senha invalida");
                                exibirRecado = false;
                            }
                        }

                        if (exibirRecado){
                            System.out.println(recado);
                        }
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

                        System.out.println("Se sua menagem for secreta digite a senha, se não deixe em branco.");
                        String senhaRecado = in.next();
                        if (senhaRecado == ""){
                            System.out.println("Você quer enviar esse recado no mural?\n1 - Sim\n 2 - Não");
                            int mural = in.nextInt();
                            if (mural == 1) {
                                perfilAmigo.enviarRecadoMural(msg, perfil.getNome());
                            } else {
                                perfilAmigo.enviarRecado(msg, perfil.getNome());
                            }
                        } else {
                            perfilAmigo.enviarRecado(msg, perfil.getNome(), senhaRecado);
                        }
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
                case 9:
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
                case 10:
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
                case 5:
                    ArrayList<Recado> muralAceitar = perfil.getRecadosMuralParaAceitar();
                    System.out.println("Você quer aceitar todos os recados?\n1 - Sim\n2 - Não");
                    int aceitartudo = in.nextInt();
                    for (int i = 0; i < muralAceitar.size(); i++) {
                        if (aceitartudo) {
                            perfil.aceitarMural(i);
                        } else {
                            System.out.println("%d - %s", i+1, muralAceitar.get(i));
                        }
                    }
                    if (!aceitarTudo){
                        System.out.println("Quando recado você quer publicar no seu mural?");
                        int indiceAceitar = in.nextInt();
                        perfil.aceitarMural(indiceAceitar-1);
                    }
                    break;
                case 6:
                    System.out.println("Qual o login do usuario que você quer ver o mural?");
                    login = in.next();
                    Perfil perfilMural = conta.getPerfil(login);
                    if (perfilMural) {
                        ArralyList<Recado> exibirMural = perfilMural.getRecados();
                        for (int i = 0; i < exibirMural.size(); i++) { 
                            Recado rMural = exibirMural.get(i);
                            if (rMural.exibirNoMural()){
                                System.out.println(rMural);
                            }
                        }
                    } else {
                        System.out.println("Login invalido.");
                    }
                    break;
                case 7:
                    System.out.print("Digite o login do usuário que você deseja dar um match:");
                    String logU = in.next();
                    Conta m = ContaDao.getInstance().buscarLogin(logU);
                    if(m == null) {
                        System.out.println("Login não encontrado!");
                    }
                    else {
                        m.getPerfil().addMatch(perfil);
                    }
                    boolean deuMatch;
                    deuMatch = m.getPerfil().verificaMatch(perfil);
                    if(deuMatch == true) {
                        System.out.println("MATCH com "+m.getPerfil());
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
