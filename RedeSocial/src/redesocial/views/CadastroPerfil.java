package redesocial.views;
import java.util.Scanner;
import redesocial.controllers.*;
import redesocial.models.*;

public class CadastroPerfil {
    //atributos
    Scanner s = new Scanner(System.in);
    private Perfil[] perfis;
    private int qtd;
    //construtor
    public CadastroPerfil (GerenciamentoConta gerenciando) {
        perfis = new Perfil [100];
    }

    public void cadastrarPerfil (String nomePerfil, String local, int idade, String fone) {
        if(buscarNomePerfil(nomePerfil) == null) {//nome não encontrado
            perfis[qtd] = new Perfil(nomePerfil, local, fone, idade);
            qtd++;
        }
        else {
            System.out.println("Nome de Usuário já está sendo ultilizado!");
        }
    }

    public Perfil buscarNomePerfil(String nomeProcurado) {
        int i;
        for (i = 0; i < qtd; i++) {
            if (perfis[i].getNome().compareTo(nomeProcurado) == 0) {
                return perfis[i];//retorna a posição
            }
        }
        return null;
    }

    public void alterarPerfil(String nome) {
        Perfil result;
        int i;
        String n, f, c;
        result = buscarNomePerfil(nome);
        if(result == null) {
            System.out.println("Nome de usuário não encontrado");
        }
        else {
            System.out.println("Alterando dados do perfil.");
            System.out.print("Digite seu novo nome de usuário:");
            n = s.nextLine();
            result.setNome(n);
            System.out.print("Digite seu novo telefone:");
            f = s.nextLine();
            result.setFone(f);
            System.out.print("Digite seu novo endereço:");
            c = s.nextLine();
            result.setNome(c);
            System.out.print("Digite sua nova idade:");
            i = s.nextInt();
            result.setIdade(i);
            }
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
        cadastrarPerfil(nome, local, idade, fone);
    }
    
}   