package dam.finalproyect.mygamecollection.controllers;

import dam.finalproyect.mygamecollection.model.Genero;
import dam.finalproyect.mygamecollection.model.Titulo;
import dam.finalproyect.mygamecollection.services.GeneroService;
import dam.finalproyect.mygamecollection.services.PlataformaService;
import dam.finalproyect.mygamecollection.services.RegionService;
import dam.finalproyect.mygamecollection.services.TituloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/titulo")
public class TituloController {

    @Autowired private TituloService tituloService;
    @Autowired private PlataformaService plataformaService;
    @Autowired private RegionService regionService;
    @Autowired private GeneroService generoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("titulos", tituloService.findAll());
        return "admin/list-titulo";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("titulo", new Titulo());
        cargarListas(model);
        return "admin/form-titulo";
    }

    @PostMapping("/nuevo/submit")
    public String submitNuevoTitulo(
            @Valid Titulo titulo,
            BindingResult bindingResult,
            @RequestParam(name = "generoIds", required = false) List<Integer> generoIds,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("plataformas", plataformaService.findAll());
            model.addAttribute("regiones", regionService.findAll());
            model.addAttribute("generos", generoService.findAll());
            return "admin/form-titulo";
        }

        // Convertir ids -> entidades Genero
        Set<Genero> generosSeleccionados = new HashSet<>();
        if (generoIds != null && !generoIds.isEmpty()) {
            generosSeleccionados.addAll(generoService.findAllById(generoIds));
        }
        titulo.setGeneros(generosSeleccionados);

        tituloService.save(titulo);
        return "redirect:/admin/titulo/";
    }


    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Titulo titulo = tituloService.findById(id);
        if (titulo == null) return "redirect:/admin/titulo/";

        model.addAttribute("titulo", titulo);
        cargarListas(model);
        return "admin/form-titulo";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable("id") Long id) {
        Titulo titulo = tituloService.findById(id);
        if (titulo != null) tituloService.delete(titulo);
        return "redirect:/admin/titulo/";
    }

    private void cargarListas(Model model) {
        model.addAttribute("plataformas", plataformaService.findAll());
        model.addAttribute("regiones", regionService.findAll());
        model.addAttribute("generos", generoService.findAll());
    }
}
