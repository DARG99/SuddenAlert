package android.example.dai2;

public class AlertSituation {
    private String severity;
    private String descricao;
    public AlertSituation(String severity, String descricao){
        this.severity = severity;
        this.descricao = descricao;
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
