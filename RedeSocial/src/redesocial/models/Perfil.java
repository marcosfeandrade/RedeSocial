
package redesocial.models;

import java.util.ArrayList;
import redesocial.models.Recado;

public class Perfil implements Comparable <Perfil> {
    //atributos
    private String nomePerfil; 
    private String cidade;
    private String fone;
    private int idade;
    private ArrayList<Recado> recados;
    private ArrayList<String> convites;
    private ArrayList<String> amigos;

    //CONSTRUTOR
    public Perfil (String nomePerfil, String cidade, String fone, int idade) {
        this.nomePerfil = nomePerfil;
        this.cidade = cidade;
        this.idade = idade;
        this.fone = fone;
        this.recados = new ArrayList<Recado>();
        this.convites = new ArrayList<String>();
        this.amigos = new ArrayList<String>();
    }
    //COnstrutor 2
    public Perfil (String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }
    //metodos set e get
    public void setNome (String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }
    public String getNome () {
        return nomePerfil;
    }
    public void setCidade (String cidade) {
        this.cidade = cidade;
    }
    public String getCidade () {
        return cidade;
    }
    public void setIdade (int idade) {
        this.idade = idade;
    } 
    public int getIdade () {
        return idade;
    }
    public void setFone (String fone) {
        this.fone = fone;
    }
    public String getFone () {
        return fone;
    }
    public String toString() {
        return "Nome: "+this.nomePerfil+
        "\nIdade: "+this.idade+
        "\nCidade: "+this.cidade+
        "\nFone: "+this.fone;
    }
    @Override
    public int compareTo (Perfil p) {
        return this.getNome().compareTo(p.getNome());
    }

    public void enviarRecado(String msg, String autor){
        Recado recado = new Recado(msg, autor);
        recados.add(recado);
    }

    public ArrayList<Recado> getRecados() {
        return this.recados;
    }

    public void enviarConvite(String solicitante) {
        this.convites.add(solicitante);
    }

    public ArrayList<String> getConvites(){
        return this.convites;
    }

    public void aceitarConvite(Perfil perfil) {
        boolean achouConvite = false;
        int index = 0;
        String novoAmigo = perfil.getNome();

        for (int i=0; i < this.convites.size(); i++){
            if (this.convites.get(i) == novoAmigo) {
                achouConvite = true;
                index = i;
                break;
            }
        }

        if (achouConvite) {
            this.convites.remove(index);
            this.amigos.add(novoAmigo);
            perfil.amigos.add(this.nomePerfil);
        }
    }
}
