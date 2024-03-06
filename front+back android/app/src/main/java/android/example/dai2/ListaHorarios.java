package android.example.dai2;

public class ListaHorarios {
    public String nomeHor, scanHor;
    public int id_hor;

    public ListaHorarios(String scanHor, String nomeHor, int id_hor) {
        this.scanHor = scanHor;
        this.nomeHor = nomeHor;
        this.id_hor = id_hor;
    }

    public String getScanHor() {
        return scanHor;
    }

    public void setScanHor(String scanHor) {
        this.scanHor = scanHor;
    }

    public String getNomeHor() {
        return nomeHor;
    }

    public void setNomeHor(String nomeHor) {
        this.nomeHor = nomeHor;
    }

    public int getId_hor() {
        return id_hor;
    }

    public void setId_hor(int id_hor) {
        this.id_hor = id_hor;
    }
}
