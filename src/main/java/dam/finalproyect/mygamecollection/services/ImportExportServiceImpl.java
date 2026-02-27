package dam.finalproyect.mygamecollection.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dam.finalproyect.mygamecollection.model.*;
import dam.finalproyect.mygamecollection.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImportExportServiceImpl implements ImportExportService {

    @Autowired private PlataformaRepository plataformaRepository;
    @Autowired private RegionRepository regionRepository;
    @Autowired private GeneroRepository generoRepository;
    @Autowired private TituloRepository tituloRepository;

    private final ObjectMapper mapper;

    public ImportExportServiceImpl() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule()); // LocalDate
    }

    /**
     * Normaliza claves de texto para mapear por nombre:
     * - trim
     * - lowercase
     */
    private String key(String value) {
        return (value == null) ? null : value.trim().toLowerCase();
    }

    // ==========================
    // DTOs (export/import planos)
    // ==========================
    public static class BackupDTO {
        public List<PlataformaDTO> plataformas;
        public List<RegionDTO> regiones;
        public List<GeneroDTO> generos;
        public List<TituloDTO> titulos;
    }

    public static class PlataformaDTO {
        public String nombre;
        public boolean destacada;
        public String fabricante;
        public LocalDate fechaLanzamiento;
    }

    public static class RegionDTO {
        public String nombre;
    }

    public static class GeneroDTO {
        public String nombre;
    }

    public static class TituloDTO {
        // ⚠️ SIN ID
        public String nombre;
        public String sles;
        public String imagen;
        public String trailer;
        public LocalDate fechaLanzamiento;
        public String descripcion;
        public String observacion;

        // Relaciones por NOMBRE (no por ID)
        public String plataformaNombre;
        public String regionNombre;
        public Set<String> generos = new HashSet<>();
    }

    // ======================
    // EXPORT
    // ======================
    @Override
    public String exportToJson() {
        try {
            BackupDTO backup = new BackupDTO();

            // Plataformas -> DTO sin id
            backup.plataformas = plataformaRepository.findAll().stream().map(p -> {
                PlataformaDTO dto = new PlataformaDTO();
                dto.nombre = p.getNombre();
                dto.destacada = p.isDestacada();
                dto.fabricante = p.getFabricante();
                dto.fechaLanzamiento = p.getFechaLanzamiento();
                return dto;
            }).toList();

            // Regiones -> DTO sin id
            backup.regiones = regionRepository.findAll().stream().map(r -> {
                RegionDTO dto = new RegionDTO();
                dto.nombre = r.getNombre();
                return dto;
            }).toList();

            // Géneros -> DTO sin id
            backup.generos = generoRepository.findAll().stream().map(g -> {
                GeneroDTO dto = new GeneroDTO();
                dto.nombre = g.getNombre();
                return dto;
            }).toList();

            // Títulos -> DTO sin id y relaciones por nombre
            backup.titulos = tituloRepository.findAll().stream().map(t -> {
                TituloDTO dto = new TituloDTO();
                dto.nombre = t.getNombre();
                dto.sles = t.getSles();
                dto.imagen = t.getImagen();
                dto.trailer = t.getTrailer();
                dto.fechaLanzamiento = t.getFechaLanzamiento();
                dto.descripcion = t.getDescripcion();
                dto.observacion = t.getObservacion();

                dto.plataformaNombre = (t.getPlataforma() != null) ? t.getPlataforma().getNombre() : null;
                dto.regionNombre = (t.getRegion() != null) ? t.getRegion().getNombre() : null;

                if (t.getGeneros() != null) {
                    dto.generos = t.getGeneros().stream()
                            .filter(Objects::nonNull)
                            .map(Genero::getNombre)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toCollection(LinkedHashSet::new));
                }
                return dto;
            }).toList();

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(backup);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo exportar: " + e.getMessage(), e);
        }
    }

    // ======================
    // IMPORT
    // ======================
    @Override
    @Transactional
    public void importFromJson(MultipartFile file) throws Exception {
        String json = new String(file.getBytes(), StandardCharsets.UTF_8);
        BackupDTO backup = mapper.readValue(json, BackupDTO.class);

        // 1) BORRAR en orden
        // (borrar títulos limpia la tabla intermedia de many-to-many)
        tituloRepository.deleteAll();
        generoRepository.deleteAll();
        regionRepository.deleteAll();
        plataformaRepository.deleteAll();

        // 2) INSERTAR CATÁLOGOS (mapeando por nombre normalizado)
        Map<String, Plataforma> plataformasByNombre = new HashMap<>();
        for (PlataformaDTO dto : Optional.ofNullable(backup.plataformas).orElse(List.of())) {
            if (dto == null || dto.nombre == null || dto.nombre.isBlank()) continue;

            String k = key(dto.nombre);
            if (plataformasByNombre.containsKey(k)) continue; // evita duplicado

            Plataforma p = new Plataforma();
            p.setNombre(dto.nombre.trim());
            p.setFabricante(dto.fabricante);
            p.setFechaLanzamiento(dto.fechaLanzamiento);
            p.setDestacada(dto.destacada);

            Plataforma saved = plataformaRepository.save(p);
            plataformasByNombre.put(k, saved);
        }

        Map<String, Region> regionesByNombre = new HashMap<>();
        for (RegionDTO dto : Optional.ofNullable(backup.regiones).orElse(List.of())) {
            if (dto == null || dto.nombre == null || dto.nombre.isBlank()) continue;

            String k = key(dto.nombre);
            if (regionesByNombre.containsKey(k)) continue;

            Region r = new Region();
            r.setNombre(dto.nombre.trim());

            Region saved = regionRepository.save(r);
            regionesByNombre.put(k, saved);
        }

        Map<String, Genero> generosByNombre = new HashMap<>();
        for (GeneroDTO dto : Optional.ofNullable(backup.generos).orElse(List.of())) {
            if (dto == null || dto.nombre == null || dto.nombre.isBlank()) continue;

            String k = key(dto.nombre);
            if (generosByNombre.containsKey(k)) continue;

            Genero g = new Genero();
            g.setNombre(dto.nombre.trim());

            Genero saved = generoRepository.save(g);
            generosByNombre.put(k, saved);
        }

        // 3) INSERTAR TÍTULOS recreando relaciones por nombre
        for (TituloDTO dto : Optional.ofNullable(backup.titulos).orElse(List.of())) {
            if (dto == null) continue;

            Titulo t = new Titulo();
            t.setNombre(dto.nombre);
            t.setSles(dto.sles);
            t.setImagen(dto.imagen);
            t.setTrailer(dto.trailer);
            t.setFechaLanzamiento(dto.fechaLanzamiento);
            t.setDescripcion(dto.descripcion);
            t.setObservacion(dto.observacion);

            // Plataforma por nombre normalizado
            if (dto.plataformaNombre != null && !dto.plataformaNombre.isBlank()) {
                Plataforma p = plataformasByNombre.get(key(dto.plataformaNombre));
                t.setPlataforma(p);
            }

            // Región por nombre normalizado
            if (dto.regionNombre != null && !dto.regionNombre.isBlank()) {
                Region r = regionesByNombre.get(key(dto.regionNombre));
                t.setRegion(r);
            }

            // Géneros por nombre normalizado
            Set<Genero> gens = new HashSet<>();
            for (String nombreGenero : Optional.ofNullable(dto.generos).orElse(Set.of())) {
                if (nombreGenero == null || nombreGenero.isBlank()) continue;

                Genero g = generosByNombre.get(key(nombreGenero));
                if (g == null) {
                    // Si el género no venía en el bloque generos, lo creamos
                    g = new Genero();
                    g.setNombre(nombreGenero.trim());
                    g = generoRepository.save(g);
                    generosByNombre.put(key(g.getNombre()), g);
                }
                gens.add(g);
            }
            t.setGeneros(gens);

            tituloRepository.save(t);
        }
    }
}
