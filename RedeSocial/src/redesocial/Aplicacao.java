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
        System.out.printf("Bem vindo");
        boolean sair = false;
        Perfil perfil = conta.getPerfil();
        String usuario = "";
        String senha = "";

        do {
            System.out.println("perfil - convites " + perfil.getConvites().size());
            System.out.println("1 - Adicionar Amigos");
            System.out.println("2 - Aceitar solicitações de amizades");
            System.out.println("3 - Listar recados");
            System.out.println("4 - Enviar recado");
            System.out.println("5 - Listar usuarios");
            System.out.println("6 - Aceitar recado no mural");
            System.out.println("7 - Ver mural de outro usuario");
            System.out.println("8 - Adicionar um Match");
            System.out.println("10 - Deslogar");

            switch (in.nextInt()) {
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
                            System.out.Println("Você tem um recado secreto de %s, digite a senha para exibir o recado.");
                            String senhaRecado = in.next();
                            if (!recado.abrirMensagemSecreta(senha)){
                                System.out.println("Senha invalida");
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
                    if(amigo == null){
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
                    Conta [] contaPrint = gerenciamentoConta.listarContas();
                    System.out.println("===============");
                    for (int i = 0; i < contaPrint.length; i++) {
                        System.out.println(contaPrint[i]);
                    }
                    break;
                case 6:
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
                        int indiceAceitar = in.nextInt();
                        perfil.aceitarMural(i-1);
                    }
                    break;
                case 7:
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
                case 8:
                    System.out.print("Digite o login do usuário que você deseja dar um match:");
                    String logU = in.next();
                    Conta m = ContaDao.getInstance().buscarLogin(logU);
                    boolean deuMatch = true;

                    try {
                        m.getPerfil().addMatch(perfil);
                    }
                    catch (Exception e) {
                        System.out.println("Login Inválido.");
                        e.getMessage();
                    }
                    Thread innerThread = new Thread (new Runnable(){
                        @Override
                        public void run () {
                            deuMatch = m.getPerfil().verificaMatch(perfil);
                            if (deuMatch) {
                                System.out.println("MATCH com "+m.getPerfil());
                            }
                            else {
                                break;//não imprimir nenhuma menssagem
                            }
                        }
                    });
                   try {
                    innerThread.start();
                   }
                   catch (Exception e) {
                    e.printStackTrace();
                   }
                    break;
                case 10:
                    sair = true;
                    break;
            }
        } while (!sair);
    }
}
