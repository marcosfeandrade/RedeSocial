package redesocial.models;

public class Conta implements Comparable<Conta> {

    private String login;
    private String senha;
    private String nomeUsuario;
    private Perfil perfil;
    
    public Conta(String login, String senha, String nomeUsuario) {
        this.login = login;
        this.senha = senha;
        this.nomeUsuario = nomeUsuario;
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

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public int compareTo(Conta c) {
        return this.login.compareTo(c.login);
    }
}
