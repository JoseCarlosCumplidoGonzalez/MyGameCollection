package dam.finalproyect.mygamecollection.repositories;


import dam.finalproyect.mygamecollection.model.Plataforma;
import dam.finalproyect.mygamecollection.model.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TituloRepository extends JpaRepository<Titulo, Long> {
    public final int TITULOS_ALEATORIOS=8;
    public List<Titulo> findByPlataforma(Plataforma plataforma);

    @Query("select e.id from Titulo e")
    public List<Long> obtenerIds();

    @Query("select e from Titulo e where e.plataforma.id = ?1 order by e.nombre asc")
    public List<Titulo> findByPlataformaId(Long plataformaId);

    @Query("select count(e) from Titulo e where e.plataforma = ?1")
    public int findNumTitulosByPlataforma(Plataforma plataforma);

    @Query("SELECT e FROM Titulo e WHERE e.nombre LIKE %?1% OR e.observacion LIKE %?1% order by e.nombre asc")
    public List<Titulo> findBySearch(String palabraClave);
}
