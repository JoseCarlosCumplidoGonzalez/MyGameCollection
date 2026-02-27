package dam.finalproyect.mygamecollection.backup;

import dam.finalproyect.mygamecollection.backup.dto.*;
import dam.finalproyect.mygamecollection.model.*;

import java.util.stream.Collectors;

public class BackupMapper {

    public static BackupPlataforma toBackup(Plataforma p) {
        BackupPlataforma bp = new BackupPlataforma();
        bp.setNombre(p.getNombre());
        bp.setFabricante(p.getFabricante());
        bp.setFechaLanzamiento(p.getFechaLanzamiento());
        bp.setDestacada(p.isDestacada());
        return bp;
    }

    public static BackupRegion toBackup(Region r) {
        BackupRegion br = new BackupRegion();
        br.setNombre(r.getNombre());
        return br;
    }

    public static BackupGenero toBackup(Genero g) {
        BackupGenero bg = new BackupGenero();
        bg.setNombre(g.getNombre());
        return bg;
    }

    public static BackupTitulo toBackup(Titulo t) {
        BackupTitulo bt = new BackupTitulo();
        bt.setNombre(t.getNombre());
        bt.setSles(t.getSles());
        bt.setImagen(t.getImagen());
        bt.setTrailer(t.getTrailer());
        bt.setFechaLanzamiento(t.getFechaLanzamiento());
        bt.setDescripcion(t.getDescripcion());
        bt.setObservacion(t.getObservacion());

        bt.setPlataformaNombre(t.getPlataforma() != null ? t.getPlataforma().getNombre() : null);
        bt.setRegionNombre(t.getRegion() != null ? t.getRegion().getNombre() : null);

        bt.setGenerosNombres(
                t.getGeneros().stream()
                        .map(Genero::getNombre)
                        .collect(Collectors.toList())
        );

        return bt;
    }
}
