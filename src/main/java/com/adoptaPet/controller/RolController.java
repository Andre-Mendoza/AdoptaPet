package com.adoptaPet.controller;

import com.adoptaPet.domain.Rol;
import com.adoptaPet.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var roles = rolService.getRoles();
        model.addAttribute("roles", roles);
        return "/rol/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Rol rol) {
        return "/rol/modifica";
    }

    @PostMapping("/guardar")
    public String guardar(Rol rol) {
        rolService.save(rol);
        return "redirect:/rol/listado";
    }

    @GetMapping("/modificar/{idRol}")
    public String modificar(Rol rol, Model model) {
        rol = rolService.getRol(rol.getIdRol());
        model.addAttribute("rol", rol);
        return "/rol/modifica";
    }

    @GetMapping("/eliminar/{idRol}")
    public String eliminar(Rol rol) {
        rolService.delete(rol.getIdRol());
        return "redirect:/rol/listado";
    }
}
