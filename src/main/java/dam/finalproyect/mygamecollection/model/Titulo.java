package dam.finalproyect.mygamecollection.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "titulo")
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String sles;

    private String imagen;

    private String trailer;

    @Column(name = "fecha_lanzamiento")
    private LocalDate fechaLanzamiento;


    private String descripcion;

    @Lob
    private String observacion;

    // FK: ON DELETE SET NULL -> relación opcional
    @ManyToOne
    @JoinColumn(name = "plataforma_id")
    private Plataforma plataforma;

    // FK: ON DELETE SET NULL -> relación opcional
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToMany
    @JoinTable(
            name = "titulo_genero",
            joinColumns = @JoinColumn(name = "titulo_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private Set<Genero> generos = new HashSet<>();

    public Titulo() {}

    public Titulo(Long id, String nombre, String sles, String imagen, String trailer, LocalDate fechaLanzamiento, String descripcion, String observaciones, Plataforma plataforma, Region region, Set<Genero> generos) {
        this.id = id;
        this.nombre = nombre;
        this.sles = sles;
        this.imagen = imagen;
        this.trailer = trailer;
        this.fechaLanzamiento = fechaLanzamiento;
        this.descripcion = descripcion;
        this.observacion = observaciones;
        this.plataforma = plataforma;
        this.region = region;
        this.generos = generos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSles() {
        return sles;
    }

    public void setSles(String sles) {
        this.sles = sles;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observaciones) {
        this.observacion = observaciones;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Set<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(Set<Genero> generos) {
        this.generos = generos;
    }
}
