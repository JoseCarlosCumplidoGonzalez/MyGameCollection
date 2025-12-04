package dam.finalproyect.mygamecollection.controllers;

import dam.finalproyect.mygamecollection.model.Genero;
import dam.finalproyect.mygamecollection.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/genero")
public class GeneroController {

    @Autowired
    private GeneroService generoService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("generos", generoService.findAll());
        return "admin/list-genero";
    }

    @GetMapping("/nueva")
    public String nuevoGenero(Model model) {
        model.addAttribute("genero", new Genero());
        return "admin/form-genero";
    }


    @PostMapping("/nueva/submit")
    public String submitNuevoGenero(@ModelAttribute("genero") Genero genero, Model model) {

        generoService.save(genero);

        return "redirect:/admin/genero/";
    }

    @GetMapping("/editar/{id}")
    public String editarGenero(@PathVariable("id") Integer id, Model model) {

        Genero genero = generoService.findById(id);

        if (genero != null) {
            model.addAttribute("genero", genero);
            return "admin/form-genero";
        } else {
            return "redirect:/admin/genero/";
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrarGenero(@PathVariable("id") int id, Model model) {
        Genero genero = generoService.findById(id);
        if (genero != null) {
            generoService.delete(genero);
        }
        return "redirect:/admin/genero/";
    }
}
