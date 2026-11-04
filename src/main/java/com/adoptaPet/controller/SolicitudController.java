package com.adoptaPet.controller;

import com.adoptaPet.domain.Solicitud;
import com.adoptaPet.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var solicitudes = solicitudService.getSolicitudes();
        model.addAttribute("solicitudes", solicitudes);
        return "/solicitud/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Solicitud solicitud) {
        return "/solicitud/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(Solicitud solicitud) {
        solicitudService.save(solicitud);
        return "redirect:/solicitud/listado";
    }

    @GetMapping("/modificar/{idSolicitud}")
    public String modificar(Solicitud solicitud, Model model) {
        solicitud = solicitudService.getSolicitud(solicitud.getIdSolicitud());
        model.addAttribute("solicitud", solicitud);
        return "/solicitud/modifica";
    }

    @GetMapping("/eliminar/{idSolicitud}")
    public String eliminar(Solicitud solicitud) {
        solicitudService.delete(solicitud.getIdSolicitud());
        return "redirect:/solicitud/listado";
    }
}