/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author Diogo
 */
public class Report {
     private String titulo, gravidade, nomeP, emailP, data, relato;
    private int idReport;

    public Report(String titulo, String gravidade, String nomeP, String emailP, String data, int idReport, String relato) {
        this.titulo = titulo;
        this.gravidade = gravidade;
        this.nomeP = nomeP;
        this.emailP = emailP;
        this.data = data;
        this.idReport = idReport;
        this.relato= relato;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getGravidade() {
        return gravidade;
    }

    public String getNomeP() {
        return nomeP;
    }

    public String getEmailP() {
        return emailP;
    }

    public String getData() {
        return data;
    }

    public int getIdReport() {
        return idReport;
    }
    
    public String getRelato() {
        return relato;
    }
}
