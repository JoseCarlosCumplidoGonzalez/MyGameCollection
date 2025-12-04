package dam.finalproyect.mygamecollection.services;

import dam.finalproyect.mygamecollection.model.Region;
import dam.finalproyect.mygamecollection.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    public Region save(Region region) {
        return regionRepository.save(region);
    }

    public Region findById(Integer id) {
        return regionRepository.findById(id).orElse(null);
    }

    public Region delete(Region region) {
        Region result = findById(region.getId());
        if (result != null) {
            regionRepository.delete(result);
        }
        return result;
    }
}
