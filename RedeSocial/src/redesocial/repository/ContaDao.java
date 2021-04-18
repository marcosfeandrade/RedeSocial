package redesocial.repository;


import java.util.ArrayList;
import java.util.List;
import redesocial.models.Conta;
import redesocial.models.Perfil;

public class ContaDao {

    private static ContaDao contaDao;
    private List<Conta> contas;

    private ContaDao() {
        this.contas = new ArrayList<>();
    }

    public static ContaDao getInstance() {
        if (contaDao == null) {
            contaDao = new ContaDao();
        }
        return contaDao;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void cadastrar(Conta conta) {
        List<Conta> contas = ContaDao.getInstance().getContas();
        contas.add(conta);
    }

    public Conta buscarLogin(String login) {
        for(Conta c: contas){
            if (c.getLogin().compareTo(login) == 0) {
                return c;
            }
        }
        return null;
    }

    public Conta buscarSenha(String senha) {
        for(Conta c: contas){
            if (c.getSenha().compareTo(senha) == 0) {
                return c;
            }
        }
        return null;
    }

    public List<Conta> listarUsuarios() {
        return contas;
    }
    
    public void removerConta(Conta c){
        this.contas.remove(c);
    }
    
   public int count(){
       return contas.size();
   } 
}
