package android.example.dai2;

public class Entidades {
    private String nome;
    private String email;
    private String scan;
    private String pontos;


    public Entidades(String nome, String email, String scan, String pontos) {
        this.nome = nome;
        this.email = email;
        this.scan = scan;
        this.pontos = pontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }
}
