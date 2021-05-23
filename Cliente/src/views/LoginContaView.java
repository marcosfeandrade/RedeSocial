/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.util.Scanner;
import utils.ContaAbstrata;
import utils.ContaAdmin;
import utils.ContaUsuario;
import utils.ConexaoUtils;
import utils.Protocolo;
import utils.StatusCodigo;

public class LoginContaView {

    public void login() throws IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        System.out.println("Deseja logar como \n1- Usuário\n2- Administrador");
        int resp = in.nextInt();
        while (resp != 1 && resp != 2) {
            System.out.println("Resposta inválida.");
            System.out.println("logar como \n1- Usuário\n2- Administrador");
            resp = in.nextInt();
        }
        System.out.println("Digite o login:");
        String login = in.next();
        System.out.println("Digite a senha:");
        String senha = in.next();
        ContaAbstrata conta;
        if (resp == 1) {
            conta = new ContaUsuario(login, senha);
        } else {
            conta = new ContaAdmin(login, senha);
        }
        ConexaoUtils.getInstance().enviar(new Protocolo(conta, "conta/logar"));
        Protocolo p = ConexaoUtils.getInstance().receber();
        if(p == null){
            System.out.println(StatusCodigo.INTERNAL_SERVER_ERRROR.
                    enumToString());
        } else {
            if(p.getStatusCodigo().equals(StatusCodigo.UNAUTHORIZED)){
                System.out.println(StatusCodigo.UNAUTHORIZED.enumToString());
            } else {
                System.out.println("Login successfully");
                //conta.MenuLogadoView(conta);
            }
        }
    }

}
