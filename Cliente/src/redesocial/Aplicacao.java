
package redesocial;
import utils.ConexaoUtils;
import views.ViewInicial;

public class Aplicacao {

    public static void main(String[] args) throws Exception {
        ConexaoUtils.getInstance();
        new ViewInicial().run();
        ConexaoUtils.getInstance().fecharConexao();
    }

}
