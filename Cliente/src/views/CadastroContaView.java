package views;

import models.ContaAbstrata;
import models.ContaUsuario;
import models.Perfil;
import models.ContaAdmin;
import java.util.Scanner;
import models.ConexaoUtils;
import models.Protocolo;

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
            conta = new ContaUsuario(login, Senha, nomeUsuario);
        } else {
            conta = new ContaAdmin(login, Senha, nomeUsuario);
        }
        Protocolo p = ConexaoUtils.getInstance().enviarReceber
        (conta, "conta/cadastrar");
        if (p == null) {
            System.out.println("Erro na comunicação.");
        } else {
            System.out.println("Cadastro com "
                    + p.getStatusCodigo().toString());
        }
        System.out.println("Voltando para pagina inicial.");
    }
}
