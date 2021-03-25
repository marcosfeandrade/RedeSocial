package redesocial.views;
import java.util.Scanner;
import redesocial.controllers.*;

public class CadastroPerfil {
    //atributos
    Scanner s = new Scanner(System.in);
    private GerenciamentoConta gerenciando;
    //construtor
    public CadastroPerfil (GerenciamentoConta gerenciando) {
        this.gerenciando = gerenciando;
    }

    public void cadastrando () {
        String nome, fone, local;
        int idade;
        System.out.println("Cadastrando seu perfil na UNIKUT");
        System.out.print("Insira seu nome de usuário: ");
        nome = s.nextLine();
        System.out.print("Insira sua idade:");
        idade = s.nextInt();
        System.out.print("Insira onde você mora: ");
        local = s.nextLine();
        System.out.print("Digite seu fone: ");
        fone = s.nextLine();
        gerenciando.cadastrarPerfil(nome, local, idade, fone);
    }

    
}   