
package models;

import java.io.Serializable;

public class RecadoMural implements Serializable{
    private static final long serialVersionUID = 1L;
    private String login;
    private int posicao;
    private boolean todos;

    public RecadoMural(String login, int posicao, boolean todos) {
        this.login = login;
        this.posicao = posicao;
        this.todos = todos;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public boolean isTodos() {
        return todos;
    }

    public void setTodos(boolean todos) {
        this.todos = todos;
    }
    
    
}
