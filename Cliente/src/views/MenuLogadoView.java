package views;

import models.ContaAbstrata;
import models.Protocolo;
import models.ConexaoUtils;
import models.StringsDTO;
import models.RecadoMural;
import models.Perfil;
import models.ContaAdmin;
import models.StatusCodigo;
import models.EnvioRecado;
import models.Recado;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuLogadoView {

    private ContaAbstrata conta;

    public MenuLogadoView(ContaAbstrata conta) {
        this.conta = conta;
    }

    public void menuLogado() {
        Scanner in = new Scanner(System.in);
        System.out.println("Bem vindo ao Unikut");
        int respMenu;
        do {
            imprimirMenu();
            respMenu = in.nextInt();
            switch (respMenu) {
                case 1:
                    adicionarAmigos(in);
                    break;
                case 2:
                    aceitarSolicitacoesAmizade(in);
                    break;
                case 3:
                    listarRecados(in);
                    break;
                case 4:
                    enviarRecado(in);
                    break;
                case 5:
                    aceitarRecadoMural(in);
                    break;
                case 6:
                    verOutroMural(in);
                    break;
                case 7:
                    adicionarMatch(in);
                    break;
                case 8:
                    break;
                case 9:
                    desativarOutraConta(in);
                    break;
                case 10:
                    editarOutraConta(in);
                    break;
                case 0:
                    System.out.println("Deslogando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (respMenu != 0);
    }

    public void imprimirMenu() {
        ArrayList<Perfil> convites = getConvites();
        System.out.println("--------------------");
        System.out.println("perfil - convites " + convites.size());
        System.out.println("--------------------");
        ArrayList<String> menu = new ArrayList<>();
        menu.add("1 - Adicionar Amigos");
        menu.add("2 - Aceitar solicitações de amizades");
        menu.add("3 - Listar recados");
        menu.add("4 - Enviar recado");
        menu.add("5 - Aceitar recado no mural");
        menu.add("6 - Ver mural de outro usuario");
        menu.add("7 - Adicionar um Match");
        menu.add("8 - Atualizar pagina");
        if (conta instanceof ContaAdmin) {
            menu.add("9 - Excluir outras contas");
            menu.add("10 - Editar outras contas");
        }
        menu.add("0 - Deslogar");

        menu.forEach((m) -> {
            System.out.println(m);
        });
    }

    public void adicionarAmigos(Scanner in) {
        System.out.println("Qual o login do usuario que você quer adicionar?");
        String login = in.next();
        Protocolo p = ConexaoUtils.getInstance().enviarReceber(new StringsDTO(conta.getLogin(), login), "conta/adicionarAmigos");
        boolean retorno = ConexaoUtils.getInstance().protocoloEhValido(p);
        if (retorno) {
            System.out.println("Convite enviado");
        } else {
            System.out.println("Convite não enviado");
        }
    }

    public void aceitarSolicitacoesAmizade(Scanner in) {
        ArrayList<Perfil> convites = getConvites();
        System.out.println("Qual convite você quer aceitar?");
        for (int i = 0; i < convites.size(); i++) {
            System.out.printf("%d - %s\n", i + 1,
                    convites.get(i).getNome());
        }
        int aceitar = in.nextInt() - 1;
        String nome = convites.get(aceitar).getNome();
        Protocolo p = ConexaoUtils.getInstance().enviarReceber(new StringsDTO(conta.getLogin(), nome), "conta/aceitarSolicitacaoAmigo");
        ConexaoUtils.getInstance().protocoloEhValido(p);
    }

    public void listarRecados(Scanner in) {
        ArrayList<Recado> recados = getRecados();
        for (Recado recado : recados) {
            if (!recado.ehSecreta()) {
                System.out.println(recado);
            } else {
                in.nextLine();
                System.out.println("Você tem um recado secreto de "
                        + recado.getAutor()
                        + " digite a senha para exibir o recado.");
                String senhaRecado = in.nextLine();
                if (!recado.ehMensagemSecreta(senhaRecado)) {
                    System.out.println("Senha invalida");
                } else {
                    System.out.println(recado);
                }
            }
        }
    }

    public void enviarRecado(Scanner in) {
        in.nextLine();
        System.out.println("Qual o login do usuario que você quer enviar a"
                + " mensagem?");
        String loginAmigo = in.nextLine();
        Protocolo p = ConexaoUtils.getInstance().enviarReceber(new StringsDTO(conta.getLogin(), loginAmigo), "conta/verificarAmigo");
        if (p == null) {
            System.out.println(StatusCodigo.INTERNAL_SERVER_ERRROR.enumToString());
        } else if (p.getStatusCodigo() == StatusCodigo.NOT_FOUND) {
            System.out.println(StatusCodigo.NOT_FOUND.enumToString());
        } else {
            System.out.println("Qual mensagem você quer enviar?");
            String msg = in.nextLine();
            System.out.println("Se sua menagem for secreta digite a senha, "
                    + "se não deixe em branco.");
            String senhaRecado = in.nextLine();
            System.out.println("Você quer enviar esse recado no mural?"
                    + "\n1 - Sim\n2 - Não");
            int mural = in.nextInt();
            Protocolo p1 = ConexaoUtils.getInstance().enviarReceber(new EnvioRecado(conta.getLogin(), loginAmigo, senhaRecado, msg, mural),
                    "conta/enviarRecado");
            if (p1 == null) {
                System.out.println(StatusCodigo.INTERNAL_SERVER_ERRROR.enumToString());
            } else if (p1.getStatusCodigo() == StatusCodigo.NOT_FOUND) {
                System.out.println(StatusCodigo.NOT_FOUND.enumToString());
            } else {
                System.out.println("Enviado!");
            }
        }
    }

    public void aceitarRecadoMural(Scanner in) {
        listarRecados(in);
        System.out.println("Você quer aceitar todos os recados recebidos?\n"
                + "1 - Sim\n2 - Não");
        int aceitarTudo = in.nextInt();
        Protocolo p;
        if (aceitarTudo == 1) {
            p = ConexaoUtils.getInstance().enviarReceber(new RecadoMural(conta.getLogin(), -1, true), "conta/aceitarRecadoMural");
        } else {
            System.out.println("Qual recado você quer publicar no seu mural?");
            int indiceAceitar = in.nextInt() - 1;
            p = ConexaoUtils.getInstance().enviarReceber(new RecadoMural(conta.getLogin(), indiceAceitar, false), "conta/aceitarRecadoMural");
        }
        if (!ConexaoUtils.getInstance().protocoloEhValido(p)) {
            System.out.println("Protocolo inválido");
        } else if (p.getStatusCodigo() == StatusCodigo.NOT_FOUND) {
            System.out.println(StatusCodigo.NOT_FOUND.enumToString());
        } else {
            System.out.println("Enviado!");
        }
    }

    public ArrayList<Recado> getRecados() {
        ArrayList<Recado> recados = new ArrayList<>();
        ConexaoUtils.getInstance().enviar(conta, "conta/getRecados");
        try {
            System.out.println("Processando...");
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean encerrou = false;
        do {
            Protocolo p;
            p = ConexaoUtils.getInstance().receber();
            if (!ConexaoUtils.getInstance().protocoloEhValido(p)) {
                System.out.println("Protocolo inválido");
            }
            if (p.getObj() == null) {
                System.out.println(StatusCodigo.NOT_FOUND.enumToString());
            } else if (p.getObj() instanceof Integer) {
                encerrou = true;
            } else if (p.getObj() instanceof Recado) {
                recados.add((Recado) p.getObj());
            } else {
                System.out.println(StatusCodigo.NOT_FOUND.enumToString());
            }
        } while (!encerrou);
        return recados;
    }

    public ArrayList<Perfil> getConvites() {
        ArrayList<Perfil> perfisConvites = new ArrayList<>();
        ConexaoUtils.getInstance().enviar(conta.getLogin(), "conta/getConvites");
        try {
            System.out.println("Processando...");
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean encerrou = false;
        do {
            Protocolo p;
            p = ConexaoUtils.getInstance().receber();
            if (!ConexaoUtils.getInstance().protocoloEhValido(p)) {
                System.out.println("Protocolo inválido");
            }
            if (p.getObj() == null) {
                System.out.println(StatusCodigo.NOT_FOUND.enumToString());
            } else if (p.getObj() instanceof Integer) {
                encerrou = true;
            } else if (p.getObj() instanceof Perfil) {
                perfisConvites.add((Perfil) p.getObj());
            } else {
                System.out.println(StatusCodigo.NOT_FOUND.enumToString());
            }
        } while (!encerrou);
        return perfisConvites;
    }

    public void verOutroMural(Scanner in) {
        in.nextLine();
        System.out.println("Qual o login do usuario que você quer ver o mural?");
        String loginMural = in.nextLine();
        ConexaoUtils.getInstance().enviar(new StringsDTO(conta.getLogin(),
                loginMural), "conta/verOutroMural");
        ArrayList<Recado> exibirMural = getRecados();
        for (Recado lista : exibirMural) {
            System.out.println(lista);
        }
        Protocolo p = ConexaoUtils.getInstance().enviarReceber(new StringsDTO(conta.getLogin(), loginMural), loginMural);
        ConexaoUtils.getInstance().protocoloEhValido(p);

    }

    public void adicionarMatch(Scanner in) {
        System.out.print("Digite o login do usuário que você deseja dar um match:");
        String loginMatch = in.nextLine();
        Protocolo p = ConexaoUtils.getInstance().enviarReceber(new StringsDTO(conta.getLogin(), loginMatch), "conta/adicionarMatch");
        boolean retorno = ConexaoUtils.getInstance().protocoloEhValido(p);
        if (retorno) {
            System.out.println("Match adicionado");
        } else if (p.getStatusCodigo() == StatusCodigo.BAD_REQUEST) {
            System.out.println(StatusCodigo.BAD_REQUEST.enumToString());
        } else if (p.getStatusCodigo() == StatusCodigo.NOT_FOUND) {
            System.out.println(StatusCodigo.NOT_FOUND.enumToString());
        } else {
            System.out.println("Match não adicionado");
        }
    }

    public void desativarOutraConta(Scanner in) {
        if (conta instanceof ContaAdmin) {
            in.nextLine();
            System.out.println("Login da conta a ser excluída: ");
            String login = in.nextLine();
            Protocolo p = ConexaoUtils.getInstance().enviarReceber(new StringsDTO(conta.getLogin(), login), "conta/desativarConta");
            if (p == null) {
                System.out.println(StatusCodigo.INTERNAL_SERVER_ERRROR);
            } else {
                System.out.println("Conta removida!");
            }
        } else {
            System.out.println("Você não tem acesso a esta opção");
        }
    }

    public void editarOutraConta(Scanner in) {
        if (conta instanceof ContaAdmin) {
            in.nextLine();
            System.out.println("Login da conta a ser editada: ");
            String loginEditada = in.nextLine();
            /*Protocolo p = ConexaoUtils.getInstance().enviarReceber(new StringsDTO(conta.getLogin(), loginEditada), null);
            if (p == null || p.getObj() == null) {
                System.out.println(StatusCodigo.INTERNAL_SERVER_ERRROR);
            } else {*/
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
                Protocolo p1 = ConexaoUtils.getInstance().enviarReceber(new StringsDTO(conta.getLogin(), loginEditada, novaSenha),
                        "conta/editarSenha");
            } else if (num == 2) {
                in.nextLine();
                System.out.println("Novo usuário:");
                String novoUsuario = in.nextLine();
                Protocolo p2 = ConexaoUtils.getInstance().enviarReceber(new StringsDTO(conta.getLogin(), loginEditada, novoUsuario),
                        "conta/editarUsuario");
            } else {
                Protocolo p3 = ConexaoUtils.getInstance().enviarReceber(
                        new StringsDTO(conta.getLogin(), loginEditada),
                        "conta/editarOutraConta");
                System.out.println("Reativando conta...");
                //}
            }
        } else {
            System.out.println("Você não tem acesso a esta opção");
        }
    }

    /*public void getAlgumaCoisa() {
        Protocolo p = ConexaoUtils.getInstance().enviarReceber(new Perfil("nome"),
                "conta/getAlgumaCoisa");
        if (p == null) {
            System.out.println("Protocolo nulo");
        } else if (p.getObj() == null) {
            System.out.println("Objeto nulo");
        } else if (!(p.getObj() instanceof ArrayList<?>)) {
            System.out.println("Instancia não é de arraylist");
        } else {
            ArrayList<Perfil> pA = (ArrayList<Perfil>) p.getObj();
            System.out.println(pA.get(0));
            System.out.println("Protocolo válido");
        }
    }*/
}
