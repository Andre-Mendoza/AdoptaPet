package com.adoptaPet.controller;

import com.adoptaPet.domain.Seguimiento;
import com.adoptaPet.service.SeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seguimiento")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var seguimientos = seguimientoService.getSeguimientos();
        model.addAttribute("seguimientos", seguimientos);
        return "/seguimiento/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Seguimiento seguimiento) {
        return "/seguimiento/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(Seguimiento seguimiento) {
        seguimientoService.save(seguimiento);
        return "redirect:/seguimiento/listado";
    }

    @GetMapping("/modificar/{idSeguimiento}")
    public String modificar(Seguimiento seguimiento, Model model) {
        seguimiento = seguimientoService.getSeguimiento(seguimiento.getIdSeguimiento());
        model.addAttribute("seguimiento", seguimiento);
        return "/seguimiento/modifica";
    }

    @GetMapping("/eliminar/{idSeguimiento}")
    public String eliminar(Seguimiento seguimiento) {
        seguimientoService.delete(seguimiento.getIdSeguimiento());
        return "redirect:/seguimiento/listado";
    }
}