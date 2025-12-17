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

    // DTOs para exportación/importación (planos)
    public static class BackupDTO {
        public List<Plataforma> plataformas;
        public List<Region> regiones;
        public List<Genero> generos;
        public List<TituloDTO> titulos;
    }

    public static class TituloDTO {
        public Long id;
        public String nombre;
        public String sles;
        public String imagen;
        public String trailer;
        public java.time.LocalDate fechaLanzamiento;
        public String descripcion;
        public String observacion;

        public Integer plataformaId; // Plataforma.id es Integer en tu modelo
        public Integer regionId;     // ajusta si tu Region usa otro tipo
        public Set<Integer> generoIds = new HashSet<>();
    }

    @Override
    public String exportToJson() {
        try {
            BackupDTO backup = new BackupDTO();
            backup.plataformas = plataformaRepository.findAll();
            backup.regiones = regionRepository.findAll();
            backup.generos = generoRepository.findAll();

            // Pasar títulos a DTO plano para no tener ciclos
            backup.titulos = tituloRepository.findAll().stream().map(t -> {
                TituloDTO dto = new TituloDTO();
                dto.id = t.getId();
                dto.nombre = t.getNombre();
                dto.sles = t.getSles();
                dto.imagen = t.getImagen();
                dto.trailer = t.getTrailer();
                dto.fechaLanzamiento = t.getFechaLanzamiento();
                dto.descripcion = t.getDescripcion();
                dto.observacion = t.getObservacion();

                dto.plataformaId = (t.getPlataforma() != null) ? t.getPlataforma().getId() : null;
                dto.regionId = (t.getRegion() != null) ? t.getRegion().getId() : null;

                if (t.getGeneros() != null) {
                    dto.generoIds = t.getGeneros().stream()
                            .filter(Objects::nonNull)
                            .map(Genero::getId)
                            .collect(Collectors.toSet());
                }
                return dto;
            }).toList();

            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(backup);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo exportar: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void importFromJson(MultipartFile file) throws Exception {
        String json = new String(file.getBytes(), StandardCharsets.UTF_8);
        BackupDTO backup = mapper.readValue(json, BackupDTO.class);

        // 1) BORRAR en orden: primero relaciones (títulos) y luego catálogos
        // (si tienes FK / tablas intermedias, borrar títulos suele limpiar la intermedia)
        tituloRepository.deleteAll();
        generoRepository.deleteAll();
        regionRepository.deleteAll();
        plataformaRepository.deleteAll();

        // 2) INSERTAR catálogos
        // Nota: si tus IDs son autogenerados y NO quieres preservar IDs, elimina los ids en los objetos antes de save.
        // Si quieres preservar IDs, necesitas que tu estrategia lo permita.
        Map<Integer, Plataforma> plataformasById = new HashMap<>();
        for (Plataforma p : Optional.ofNullable(backup.plataformas).orElse(List.of())) {
            Plataforma saved = plataformaRepository.save(p);
            plataformasById.put(saved.getId(), saved);
        }

        Map<Integer, Region> regionesById = new HashMap<>();
        for (Region r : Optional.ofNullable(backup.regiones).orElse(List.of())) {
            Region saved = regionRepository.save(r);
            regionesById.put(saved.getId(), saved);
        }

        Map<Integer, Genero> generosById = new HashMap<>();
        for (Genero g : Optional.ofNullable(backup.generos).orElse(List.of())) {
            Genero saved = generoRepository.save(g);
            generosById.put(saved.getId(), saved);
        }

        // 3) INSERTAR títulos recreando relaciones
        for (TituloDTO dto : Optional.ofNullable(backup.titulos).orElse(List.of())) {
            Titulo t = new Titulo();
            t.setId(dto.id); // si da problemas por autogeneración, quita esta línea
            t.setNombre(dto.nombre);
            t.setSles(dto.sles);
            t.setImagen(dto.imagen);
            t.setTrailer(dto.trailer);
            t.setFechaLanzamiento(dto.fechaLanzamiento);
            t.setDescripcion(dto.descripcion);
            t.setObservacion(dto.observacion);

            if (dto.plataformaId != null) {
                t.setPlataforma(plataformasById.get(dto.plataformaId));
            }
            if (dto.regionId != null) {
                t.setRegion(regionesById.get(dto.regionId));
            }

            Set<Genero> gens = new HashSet<>();
            for (Integer gid : Optional.ofNullable(dto.generoIds).orElse(Set.of())) {
                Genero g = generosById.get(gid);
                if (g != null) gens.add(g);
            }
            t.setGeneros(gens);

            tituloRepository.save(t);
        }
    }
}
