package dam.finalproyect.mygamecollection.model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "plataforma")
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean destacada;

    private String nombre;

    private String fabricante;

    private LocalDate fechaLanzamiento;

    public Plataforma() {}

    public Plataforma(Integer id, boolean destacada, String nombre, String fabricante, LocalDate fechaLanzamiento) {
        this.id = id;
        this.destacada = destacada;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDestacada() {
        return destacada;
    }

    public void setDestacada(boolean destacada) {
        this.destacada = destacada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }
}
