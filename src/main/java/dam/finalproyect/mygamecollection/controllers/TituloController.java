package dam.finalproyect.mygamecollection.controllers;

import dam.finalproyect.mygamecollection.model.Titulo;
import dam.finalproyect.mygamecollection.services.PlataformaService;
import dam.finalproyect.mygamecollection.services.TituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/admin/titulo")
public class TituloController {

    @Autowired
    private TituloService tituloService;

    @Autowired
    private PlataformaService plataformaService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("titulos", tituloService.findAll());
        return "admin/list-titulo";
    }

    @GetMapping("/nuevo")
    public String nuevaTitulo(Model model) {
        model.addAttribute("titulos",new Titulo());
        model.addAttribute("plataformas",this.plataformaService.findAll());
        return "admin/form-titulo";
    }

    @PostMapping("/nuevo/submit")
    public String submitNuevoTitulo(@Valid Titulo titulo, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("plataformas", plataformaService.findAll());
            return "admin/form-titulo";
        } else {
            tituloService.save(titulo);
            return "redirect:/admin/titulo/";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarTitulo(@PathVariable("id") Long id, Model model) {
        Titulo titulo = tituloService.findById(id);
        if (titulo != null) {
            model.addAttribute("titulo", titulo);
            model.addAttribute("plataformas", plataformaService.findAll());
            return "admin/form-titulo";
        } else {
            return "redirect:/admin/titulo/";
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrarTitulo(@PathVariable("id") Long id) {
        Titulo titulo = tituloService.findById(id);
        if (titulo != null) {
            tituloService.delete(titulo);
        }
        return "redirect:/admin/titulo/";
    }

}