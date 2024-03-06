package android.example.dai2;

public class AlertSituation {
    private int id_alert;
    private String nome;
    private int numero;
    private String severity;
    private String descricao;

    public AlertSituation(int id_alert, String nome, int numero, String severity, String descricao) {
        this.id_alert = id_alert;
        this.nome = nome;
        this.numero = numero;
        this.severity = severity;
        this.descricao = descricao;
    }

    public int getId_alert() {
        return id_alert;
    }

    public void setId_alert(int id_alert) {
        this.id_alert = id_alert;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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
