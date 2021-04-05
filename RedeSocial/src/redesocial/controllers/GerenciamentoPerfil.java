package redesocial.controllers;

import redesocial.models.Perfil;

public class GerenciamentoPerfil {

    public void alterarNome(String nome, Perfil perfil) {
        perfil.setNome(nome);
    }
    
    public void alterarTelefone(String fone, Perfil perfil) {
        perfil.setFone(fone);
    }
    
    public void alterarEndereco(String cidade, Perfil perfil) {
        perfil.setCidade(cidade);
    }
    
    public void alterarIdade(int idade, Perfil perfil) {
        perfil.setIdade(idade);
    }
}
