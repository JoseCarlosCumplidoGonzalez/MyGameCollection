package dam.finalproyect.mygamecollection.backup.dto;

import java.util.ArrayList;
import java.util.List;

public class BackupData {

    private List<BackupPlataforma> plataformas = new ArrayList<>();
    private List<BackupRegion> regiones = new ArrayList<>();
    private List<BackupGenero> generos = new ArrayList<>();
    private List<BackupTitulo> titulos = new ArrayList<>();

    public List<BackupPlataforma> getPlataformas() { return plataformas; }
    public void setPlataformas(List<BackupPlataforma> plataformas) { this.plataformas = plataformas; }

    public List<BackupRegion> getRegiones() { return regiones; }
    public void setRegiones(List<BackupRegion> regiones) { this.regiones = regiones; }

    public List<BackupGenero> getGeneros() { return generos; }
    public void setGeneros(List<BackupGenero> generos) { this.generos = generos; }

    public List<BackupTitulo> getTitulos() { return titulos; }
    public void setTitulos(List<BackupTitulo> titulos) { this.titulos = titulos; }
}