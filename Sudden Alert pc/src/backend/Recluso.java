/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author jorge
 */
public class Recluso {
    private int id, numero_recluso;
    private String nome, data_nascimento,  data_entrada, ala, piso, doenças, motivo;
    
    public Recluso (int numero_recluso, String nome, String data_nascimento, String data_entrada, String ala, String piso, String doenças, String motivo)
    {

        this.numero_recluso=numero_recluso;
        this.nome=nome;
        this.data_nascimento=data_nascimento;
        this.data_entrada=data_entrada;
        this.ala=ala;
        this.piso=piso;
        this.doenças=doenças;
        this.motivo=motivo;
    }   
    
    public int getnumero_recluso() {
        return numero_recluso;
    }
    
    public String getnome() {
        return nome;
    }
    
    public String getdata_nascimento() {
        return data_nascimento;
    }
    
    public String getdata_entrada() {
        return data_entrada;
    }
    
    public String getala() {
        return ala;
    }
    
    public String getpiso() {
        return piso;
    }
    
    public String getdoenças() {
        return doenças;
    }
    
        public String getMotivo() {
        return motivo;
    }
}
