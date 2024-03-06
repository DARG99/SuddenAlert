package android.example.dai2;

public class Historicos {
    private int id_hist;
    private String nomePessoa;
    private String data;
    private String acao;
    private String tipo;
    private String motivo;


    public Historicos(int id_hist, String nomePessoa, String data, String acao, String tipo, String motivo) {
        this.id_hist = id_hist;
        this.nomePessoa = nomePessoa;
        this.data = data;
        this.acao = acao;
        this.tipo = tipo;
        this.motivo = motivo;
    }

    public int getId_hist() {
        return id_hist;
    }

    public void setId_hist(int id_hist) {
        this.id_hist = id_hist;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
