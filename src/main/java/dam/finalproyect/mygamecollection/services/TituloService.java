package dam.finalproyect.mygamecollection.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import dam.finalproyect.mygamecollection.model.Plataforma;
import dam.finalproyect.mygamecollection.model.Titulo;
import dam.finalproyect.mygamecollection.repositories.TituloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TituloService {
    @Autowired
    private TituloRepository tituloRepository;


    public List<Titulo> findAll() {
        return tituloRepository.findAll();
    }

    public List<Titulo> findAllBySeacrh(String clave) {
        List<Titulo> titulos;
        titulos = tituloRepository.findBySearch((clave));
        return titulos;
    }

    public List<Titulo> findAllByPlataforma(Plataforma plataforma) {
        return tituloRepository.findByPlataforma(plataforma);
    }

    public List<Titulo> findAllByPlataforma(Long plataformaId) {
        return tituloRepository.findByPlataformaId(plataformaId);
    }

    public Titulo findById(Long id) {
        return tituloRepository.findById(id).orElse(null);
    }

    public Titulo save(Titulo titulo) {
        return tituloRepository.save(titulo);
    }

    public Titulo delete(Titulo titulo) {
        Titulo result = findById(titulo.getId());
        tituloRepository.delete(result);
        return result;
    }

    public int numeroTitulosPlataforma(Plataforma plataforma) {
        return tituloRepository.findNumTitulosByPlataforma(plataforma);
    }

    public List<Titulo> obtenerTitulosAleatorios(int numero) {
        // Obtenemos los ids de todos los productos
        List<Long> listaIds = tituloRepository.obtenerIds();
        // Los desordenamos
        Collections.shuffle(listaIds);
        // Nos quedamos con los N primeros, con N = numero.
        listaIds = listaIds.stream().limit(numero).collect(Collectors.toList());
        // Buscamos los productos con esos IDs y devolvemos la lista
        return tituloRepository.findAllById(listaIds);

    }
}
