
package com.adoptaPet.controller;

import com.adoptaPet.domain.Usuario;
import com.adoptaPet.service.UsuarioService;
import com.adoptaPet.service.RolService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    // Mostrar listado de usuarios
    @GetMapping("/listado")
    public String listado(Model model) {
        var usuarios = usuarioService.getUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "/usuario/listado";
    }

    // Mostrar formulario para crear usuario
    @GetMapping("/nuevo")
    public String nuevo(Usuario usuario, Model model) {
        model.addAttribute("roles", rolService.getRoles());
        return "/usuario/modifica";
    }

    // Guardar usuario desde administración
    @PostMapping("/guardar")
    public String guardar(Usuario usuario, HttpSession session) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioSesion == null
                || !"Administrador".equalsIgnoreCase(
                        usuarioSesion.getRol().getNombre())) {
            return "redirect:/usuario/login";
        }

        usuarioService.save(usuario);

        return "redirect:/usuario/listado";
    }

    // Modificar usuario desde administración
    @GetMapping("/modificar/{idUsuario}")
    public String modificar(
            @PathVariable Integer idUsuario,
            Model model,
            HttpSession session) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioSesion == null) {
            return "redirect:/usuario/login";
        }

        Usuario usuarioEditar =
                usuarioService.getUsuario(idUsuario);

        if (usuarioEditar == null) {
            return "redirect:/usuario/listado";
        }

        boolean esAdministrador =
                "Administrador".equalsIgnoreCase(
                        usuarioSesion.getRol().getNombre());

        boolean esPropietario =
                usuarioSesion.getIdUsuario().equals(idUsuario);

        if (!esAdministrador && !esPropietario) {
            return "redirect:/usuario/perfil";
        }

        model.addAttribute("usuario", usuarioEditar);
        model.addAttribute("roles", rolService.getRoles());

        return "/usuario/modifica";
    }

    // Eliminar usuario: solo administrador
    @GetMapping("/eliminar/{idUsuario}")
    public String eliminar(
            @PathVariable Integer idUsuario,
            HttpSession session) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioSesion == null
                || !"Administrador".equalsIgnoreCase(
                        usuarioSesion.getRol().getNombre())) {
            return "redirect:/usuario/login";
        }

        usuarioService.delete(idUsuario);

        return "redirect:/usuario/listado";
    }

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String registro(Usuario usuario) {
        return "/usuario/registro";
    }

    // Registrar nuevo usuario con validación de correo único
    @PostMapping("/registrar")
    public String registrar(Usuario usuario, Model model) {

        var usuarioExistente = usuarioService.getUsuarioPorCorreo(
                usuario.getCorreoElectronico()
        );

        if (usuarioExistente != null) {
            model.addAttribute("error",
                    "Este correo electrónico ya se encuentra registrado.");
            return "/usuario/registro";
        }

        var rolAdoptante = rolService.getRoles()
                .stream()
                .filter(rol -> "Adoptante".equalsIgnoreCase(rol.getNombre()))
                .findFirst()
                .orElse(null);

        if (rolAdoptante == null) {
            model.addAttribute("error",
                    "No se encontró el rol Adoptante.");
            return "/usuario/registro";
        }

        usuario.setRol(rolAdoptante);
        usuario.setEstado("Activo");

        usuarioService.save(usuario);

        return "redirect:/";
    }

    // Mostrar formulario de inicio de sesión
    @GetMapping("/login")
    public String login() {
        return "/usuario/login";
    }

    // Validar inicio de sesión
    @PostMapping("/login")
    public String iniciarSesion(
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam("contrasena") String contrasena,
            Model model,
            HttpSession session) {

        var usuario = usuarioService.getUsuarioPorCorreo(
                correoElectronico
        );

        if (usuario == null
                || !usuario.getContrasena().equals(contrasena)
                || !"Activo".equalsIgnoreCase(usuario.getEstado())) {

            model.addAttribute("error",
                    "Correo o contraseña incorrectos.");

            return "/usuario/login";
        }

        session.setAttribute("usuarioLogueado", usuario);
        model.addAttribute("usuario", usuario);

        return "/usuario/perfil";
    }

    // Consultar perfil
    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioSesion == null) {
            return "redirect:/usuario/login";
        }

        model.addAttribute("usuario", usuarioSesion);

        return "/usuario/perfil";
    }

    // Mostrar formulario para modificar el perfil propio
    @GetMapping("/perfil/modificar")
    public String modificarPerfil(
            HttpSession session,
            Model model) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioSesion == null) {
            return "redirect:/usuario/login";
        }

        Usuario usuarioActual =
                usuarioService.getUsuario(usuarioSesion.getIdUsuario());

        if (usuarioActual == null) {
            session.invalidate();
            return "redirect:/usuario/login";
        }

        model.addAttribute("usuario", usuarioActual);

        return "/usuario/perfil-modifica";
    }

    // Guardar cambios del perfil propio
    @PostMapping("/perfil/guardar")
    public String guardarPerfil(
            @RequestParam("nombre") String nombre,
            @RequestParam("correoElectronico") String correoElectronico,
            @RequestParam(value = "telefono", required = false) String telefono,
            @RequestParam(value = "direccion", required = false) String direccion,
            HttpSession session,
            Model model) {

        Usuario usuarioSesion =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioSesion == null) {
            return "redirect:/usuario/login";
        }

        Usuario usuarioActual =
                usuarioService.getUsuario(usuarioSesion.getIdUsuario());

        if (usuarioActual == null) {
            session.invalidate();
            return "redirect:/usuario/login";
        }

        Usuario usuarioExistente =
                usuarioService.getUsuarioPorCorreo(correoElectronico);

        if (usuarioExistente != null
                && !usuarioExistente.getIdUsuario()
                        .equals(usuarioActual.getIdUsuario())) {

            model.addAttribute("usuario", usuarioActual);
            model.addAttribute("error",
                    "Ese correo electrónico ya está registrado.");

            return "/usuario/perfil-modifica";
        }

        usuarioActual.setNombre(nombre);
        usuarioActual.setCorreoElectronico(correoElectronico);
        usuarioActual.setTelefono(telefono);
        usuarioActual.setDireccion(direccion);

        usuarioService.save(usuarioActual);

        session.setAttribute("usuarioLogueado", usuarioActual);

        return "redirect:/usuario/perfil";
    }

    // Cerrar sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}
