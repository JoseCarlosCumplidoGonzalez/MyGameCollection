package dam.finalproyect.mygamecollection.services;

import dam.finalproyect.mygamecollection.model.Plataforma;
import dam.finalproyect.mygamecollection.repositories.PlataformaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PlataformaService {
    @Autowired
    private PlataformaRepository repositorio;

    public List<Plataforma> findAll() {
        return repositorio.findAll();
    }

    public List<Plataforma> findDestacadas() {
        return repositorio.findDestacadas();
    }

    public Plataforma save(Plataforma plataforma) {
        return repositorio.save(plataforma);
    }

    public Plataforma findById(Integer id) {
        return repositorio.findById(id).orElse(null);
    }

    public Plataforma delete(Plataforma plataforma) {
        Plataforma result = findById(plataforma.getId());
        repositorio.delete(result);
        return result;
    }
}