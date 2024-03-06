/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jorge
 */
public class HistoricoBack {
    private int id_recluse;
    private String scan, motivo, acao, tipo, date, nome;
    
    public HistoricoBack (String nome, String motivo , String acao, String date, String tipo)
    {


        this.nome=nome;
        this.motivo = motivo;
        this.acao = acao;
        this.date = date;
        this.tipo = tipo;        
    }  
             
    public String getNome(){
        return nome;
    }
    public String getScan() {
        return scan;
    }

    public int getId_recluse() {
        return id_recluse;
    }
    
     public String getMotivo() {
        return motivo;
    }

    public String getAcao() {
        return acao;
    }

    public String getDate() {
        return date;
    }

    public String getTipo() {
        return tipo;
    }
}
