package redesocial.models;

public class Recado {

    private String mensagem;
    private String autor;

    public Recado(String mensagem, String autor) {
        this.mensagem = mensagem;
        this.autor = autor;
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
}
