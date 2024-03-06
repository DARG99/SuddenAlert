package android.example.dai2;

import java.util.Date;

public class Profile {
    private String scan, name, localation;
    private int type, points;
    private Date nascimento;

   /* public Profile(String scan, String name, String localation, int type, int points, Date nascimento) {
        this.scan = scan;
        this.name = name;
        this.localation = localation;
        this.type = type;
        this.points = points;
        this.nascimento = nascimento;
    }*/

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalation() {
        return localation;
    }

    public void setLocalation(String localation) {
        this.localation = localation;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
