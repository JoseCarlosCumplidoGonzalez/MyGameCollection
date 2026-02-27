package dam.finalproyect.mygamecollection.backup.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BackupTitulo {

    private String nombre;
    private String sles;
    private String imagen;
    private String trailer;
    private LocalDate fechaLanzamiento;
    private String descripcion;
    private String observacion;

    // Relaciones por nombre (sin ids)
    private String plataformaNombre;
    private String regionNombre;
    private List<String> generosNombres = new ArrayList<>();

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSles() { return sles; }
    public void setSles(String sles) { this.sles = sles; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getTrailer() { return trailer; }
    public void setTrailer(String trailer) { this.trailer = trailer; }

    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
    public void setFechaLanzamiento(LocalDate fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }

    public String getPlataformaNombre() { return plataformaNombre; }
    public void setPlataformaNombre(String plataformaNombre) { this.plataformaNombre = plataformaNombre; }

    public String getRegionNombre() { return regionNombre; }
    public void setRegionNombre(String regionNombre) { this.regionNombre = regionNombre; }

    public List<String> getGenerosNombres() { return generosNombres; }
    public void setGenerosNombres(List<String> generosNombres) { this.generosNombres = generosNombres; }
}
