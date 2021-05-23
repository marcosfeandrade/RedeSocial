
package repository;

import java.util.ArrayList;
import java.util.List;
import utils.*;

public class ContaDao {

    private static ContaDao contaDao;
    private List<ContaAbstrata> contas;

    private ContaDao() {
        this.contas = new ArrayList<>();
    }

    public static ContaDao getInstance() {
        if (contaDao == null) {
            contaDao = new ContaDao();
        }
        return contaDao;
    }

    public List<ContaAbstrata> getContas() {
        return contas;
    }

    public void cadastrar(ContaAbstrata conta) {
        List<ContaAbstrata> contas = ContaDao.getInstance().getContas();
        contas.add(conta);
    }

    public ContaAbstrata buscarLogin(String login) {
        for(ContaAbstrata c: contas){
            if (c.getLogin().compareTo(login) == 0) {
                return c;
            }
        }
        return null;
    }

    public ContaAbstrata buscarSenha(String senha) {
        for(ContaAbstrata c: contas){
            if (c.getSenha().compareTo(senha) == 0) {
                return c;
            }
        }
        return null;
    }

    public List<ContaAbstrata> listarUsuarios() {
        return contas;
    }
    
    public void removerConta(ContaAbstrata c){
        this.contas.remove(c);
    }
    
   public int count(){
       return contas.size();
   } 
}
