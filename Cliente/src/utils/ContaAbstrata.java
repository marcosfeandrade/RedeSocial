
package utils;
import java.io.Serializable;

public abstract class ContaAbstrata implements Comparable<ContaAbstrata>, Serializable {
    private static final long serialVersionUID = 1L;
    private String login;
    private String senha;
    private boolean ativo;
    private String nomeUsuario;
    private Perfil perfil;
    
    public ContaAbstrata(String login, String senha, String nomeUsuario,
            Perfil perfil) {
        this.login = login;
        this.senha = senha;
        this.nomeUsuario = nomeUsuario;
        this.perfil = perfil;
        ativo = true;
    }
    
    public ContaAbstrata(String login, String senha, String nomeUsuario) {
        this.login = login;
        this.senha = senha;
        this.nomeUsuario = nomeUsuario;
    }
    
    public ContaAbstrata(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Perfil getPerfil() {
        return perfil;
        }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int compareTo(ContaAbstrata c) {
        return this.login.compareTo(c.login);
    }

    @Override
    public String toString() {
        return "Conta{" + "login=" + login + ", senha=" + senha + ", perfil=" +
                perfil + ", ativa=" + ativo + "}";
    }
}

