package com.adoptaPet.controller;

import com.adoptaPet.domain.Adopcion;
import com.adoptaPet.service.AdopcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adopcion")
public class AdopcionController {

    @Autowired
    private AdopcionService adopcionService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var adopciones = adopcionService.getAdopciones();
        model.addAttribute("adopciones", adopciones);
        return "/adopcion/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Adopcion adopcion) {
        return "/adopcion/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(Adopcion adopcion) {
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