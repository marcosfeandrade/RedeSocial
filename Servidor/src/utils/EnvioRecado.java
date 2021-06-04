
package utils;

import java.io.Serializable;

public class EnvioRecado implements Serializable {
    private static final long serialVersionUID = 1L;
    private String login;
    private String loginAmigo;
    private String senha;
    private String mensagem;
    private int mural; // 1 - sim | 2 - não

    public EnvioRecado(String login, String loginAmigo, String senha,
            String mensagem, int mural) {
        this.login = login;
        this.loginAmigo = loginAmigo;
        this.senha = senha;
        this.mensagem = mensagem;
        this.mural = mural;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLoginAmigo() {
        return loginAmigo;
    }

    public void setLoginAmigo(String loginAmigo) {
        this.loginAmigo = loginAmigo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public int getMural() {
        return mural;
    }

    public void setMural(int mural) {
        this.mural = mural;
    }
    
    
}
