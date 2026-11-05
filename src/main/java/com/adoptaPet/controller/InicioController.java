
package com.adoptaPet.controller;

import com.adoptaPet.service.MascotaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    private final MascotaService mascotaService;

    public InicioController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping("/")
    public String inicio(Model model) {

        model.addAttribute(
                "mascotasDestacadas",
                mascotaService.getMascotasDisponibles()
        );

        return "index";
    }
}
