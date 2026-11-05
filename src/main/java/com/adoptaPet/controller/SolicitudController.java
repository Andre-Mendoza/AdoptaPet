package com.adoptaPet.controller;

import com.adoptaPet.domain.Usuario;
import jakarta.servlet.http.HttpSession;
import com.adoptaPet.domain.Solicitud;
import com.adoptaPet.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.adoptaPet.domain.Mascota;
import com.adoptaPet.service.MascotaService;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/solicitud")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    //permitirá consultar la mascota que el adoptante seleccionó en el catálogo.
    @Autowired
    private MascotaService mascotaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        var solicitudes = solicitudService.getSolicitudes();
        model.addAttribute("solicitudes", solicitudes);
        return "/solicitud/listado";
    }

    // Mostrar las solicitudes del usuario que inició sesión  
    @GetMapping("/mis-solicitudes")
    public String misSolicitudes(HttpSession session, Model model) {

        Usuario usuarioSesion
                = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioSesion == null) {
            return "redirect:/usuario/login";
        }

        var solicitudes = solicitudService.getSolicitudesPorUsuario(
                usuarioSesion.getIdUsuario()
        );

        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("usuario", usuarioSesion);

        return "/solicitud/mis-solicitudes";
    }

    /*@GetMapping("/nuevo")
    public String nuevo(Solicitud solicitud) {
        return "/solicitud/modifica";
    }
     */
// Mostrar el formulario de solicitud con los datos del usuario y la mascota seleccionada
    @GetMapping("/nuevo")
    public String nuevo(
            @RequestParam("idMascota") Integer idMascota,
            HttpSession session,
            Model model) {

        Usuario usuarioSesion
                = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioSesion == null) {
            return "redirect:/usuario/login";
        }

        Mascota mascota = mascotaService.getMascota(idMascota);

        if (mascota == null) {
            return "redirect:/catalogo/listado";
        }

        Solicitud solicitud = new Solicitud();
        solicitud.setMascota(mascota);

        model.addAttribute("solicitud", solicitud);
        model.addAttribute("usuario", usuarioSesion);
        model.addAttribute("mascota", mascota);

        return "/solicitud/formulario";
    }


    /*@PostMapping("/guardar")
    public String guardar(Solicitud solicitud) {
        solicitudService.save(solicitud);
        return "redirect:/solicitud/listado";
    }
     */
// Desarrollado por: Isabel
// Guardar una nueva solicitud de adopción
    @PostMapping("/guardar")
    public String guardar(
            Solicitud solicitud,
            @RequestParam("mascota.idMascota") Integer idMascota,
            HttpSession session) {

        // Obtener el usuario que inició sesión
        Usuario usuarioSesion
                = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioSesion == null) {
            return "redirect:/usuario/login";
        }

        // Buscar la mascota seleccionada
        Mascota mascota = mascotaService.getMascota(idMascota);

        if (mascota == null) {
            return "redirect:/catalogo/listado";
        }

        // Asignar los datos de la solicitud
        solicitud.setUsuario(usuarioSesion);
        solicitud.setMascota(mascota);
        solicitud.setFechaSolicitud(LocalDate.now());
        solicitud.setEstadoSolicitud("Pendiente");

// Guardar en la base de datos
        solicitudService.save(solicitud);

// Mostrar la confirmación de la solicitud registrada
        return "redirect:/solicitud/confirmacion/" + solicitud.getIdSolicitud();
    }

    // Mostrar la confirmación de la solicitud enviada
// Desarrollado por: Isabel
    @GetMapping("/confirmacion/{idSolicitud}")
    public String confirmacion(
            @PathVariable Integer idSolicitud,
            HttpSession session,
            Model model) {

        Usuario usuarioSesion
                = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioSesion == null) {
            return "redirect:/usuario/login";
        }

        Solicitud solicitud = solicitudService.getSolicitud(idSolicitud);

        if (solicitud == null
                || !solicitud.getUsuario().getIdUsuario()
                        .equals(usuarioSesion.getIdUsuario())) {
            return "redirect:/solicitud/mis-solicitudes";
        }

        model.addAttribute("solicitud", solicitud);

        return "/solicitud/confirmacion";
    }
// Mostrar los detalles de una solicitud 

    @GetMapping("/modificar/{idSolicitud}")
    public String modificar(Solicitud solicitud, Model model) {

        solicitud = solicitudService.getSolicitud(solicitud.getIdSolicitud());

        model.addAttribute("solicitud", solicitud);

        return "/solicitud/detalle";
    }

// Aprobar una solicitud de adopción
    @GetMapping("/aprobar/{idSolicitud}")
    public String aprobar(@PathVariable Integer idSolicitud) {

        Solicitud solicitud = solicitudService.getSolicitud(idSolicitud);

        if (solicitud != null) {
            solicitud.setEstadoSolicitud("Aprobada");
            solicitudService.save(solicitud);
        }

        return "redirect:/solicitud/listado";
    }

// Rechazar una solicitud de adopción
    @GetMapping("/rechazar/{idSolicitud}")
    public String rechazar(@PathVariable Integer idSolicitud) {

        Solicitud solicitud = solicitudService.getSolicitud(idSolicitud);

        if (solicitud != null) {
            solicitud.setEstadoSolicitud("Rechazada");
            solicitudService.save(solicitud);
        }

        return "redirect:/solicitud/listado";
    }

// Mostrar la pantalla de evaluación del administrador
    @GetMapping("/evaluar/{idSolicitud}")
    public String evaluar(@PathVariable Integer idSolicitud, Model model) {

        Solicitud solicitud = solicitudService.getSolicitud(idSolicitud);

        if (solicitud == null) {
            return "redirect:/solicitud/listado";
        }

        model.addAttribute("solicitud", solicitud);

        return "/solicitud/evaluacion";
    }

    @GetMapping("/eliminar/{idSolicitud}")
    public String eliminar(Solicitud solicitud) {
        solicitudService.delete(solicitud.getIdSolicitud());
        return "redirect:/solicitud/listado";
    }
}
