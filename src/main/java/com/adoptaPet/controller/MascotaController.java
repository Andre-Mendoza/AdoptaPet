package com.adoptaPet.controller;

import com.adoptaPet.domain.Mascota;
import com.adoptaPet.service.MascotaService;
import com.adoptaPet.service.RefugioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/mascota")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private RefugioService refugioService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var mascotas = mascotaService.getMascotas();
        model.addAttribute("mascotas", mascotas);
        return "/mascota/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Mascota mascota, Model model) {
        model.addAttribute("refugios", refugioService.getRefugios());
        return "/mascota/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(Mascota mascota) {
        mascotaService.save(mascota);
        return "redirect:/mascota/listado";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable("id") Integer id, Model model) {
        Mascota mascota = mascotaService.getMascota(id);
        model.addAttribute("mascota", mascota);
        model.addAttribute("refugios", refugioService.getRefugios());
        return "/mascota/modifica";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        mascotaService.delete(id);
        return "redirect:/mascota/listado";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable("id") Integer id, Model model) {

        var mascota = mascotaService.getMascota(id);

        if (mascota == null) {
            return "redirect:/catalogo/listado";
        }

        model.addAttribute("mascota", mascota);

        return "/mascota/detalle";
    }
}
