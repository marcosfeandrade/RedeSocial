package models;

import java.io.Serializable;

public class ContaUsuario extends ContaAbstrata implements Serializable {

    private static final long serialVersionUID = 1L;

    public ContaUsuario(String login, String senha, String nomeUsuario,
            Perfil perfil) {
        super(login, senha, nomeUsuario, perfil);
    }
    
    public ContaUsuario(String login, String senha, String nomeUsuario) {
        super(login, senha, nomeUsuario);
    }

    public ContaUsuario(String login, String senha) {
        super(login, senha);
    }
}
