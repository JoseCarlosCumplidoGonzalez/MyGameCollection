package dam.finalproyect.mygamecollection.repositories;

import dam.finalproyect.mygamecollection.model.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlataformaRepository extends JpaRepository<Plataforma, Integer> {
    @Query("select c from Plataforma c where c.destacada = true")
    public List<Plataforma> findDestacadas();
}
