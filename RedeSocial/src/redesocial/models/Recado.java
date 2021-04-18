package redesocial.models;

public class Recado {

    private String mensagem;
    private String autor;
    private String senha;
    private boolean secreta;
    private boolean mural;
    
    public Recado(String mensagem, String autor) {
        this.mensagem = mensagem;
        this.autor = autor;
    }

    public Recado(string mensagem, String autor, boolean mural){
        this.mensagem = mensagem;
        this.autor = autor;
        this.mural = mural;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public String getAutor() {
        return this.autor;
    }

    public String toString() {
        return "Mensagem: " + this.mensagem + "Enviada por: " + this.autor;
    }

    public boolean abrirMensagemSecreta(String senha) {
        return (this.senha == senha);
    }

    public boolean exibirNoMural(){
        return this.mural;
    }
}
