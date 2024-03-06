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
    private int id;
    private String nome, ala, piso, doenças;
    
    public Recluso (int id, String nome, String ala, String piso, String doenças)
    {
        this.id=id;
        this.nome=nome;
        this.ala=ala;
        this.piso=piso;
        this.doenças=doenças;
    }   

    
    public int getid() {
        return id;
    }
    
    public String getnome() {
        return nome;
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
}
