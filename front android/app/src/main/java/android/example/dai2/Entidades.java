package android.example.dai2;

public class Entidades {
    private String nome;
    private String email;
    private String funcao;
    private String pontos;


    public Entidades(String nome, String email, String funcao, String pontos) {
        this.nome = nome;
        this.email = email;
        this.funcao = funcao;
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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }
}

