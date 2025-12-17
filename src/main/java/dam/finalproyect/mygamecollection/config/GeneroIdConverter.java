package dam.finalproyect.mygamecollection.config;

import dam.finalproyect.mygamecollection.model.Genero;
import dam.finalproyect.mygamecollection.services.GeneroService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GeneroIdConverter implements Converter<String, Genero> {

    private final GeneroService generoService;

    public GeneroIdConverter(GeneroService generoService) {
        this.generoService = generoService;
    }

    @Override
    public Genero convert(String source) {
        if (source == null || source.isBlank()) return null;
        Integer id = Integer.valueOf(source);
        return generoService.findById(id);
    }
}
