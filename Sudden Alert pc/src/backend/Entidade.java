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
public class Entidade {
    private int id_type, points, idSchedule;
    private String scan, location, dataNascimento, email, nome;
    
    public Entidade (String scan, int id_type, String nome, String location, int points, String dataNascimento, String email, int idSchedule)
    {

        this.scan = scan;
        this.id_type = id_type;
        this.location = location;
        this.points = points;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.nome = nome;
        this.idSchedule = idSchedule;
        
    }   

    public int getIdSchedule() {
        return idSchedule;
    }

    public String getScan() {
        return scan;
    }

    public int getId_type() {
        return id_type;
    }
    
     public String getNome() {
        return nome;
    }

    public String getLocation() {
        return location;
    }

    public int getPoints() {
        return points;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }
    
}
