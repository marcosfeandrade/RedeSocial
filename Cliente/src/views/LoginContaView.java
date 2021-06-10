package views;

import java.io.IOException;
import java.util.Scanner;
import models.ContaAbstrata;
import models.ContaAdmin;
import models.ContaUsuario;
import models.ConexaoUtils;
import models.Protocolo;
import models.StatusCodigo;

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
        Protocolo p = ConexaoUtils.getInstance().enviarReceber(conta, "conta/logar");
        if (testarCondicoes(p) == true) {
            ContaAbstrata contaLogada = (ContaAbstrata) p.getObj();
            System.out.println("Login successfully");
            new MenuLogadoView(contaLogada).menuLogado();
        }
    }

    public boolean testarCondicoes(Protocolo p) {
        if (p == null) {
            System.out.println(StatusCodigo.INTERNAL_SERVER_ERRROR);
            return false;
        } else if (p.getStatusCodigo().equals(StatusCodigo.UNAUTHORIZED)
                || !(p.getObj() instanceof ContaAbstrata)) {
            System.out.println(p.getStatusCodigo().enumToString());
            return false;
        }
        return true;
    }
}
