package views;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.*;

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
            imprimeMenu();
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

    public void imprimeMenu() {
        ArrayList<Perfil> convites = new ArrayList<>();
        int qtdConvites = 0;
        try {
            int encerrou = 0;
            ConexaoUtils.getInstance().enviar(new Protocolo(conta.getLogin(), "conta/getConvites"));
            Protocolo p;
            do {
                p = ConexaoUtils.getInstance().receber();
                if (!validarProtocolo(p)) {
                    System.out.println("TEste");
                }
                if (p.getObj() == null) {
                    System.out.println(StatusCodigo.NOT_FOUND.enumToString());
                } else if (p.getObj() instanceof Integer) {
                    encerrou = -1;
                } else if (p.getObj() instanceof Perfil) {
                    convites.add((Perfil) p.getObj());
                } else {
                    System.out.println(StatusCodigo.NOT_FOUND.enumToString());
                }
            } while (encerrou == 0);
        } catch (IOException ex) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("--------------------");
        System.out.println("perfil - convites " + convites.size());
        System.out.println("--------------------");
        System.out.println("1 - Adicionar Amigos");
        System.out.println("2 - Aceitar solicitações de amizades");
        System.out.println("3 - Listar recados");
        System.out.println("4 - Enviar recado");
        System.out.println("5 - Aceitar recado no mural");
        System.out.println("6 - Ver mural de outro usuario");
        System.out.println("7 - Adicionar um Match");
        System.out.println("8 - Atualizar pagina");
        if (conta instanceof ContaAdmin) {
            System.out.println("9 - Excluir outras contas");
            System.out.println("10 - Editar outras contas");
        }
        System.out.println("0 - Deslogar");

    }

    public void adicionarAmigos(Scanner in) {
        System.out.println("Qual o login do usuario que você quer adicionar?");
        String login = in.next();
        Protocolo p = enviarReceber(new StringsDTO(conta.getLogin(), login),
                "conta/adicionarAmigos");
        boolean retorno = validarProtocolo(p);
        if (retorno) {
            System.out.println("Amigo adicionado");
        } else {
            System.out.println("Amigo não adicionado");
        }
    }

    public void aceitarSolicitacoesAmizade(Scanner in) {
        try {
            ConexaoUtils.getInstance().enviar(new Protocolo(conta.getLogin(),
                    "conta/getConvites"));
            ArrayList<Perfil> convites = validarConvites();
            System.out.println("Qual convite você quer aceitar?");
            for (int i = 0; i < convites.size(); i++) {
                System.out.printf("%d - %s\n", i + 1,
                        convites.get(i).getNome());
            }
            int aceitar = in.nextInt() - 1;
            String nome = convites.get(aceitar).getNome();
            Protocolo p = enviarReceber(new StringsDTO(conta.getLogin(), nome),
                    "conta/aceitarSolicitacaoAmigo");
            validarProtocolo(p);
        } catch (IOException ex) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public void listarRecados(Scanner in) {
        try {
            ConexaoUtils.getInstance().enviar(new Protocolo(conta,
                    "conta/listarRecados"));
            ArrayList<Recado> recados = validarRecados();
            for (Recado recado : recados) {
                if (!recado.ehSecreta()) {
                    System.out.println(recado);
                } else {
                    System.out.println("Você tem um recado secreto de "
                            + recado.getAutor()
                            + "digite a senha para exibir o recado.");
                    String senhaRecado = in.next();
                    if (!recado.abrirMensagemSecreta(senhaRecado)) {
                        System.out.println("Senha invalida");
                    } else {
                        System.out.println(recado);
                    }
                }
            }
        } catch (IOException e) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE,
                    null, e);
        }
    }

    public void enviarRecado(Scanner in) {
        System.out.println("Qual o login do usuario que você quer enviar a"
                + " mensagem?");
        String loginAmigo = in.nextLine();
        Protocolo p = enviarReceber(new StringsDTO(conta.getLogin(), loginAmigo),
                "conta/verificarAmigo");

        if (p == null) {
            System.out.println(StatusCodigo.INTERNAL_SERVER_ERRROR.enumToString());
        } else if (p.getStatusCodigo() == StatusCodigo.NOT_FOUND) {
            System.out.println(StatusCodigo.NOT_FOUND.enumToString());
        } else {
            System.out.println("Qual mensagem você quer enviar?");
            String msg = in.nextLine();
            System.out.println("Se sua menagem for secreta digite a senha, se não deixe em branco.");
            String senhaRecado = in.nextLine();
            System.out.println("Você quer enviar esse recado no mural?\n1 - Sim\n 2 - Não");
            int mural = in.nextInt();
            Protocolo p1 = enviarReceber(new EnvioRecado(conta.getLogin(), loginAmigo, senhaRecado, msg, mural),
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
            p = enviarReceber(new RecadoMural(conta.getLogin(), -1, true),
                    "conta/aceitarRecadoMural");
        } else {
            System.out.println("Qual recado você quer publicar no seu mural?");
            int indiceAceitar = in.nextInt() - 1;
            p = enviarReceber(new RecadoMural(conta.getLogin(), indiceAceitar, false),
                    "conta/aceitarRecadoMural");
        }
        if (!validarProtocolo(p)) {
            System.out.println("Protocolo inválido");
        } else if (p.getStatusCodigo() == StatusCodigo.NOT_FOUND) {
            System.out.println(StatusCodigo.NOT_FOUND.enumToString());
        } else {
            System.out.println("Enviado!");
        }
    }

    public ArrayList<Recado> validarRecados() {
        ArrayList<Recado> recados = new ArrayList<>();
        boolean encerrou = false;
        try {
            do {
                Protocolo p = ConexaoUtils.getInstance().receber();
                if (!validarProtocolo(p)) {
                    System.out.println("Inválido");
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
        } catch (IOException ex) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return recados;
        }
    }

    public ArrayList<Perfil> validarConvites() {
        ArrayList<Perfil> perfis = new ArrayList<>();
        boolean encerrou = false;
        try {
            do {
                Protocolo p;
                p = ConexaoUtils.getInstance().receber();
                if (!validarProtocolo(p)) {
                    System.out.println("Inválido");
                }
                if (p.getObj() == null) {
                    System.out.println(StatusCodigo.NOT_FOUND.enumToString());
                } else if (p.getObj() instanceof Integer) {
                    encerrou = true;
                } else if (p.getObj() instanceof Perfil) {
                    perfis.add((Perfil) p.getObj());
                } else {
                    System.out.println(StatusCodigo.NOT_FOUND.enumToString());
                }
            } while (encerrou == false);
        } catch (IOException ex) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return perfis;
        }
    }

    public void verOutroMural(Scanner in) {
        boolean encerrou = false;
        try {
            System.out.println("Qual o login do usuario que você quer ver o mural?");
            String loginMural = in.nextLine();
            ConexaoUtils.getInstance().enviar(new Protocolo(new StringsDTO(conta.getLogin(), loginMural), "conta/verOutroMural"));
            ArrayList<Recado> exibirMural = validarRecados();
            for (Recado lista : exibirMural) {
                System.out.println(lista);
            }
            Protocolo p = enviarReceber(new StringsDTO(conta.getLogin(),
                    loginMural), loginMural);
            validarProtocolo(p);
        } catch (IOException ex) {
            Logger.getLogger(MenuLogadoView.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public void adicionarMatch(Scanner in) {
        System.out.print("Digite o login do usuário que você deseja dar um match:");
        String loginMatch = in.nextLine();
        Protocolo p = enviarReceber(new StringsDTO(conta.getLogin(), loginMatch),
                "conta/adicionarMatch");
        boolean retorno = validarProtocolo(p);
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
            Protocolo p = enviarReceber(new StringsDTO(conta.getLogin(), login),
                    "conta/desativarConta");
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
            Protocolo p = enviarReceber(new StringsDTO(conta.getLogin(),
                    loginEditada), "conta/");
            if (p == null) {
                System.out.println(StatusCodigo.INTERNAL_SERVER_ERRROR);
            } else {
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
                    Protocolo p1 = enviarReceber(new StringsDTO(conta.getLogin(), loginEditada, novaSenha), "conta/editarSenha");
                } else if (num == 2) {
                    in.nextLine();
                    System.out.println("Novo usuário:");
                    String novoUsuario = in.nextLine();
                    Protocolo p2 = enviarReceber(new StringsDTO(conta.getLogin(), loginEditada, novoUsuario), "conta/editarUsuario");
                } else {
                    Protocolo p3 = enviarReceber(
                            new StringsDTO(conta.getLogin(), loginEditada), "conta/editarOutraConta");
                    System.out.println("Reativando conta...");
                }
            }
        } else {
            System.out.println("Você não tem acesso a esta opção");
        }
    }

    private boolean validarProtocolo(Protocolo p) {
        if (p == null) {
            System.out.println(StatusCodigo.INTERNAL_SERVER_ERRROR);
            return false;
        }
        System.out.println(p.getStatusCodigo().enumToString());
        return true;
    }

    private <T> Protocolo enviarReceber(T obj, String url) {
        Protocolo p = null;
        try {
            ConexaoUtils.getInstance().enviar(new Protocolo(obj, url));
            p = ConexaoUtils.getInstance().receber();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }
}
