package dam.finalproyect.mygamecollection.repositories;

import dam.finalproyect.mygamecollection.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    @Query("select c.nombre from Genero c")
    public List<Genero> obtenerGeneros();
}
