package dam.finalproyect.mygamecollection.controllers;

import dam.finalproyect.mygamecollection.model.Plataforma;
import dam.finalproyect.mygamecollection.services.PlataformaService;
import dam.finalproyect.mygamecollection.services.TituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/plataforma")
public class PlataformaController {

    @Autowired
    private PlataformaService plataformaService;

    @Autowired
    private TituloService tituloService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("plataformas", plataformaService.findAll());
        return "admin/list-plataforma";
    }

    @GetMapping("/nueva")
    public String nuevaPlataforma(Model model) {
        model.addAttribute("plataforma", new Plataforma());
        return "admin/form-plataforma";
    }

    @PostMapping("/nueva/submit")
    public String submitNuevaPlataforma(@ModelAttribute("plataforma") Plataforma plataforma) {
        plataformaService.save(plataforma);
        return "redirect:/admin/plataforma/";
    }

    @GetMapping("/editar/{id}")
    public String editarPlataforma(@PathVariable("id") Integer id, Model model) {

        Plataforma plataforma = plataformaService.findById(id);

        if (plataforma != null) {
            model.addAttribute("plataforma", plataforma);
            return "admin/form-plataforma";
        } else {
            return "redirect:/admin/plataforma/";
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrarPlataforma(@PathVariable("id") Integer id) {
        Plataforma plataforma = plataformaService.findById(id);
        if (plataforma != null) {
            plataformaService.delete(plataforma);
        }
        return "redirect:/admin/plataforma/";
    }

}
