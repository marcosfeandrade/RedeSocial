/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.util.Scanner;
import utils.*;
import utils.ConexaoUtils;
import utils.Protocolo;

public class CadastroContaView {

    public void cadastrarConta() {
        Scanner in = new Scanner(System.in);
        ContaAbstrata conta;

        System.out.println("Cadastro:");
        System.out.println("1 - Conta de Usuário;");
        System.out.println("2 - Conta de Admin;");
        System.out.println("Escolha o tipo de conta:");
        int tipoConta = in.nextInt();
        while (tipoConta != 1 && tipoConta != 2) {
            System.out.println("Escolha inválida.");
            System.out.println("1 - Conta de Usuário;");
            System.out.println("2 - Conta de Admin;");
            System.out.println("Escolha o tipo de conta:");
            tipoConta = in.nextInt();
        }
        in.nextLine();
        System.out.println("Login:");
        String login = in.nextLine();
        System.out.println("Senha:");
        String Senha = in.nextLine();
        System.out.println("Nome de usuário: ");
        String nomeUsuario = in.nextLine();
        if (nomeUsuario == null) {
            nomeUsuario = "Convidado";
        }
        Perfil perfil = new Perfil(nomeUsuario);
        if (tipoConta == 1) {
            conta = new ContaUsuario(login, Senha, nomeUsuario, perfil);
        } else {
            conta = new ContaAdmin(login, Senha, nomeUsuario, perfil);
        }
        try {
            ConexaoUtils.getInstance().enviar(new Protocolo(conta,
                    "conta/cadastrar"));
            Protocolo p = ConexaoUtils.getInstance().receber();
            if (p == null) {
                System.out.println("Erro na comunicação.");
            } else {
                System.out.println("Cadastro com "
                        + p.getStatusCodigo().toString());
            }
        } catch (IOException e) {
            System.out.println("Erro de conexão.");
        } catch (ClassNotFoundException e) {
            System.out.println("Aconteceu um erro");
        } finally {
            System.out.println("Voltando para pagina inicial.");
        }
    }
}
