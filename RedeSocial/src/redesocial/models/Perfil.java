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
    private ArrayList<Perfil> convites;
    private ArrayList<Perfil> amigos;

    //CONSTRUTOR
    public Perfil (String nomePerfil, String cidade, String fone, int idade) {
        this.nomePerfil = nomePerfil;
        this.cidade = cidade;
        this.idade = idade;
        this.fone = fone;
        this.recados = new ArrayList<Recado>();
        this.convites = new ArrayList<Perfil>();
        this.amigos = new ArrayList<Perfil>();
    }
    //COnstrutor 2
    public Perfil (String nomePerfil) {
        this.nomePerfil = nomePerfil;
        this.recados = new ArrayList<Recado>();
        this.convites = new ArrayList<Perfil>();
        this.amigos = new ArrayList<Perfil>();
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
        return "Nome: " + this.nomePerfil;
    }
    @Override
    public int compareTo (Perfil p) {
        return this.getNome().compareTo(p.getNome());
    }

    public void enviarRecado(String msg, String autor){
        Recado recado = new Recado(msg, autor);
        recados.add(recado);
    }

    public void enviarRecado(String msg, String autor, String senha){
        Recado recado = new Recado(msg, autor, senha);
        recados.add(recado);
    }

    public ArrayList<Recado> getRecados() {
        return this.recados;
    }

    public void addConvite(Perfil solicitante) {
        this.convites.add(solicitante);
    }

    public ArrayList<Perfil> getConvites(){
        return this.convites;
    }

    public void aceitarConvite(Perfil perfil) {
        boolean achouConvite = false;
        int index = 0;
        for (int i=0; i < this.convites.size(); i++){
            if (this.convites.get(i).getNome().equals(perfil.getNome())) {
                achouConvite = true;
                index = i;
                break;
            }
        }

        if (achouConvite) {
            this.convites.remove(index);
            this.amigos.add(perfil);
            perfil.amigos.add(this);
        }
    }
    
}