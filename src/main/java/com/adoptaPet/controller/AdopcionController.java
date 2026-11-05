
package com.adoptaPet.controller;

// Desarrollado por: Isabel

import com.adoptaPet.domain.Adopcion;
import com.adoptaPet.domain.Solicitud;
import com.adoptaPet.service.AdopcionService;
import com.adoptaPet.service.SolicitudService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/adopcion")
public class AdopcionController {

    @Autowired
    private AdopcionService adopcionService;

    @Autowired
    private SolicitudService solicitudService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var adopciones = adopcionService.getAdopciones();
        model.addAttribute("adopciones", adopciones);
        return "/adopcion/listado";
    }

    @GetMapping("/nuevo/{idSolicitud}")
    public String nuevo(@PathVariable Integer idSolicitud, Model model) {

        Solicitud solicitud = solicitudService.getSolicitud(idSolicitud);

        if (solicitud == null || !"Aprobada".equals(solicitud.getEstadoSolicitud())) {
            return "redirect:/solicitud/listado";
        }

        Adopcion adopcion = new Adopcion();
        adopcion.setSolicitud(solicitud);
        adopcion.setUsuario(solicitud.getUsuario());
        adopcion.setMascota(solicitud.getMascota());

        model.addAttribute("adopcion", adopcion);
        model.addAttribute("solicitud", solicitud);

        return "/adopcion/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam("solicitud.idSolicitud") Integer idSolicitud,
            @RequestParam("fechaAdopcion") LocalDate fechaAdopcion,
            @RequestParam("acta") String acta,
            @RequestParam("responsable") String responsable) {

        Solicitud solicitud = solicitudService.getSolicitud(idSolicitud);

        if (solicitud == null || !"Aprobada".equals(solicitud.getEstadoSolicitud())) {
            return "redirect:/solicitud/listado";
        }

        if (adopcionService.existePorSolicitud(idSolicitud)) {
            return "redirect:/solicitud/listado";
        }

        Adopcion adopcion = new Adopcion();
        adopcion.setSolicitud(solicitud);
        adopcion.setUsuario(solicitud.getUsuario());
        adopcion.setMascota(solicitud.getMascota());
        adopcion.setFechaAdopcion(fechaAdopcion);
        adopcion.setEstadoAdopcion("Finalizada");
        adopcion.setInformacionCierre(
                "Acta: " + acta + "; Responsable: " + responsable);

        adopcionService.save(adopcion);

        return "redirect:/adopcion/listado";
    }

    @GetMapping("/modificar/{idAdopcion}")
    public String modificar(Adopcion adopcion, Model model) {
        adopcion = adopcionService.getAdopcion(adopcion.getIdAdopcion());
        model.addAttribute("adopcion", adopcion);
        return "/adopcion/modifica";
    }

    @GetMapping("/eliminar/{idAdopcion}")
    public String eliminar(Adopcion adopcion) {
        adopcionService.delete(adopcion.getIdAdopcion());
        return "redirect:/adopcion/listado";
    }
}
