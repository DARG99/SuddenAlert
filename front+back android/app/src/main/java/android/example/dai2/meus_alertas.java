package android.example.dai2;

public class meus_alertas {
    private String nomeRec;
    private String severity;
    private String descricao;
    public meus_alertas(String nomeRec, String severity, String descricao){
        this.nomeRec = nomeRec;
        this.severity = severity;
        this.descricao = descricao;
    }

    public String getNomeRec() {
        return nomeRec;
    }

    public void setNomeRec(String nomeRec) {
        this.nomeRec = nomeRec;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
