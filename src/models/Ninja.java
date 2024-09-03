package models;

import java.util.ArrayList;
import java.util.List;

public class Ninja {
    private int id;
    private String nombre;
    private String rango;
    private String aldea;
    private List<Habilidad> habilidades;

    public Ninja(int id, String nombre, String rango, String aldea) {
        this.id = id;
        this.nombre = nombre;
        this.rango = rango;
        this.aldea = aldea;
        this.habilidades = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRango() {
        return rango;
    }

    public String getAldea() {
        return aldea;
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    public void addHabilidad(Habilidad habilidad) {
        habilidades.add(habilidad);
    }
}
