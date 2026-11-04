package com.adoptaPet.controller;

import com.adoptaPet.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private MascotaService mascotaService;

    @GetMapping("/listado")
    public String listado(Model model) {

        var mascotasDisponibles = mascotaService.getMascotas()
                .stream()
                .filter(mascota -> "Disponible".equalsIgnoreCase(mascota.getEstado()))
                .collect(Collectors.toList());

        model.addAttribute("mascotasDisponibles", mascotasDisponibles);

        return "/catalogo/listado";
    }
}