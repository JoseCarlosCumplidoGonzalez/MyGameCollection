package dam.finalproyect.mygamecollection.repositories;

import dam.finalproyect.mygamecollection.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    @Query("select c.nombre from Genero c")
    public List<Region> obtenerRegiones();
}
