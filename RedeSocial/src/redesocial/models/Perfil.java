
package redesocial.models;

public class Perfil implements Comparable <Perfil> {
    //atributos
    private String nomePerfil; 
    private String cidade;
    private String fone;
    private int idade;
    //CONSTRUTOR
    public Perfil (String nomePerfil, String cidade, String fone, int idade) {
        this.nomePerfil = nomePerfil;
        this.cidade = cidade;
        this.idade = idade;
        this.fone = fone;
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
}
