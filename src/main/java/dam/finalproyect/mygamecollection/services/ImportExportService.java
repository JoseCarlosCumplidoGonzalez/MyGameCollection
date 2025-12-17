package dam.finalproyect.mygamecollection.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImportExportService {
    String exportToJson();
    void importFromJson(MultipartFile file) throws Exception;
}
