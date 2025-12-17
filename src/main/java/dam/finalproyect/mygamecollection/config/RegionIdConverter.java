package dam.finalproyect.mygamecollection.config;

import dam.finalproyect.mygamecollection.model.Region;
import dam.finalproyect.mygamecollection.services.RegionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegionIdConverter implements Converter<String, Region> {

    private final RegionService regionService;

    public RegionIdConverter(RegionService regionService) {
        this.regionService = regionService;
    }

    @Override
    public Region convert(String source) {
        if (source == null || source.isBlank()) return null;
        Integer id = Integer.valueOf(source);
        return regionService.findById(id);
    }
}
