package dam.finalproyect.mygamecollection.controllers;

import dam.finalproyect.mygamecollection.services.ImportExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/importar-exportar")
public class ImportExportController {

    @Autowired
    private ImportExportService importExportService;

    @GetMapping
    public String view() {
        return "admin/importar-exportar";
    }

    // EXPORTAR -> descarga un JSON
    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportar() {
        String json = importExportService.exportToJson();

        String filename = "backup-mygamecollection-" + LocalDateTime.now().toString().replace(":", "-") + ".json";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json.getBytes(StandardCharsets.UTF_8));
    }

    // IMPORTAR -> sube JSON y restaura
    @PostMapping("/importar")
    public String importar(@RequestParam("file") MultipartFile file, Model model) {
        try {
            if (file.isEmpty()) {
                model.addAttribute("error", "No se ha seleccionado ningún archivo.");
                return "admin/importar-exportar";
            }
            importExportService.importFromJson(file);
            model.addAttribute("ok", "Importación realizada correctamente.");
        } catch (Exception e) {
            model.addAttribute("error", "Error importando: " + e.getMessage());
        }
        return "admin/importar-exportar";
    }
}
