
package com.adoptaPet.controller;

import com.adoptaPet.service.AdopcionService;
import com.adoptaPet.service.SolicitudService;
import com.adoptaPet.service.MascotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reporte")
public class ReporteController {

    private final AdopcionService adopcionService;
    private final SolicitudService solicitudService;
    private final MascotaService mascotaService;

    public ReporteController(
            AdopcionService adopcionService,
            SolicitudService solicitudService,
            MascotaService mascotaService) {

        this.adopcionService = adopcionService;
        this.solicitudService = solicitudService;
        this.mascotaService = mascotaService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {

        var adopciones = adopcionService.getAdopciones();
        var solicitudes = solicitudService.getSolicitudes();
        var mascotas = mascotaService.getMascotas();

        model.addAttribute("adopciones", adopciones);
        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("mascotas", mascotas);

        model.addAttribute("totalAdopciones", adopciones.size());
        model.addAttribute("totalSolicitudes", solicitudes.size());
        model.addAttribute("totalMascotas", mascotas.size());

        return "/reporte/listado";
    }
}
