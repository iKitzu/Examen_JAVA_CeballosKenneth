package models;

public class Mision {
    private int id;
    private String descripcion;
    private String rango;
    private double recompensa;

    public Mision(int id, String descripcion, String rango, double recompensa) {
        this.id = id;
        this.descripcion = descripcion;
        this.rango = rango;
        this.recompensa = recompensa;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getRango() {
        return rango;
    }

    public double getRecompensa() {
        return recompensa;
    }
}
