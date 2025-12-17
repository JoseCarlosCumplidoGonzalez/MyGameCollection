package dam.finalproyect.mygamecollection.services;

import dam.finalproyect.mygamecollection.model.Genero;
import dam.finalproyect.mygamecollection.repositories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    public List<Genero> findAllByIds(List<Integer> ids) {
        return generoRepository.findAllById(ids);
    }

    public Genero save(Genero genero) {
        return generoRepository.save(genero);
    }

    public List<Genero> findAllById(List<Integer> ids) {
        return generoRepository.findAllById(ids);
    }

    public Genero findById(Integer id) {
        return generoRepository.findById(id).orElse(null);
    }

    public Genero delete(Genero genero) {
        Genero result = findById(genero.getId());
        if (result != null) {
            generoRepository.delete(result);
        }
        return result;
    }

}
