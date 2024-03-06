package android.example.dai2;

public class Documentos {
    private int id_report;
    private String nomeRel;
    private String nomeEn;
    private String data;
    private String gravidade;
    private String email;
    private String relatorio;


    public Documentos(int id_report,String nomeRel, String nomeEn, String data, String gravidade, String email, String relatorio) {
        this.id_report = id_report;
        this.nomeRel = nomeRel;
        this.nomeEn = nomeEn;
        this.data = data;
        this.gravidade = gravidade;
        this.email = email;
        this.relatorio= relatorio;
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public int getId_report() {
        return id_report;
    }

    public void setId_report(int id_report) {
        this.id_report = id_report;
    }

    public String getNomeRel() {
        return nomeRel;
    }
    public String getEmail(){return email;}

    public void setNomeRel(String nomeRel) {
        this.nomeRel = nomeRel;
    }
    public void setEmail(String email) {this.email = email;}

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

    public String getGravidade(){
        return gravidade;
    }
    public void setGravidade(String gravidade) {this.gravidade = gravidade;}


}