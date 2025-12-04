package dam.finalproyect.mygamecollection.controllers;

import dam.finalproyect.mygamecollection.model.Region;
import dam.finalproyect.mygamecollection.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("regiones", regionService.findAll());
        return "admin/list-region";
    }

    @GetMapping("/nueva")
    public String nuevaRegion(Model model) {
        model.addAttribute("region", new Region());
        return "admin/form-region";
    }

    @PostMapping("/nueva/submit")
    public String submitNuevaRegion(@ModelAttribute("region") Region region) {

        regionService.save(region);

        return "redirect:/admin/region/";
    }

    @GetMapping("/editar/{id}")
    public String editarRegion(@PathVariable("id") Integer id, Model model) {

        Region region = regionService.findById(id);

        if (region != null) {
            model.addAttribute("region", region);
            return "admin/form-region";
        } else {
            return "redirect:/admin/region/";
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrarRegion(@PathVariable("id") Integer id) {
        Region region = regionService.findById(id);
        if (region != null) {
            regionService.delete(region);
        }
        return "redirect:/admin/region/";
    }

}
