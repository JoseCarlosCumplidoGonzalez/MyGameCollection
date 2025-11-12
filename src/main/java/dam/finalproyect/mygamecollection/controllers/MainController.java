package dam.finalproyect.mygamecollection.controllers;

import java.util.List;

import dam.finalproyect.mygamecollection.model.Titulo;
import dam.finalproyect.mygamecollection.repositories.TituloRepository;
import dam.finalproyect.mygamecollection.services.PlataformaService;
import dam.finalproyect.mygamecollection.services.TituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    @Autowired
    private PlataformaService plataformaService;

    @Autowired
    private TituloService tituloService;

    @GetMapping("/")
    public String index(@RequestParam(name="idPlataforma", required=false) Long idPlataforma,
                        @RequestParam(name="palabraClave", required=false) String palabraClave, Model model) {
        List<Titulo> titulos;
        if(idPlataforma==null && palabraClave==null) {
            titulos = tituloService.obtenerTitulosAleatorios(TituloRepository.TITULOS_ALEATORIOS);
        }else if(idPlataforma!=null){
            titulos = tituloService.findAllByPlataforma(idPlataforma);
        }else {
            titulos = tituloService.findAllBySeacrh(palabraClave);
        }
        model.addAttribute("plataformas", plataformaService.findAll());
        model.addAttribute("titulos", titulos);

        if(palabraClave!=null) {
            return "busqueda";
        }else {
            return "index";
        }
    }

    @GetMapping("/titulo/{id}")
    public String showDetails(@PathVariable("id") Long id, Model model) {
        Titulo titulo = tituloService.findById(id);
        if(titulo!=null) {
            model.addAttribute(titulo);
            return "detail";
        }
        return "redirect:/";
    }
}