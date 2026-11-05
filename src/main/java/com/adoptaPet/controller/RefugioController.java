
package com.adoptaPet.controller;

import com.adoptaPet.domain.Refugio;
import com.adoptaPet.service.RefugioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/refugio")
public class RefugioController {

    @Autowired
    private RefugioService refugioService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var refugios = refugioService.getRefugios();
        model.addAttribute("refugios", refugios);
        return "/refugio/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Refugio refugio) {
        return "/refugio/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(Refugio refugio) {
        refugioService.save(refugio);
        return "redirect:/refugio/listado";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable("id") Integer id, Model model) {
        Refugio refugio = refugioService.getRefugio(id);
        model.addAttribute("refugio", refugio);
        return "/refugio/modifica";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        refugioService.delete(id);
        return "redirect:/refugio/listado";
    }
}
