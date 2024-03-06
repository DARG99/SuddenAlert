package android.example.dai2;

public class Documentos {
    private String nomeRe;
    private String nomeEn;
    private String data;


    public Documentos(String nomeRe, String nomeEn, String data) {
        this.nomeRe = nomeRe;
        this.nomeEn = nomeEn;
        this.data = data;
    }

    public String getNomeRe() {
        return nomeRe;
    }

    public void setNomeRe(String nomeRe) {
        this.nomeRe = nomeRe;
    }

    public String getNomeEn() {
        return nomeEn;
    }

    public void setNomeEn(String nomeEn) {
        this.nomeEn = nomeEn;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
