package android.example.dai2;

public class Reclusos {

    private String nome;
    private int imagem; // vai armazenar o identificador do recurso
    private String ala;
    private String doencas;
    private String piso;

    public Reclusos(String nome, String ala, String doencas, String piso, int imagem) {
        this.nome = nome;
        this.ala = ala;
        this.doencas = doencas;
        this.piso = piso;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

   public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getAla() {
        return ala;
    }

    public void setAla(String ala) {
        this.ala = ala;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDoencas() {
        return doencas;
    }

    public void setDoencas(String doencas) {
        this.doencas = doencas;
    }
}
