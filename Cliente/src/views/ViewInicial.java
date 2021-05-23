/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.IOException;
import java.util.Scanner;

public class ViewInicial {

    public void run() throws IOException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        boolean sair = false;
        int resp;
        String login = "";
        String senha = "";

        do {
            System.out.println("Bem vindo!");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Login");
            System.out.println("3 - Sair");
            resp = in.nextInt();
            switch(resp){
                case 1:
                    new CadastroContaView().cadastrarConta();
                    break;
                case 2:
                    new LoginContaView().login();
                    break;
            }
        } while (resp != 3);
    }
}
