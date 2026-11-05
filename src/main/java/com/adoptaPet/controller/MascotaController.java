
package com.adoptaPet.controller;

import com.adoptaPet.domain.Mascota;
import com.adoptaPet.service.FirebaseStorageService;
import com.adoptaPet.service.MascotaService;
import com.adoptaPet.service.RefugioService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/mascota")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private RefugioService refugioService;

    @Autowired
    private FirebaseStorageService firebaseStorageService;

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
    public String guardar(
            Mascota mascota,
            @RequestParam(value = "imagen", required = false)
            MultipartFile imagen) throws IOException {

        // Si es una modificación, recuperamos la fotografía anterior.
        if (mascota.getIdMascota() != null) {
            Mascota existente = mascotaService.getMascota(
                    mascota.getIdMascota());

            if (existente != null) {
                mascota.setFotografia(existente.getFotografia());
            }
        }

        // Guardamos primero la mascota para obtener su ID.
        Mascota mascotaGuardada = mascotaService.save(mascota);

        // Si se seleccionó una fotografía nueva, la subimos a Firebase.
        if (imagen != null && !imagen.isEmpty()) {
            String urlImagen = firebaseStorageService.uploadImage(
                    imagen,
                    "mascotas",
                    mascotaGuardada.getIdMascota()
            );

            mascotaGuardada.setFotografia(urlImagen);
            mascotaService.save(mascotaGuardada);
        }

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
