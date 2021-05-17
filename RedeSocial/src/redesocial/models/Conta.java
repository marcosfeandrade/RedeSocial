package redesocial.models;

public class Conta implements Comparable<Conta> {

    private String login;
    private String senha;
    private boolean ativo;
    private Perfil perfil;
    private TipoContaEnum tipoConta;
    
    public Conta(String login, String senha, Perfil perfil,
            TipoContaEnum tipoConta) {
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
        this.tipoConta = tipoConta;
        ativo = true;
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
    
    public TipoContaEnum getTipoContaEnum() {
       return tipoConta;
    }

    @Override
    public int compareTo(Conta c) {
        return this.login.compareTo(c.login);
    }

    @Override
    public String toString() {
        return "Conta{" + "login=" + login + ", senha=" + senha + ", perfil=" +
                perfil + ", ativa=" + ativo + "}";
    }
}
