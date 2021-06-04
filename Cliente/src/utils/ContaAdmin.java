
package utils;

import java.io.Serializable;

public class ContaAdmin extends ContaAbstrata implements Serializable {
    private static final long serialVersionUID = 1L;
    public ContaAdmin(String login, String senha, String nomeUsuario,
            Perfil perfil) {
        super(login, senha, nomeUsuario, perfil);
    }
    public ContaAdmin(String login, String senha, String nomeUsuario) {
        super(login, senha, nomeUsuario);
    }
    public ContaAdmin(String login, String senha) {
        super(login, senha);
    }
}