/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;

public class ContaAdmin extends ContaAbstrata implements Serializable {
    private static final long serialVersionUID = 1L;
    public ContaAdmin(String login, String senha, Perfil perfil,
            boolean isAdmin) {
        super(login, senha, perfil);
    }
}

