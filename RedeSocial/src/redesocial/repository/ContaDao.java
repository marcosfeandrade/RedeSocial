package redesocial.repository;


import redesocial.models.Conta;
import redesocial.models.Perfil;

public class ContaDao {

    private static ContaDao contaDao;
    private Conta contas[];
    private static int qtd;

    private ContaDao() {
        this.qtd = 0;
        this.contas = new Conta[100];
    }

    public static ContaDao getInstance() {
        if (contaDao == null) {
            contaDao = new ContaDao();
        }
        return contaDao;
    }

    public Conta[] getContas() {
        return contas;
    }

    public void cadastrar(Conta conta) {
        Conta[] contas = ContaDao.getInstance().getContas();
        contas[qtd] = conta;
        ++qtd;
    }

    public Conta buscarLogin(String login) {
        for (int i = 0; i < qtd; i++) {
            if (ContaDao.getInstance().getContas()[i].getLogin().compareTo(login)
                    == 0) {
                return contas[i];
            }
        }
        return null;
    }

    public Conta buscarSenha(String senha) {
        for (int i = 0; i < qtd; i++) {
            if (ContaDao.getInstance().getContas()[i].getSenha().compareTo(senha)
                    == 0) {
                return contas[i];
            }
        }
        return null;
    }

    public Conta[] listarUsuarios() {
        return contas;
    }
}
