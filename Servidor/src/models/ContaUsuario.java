/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.io.Serializable;

public class ContaUsuario extends ContaAbstrata implements Serializable {
    private static final long serialVersionUID = 1L;
    public ContaUsuario(String login, String senha, String nomeUsuario,Perfil perfil) {
        super(login, senha, perfil);
    }
}
