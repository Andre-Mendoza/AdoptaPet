package com.adoptaPet;

import com.adoptaPet.domain.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AccesoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        String ruta = request.getRequestURI()
                .substring(request.getContextPath().length());

        HttpSession session = request.getSession(false);

        // Rutas públicas
        if (ruta.equals("/")
                || ruta.startsWith("/usuario/login")
                || ruta.startsWith("/usuario/registro")
                || ruta.startsWith("/usuario/registrar")
                || ruta.startsWith("/catalogo")
                || ruta.startsWith("/mascota/detalle")
                || ruta.startsWith("/css/")
                || ruta.startsWith("/js/")
                || ruta.startsWith("/img/")
                || ruta.startsWith("/fav/")
                || ruta.startsWith("/webjars/")
                || ruta.equals("/favicon.ico")) {

            return true;
        }

        // Validar sesión
        if (session == null
                || session.getAttribute("usuarioLogueado") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/usuario/login");
            return false;
        }

        Usuario usuario
                = (Usuario) session.getAttribute("usuarioLogueado");

        String rol = usuario.getRol().getNombre();

        // Administración de usuarios: solo Administrador
        // La modificación individual se valida en UsuarioController.
        if (ruta.startsWith("/usuario/listado")
                || ruta.startsWith("/usuario/nuevo")
                || ruta.startsWith("/usuario/guardar")
                || ruta.startsWith("/usuario/eliminar/")) {

            if (!"Administrador".equalsIgnoreCase(rol)) {
                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }

        // Gestión de refugios: solo Administrador
        if (ruta.startsWith("/refugio")
                && !"Administrador".equalsIgnoreCase(rol)) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        // Gestión de mascotas: Administrador y Colaborador
        if (ruta.startsWith("/mascota")
                && !"Administrador".equalsIgnoreCase(rol)
                && !"Colaborador".equalsIgnoreCase(rol)) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        //Gestión de solicitudes, adopciones y seguimientos
        /*if ((ruta.startsWith("/solicitud")
                || ruta.startsWith("/adopcion")
                || ruta.startsWith("/seguimiento"))
                && !"Administrador".equalsIgnoreCase(rol)
                && !"Colaborador".equalsIgnoreCase(rol)) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
         */
        // Gestión de solicitudes, adopciones y seguimientos / Modifique Isa
        if (ruta.startsWith("/solicitud")
                || ruta.startsWith("/adopcion")
                || ruta.startsWith("/seguimiento")) {

            // Las solicitudes pueden ser utilizadas por los tres roles.
            // Las demás rutas conservan sus restricciones.
            if (ruta.startsWith("/adopcion")
                    || ruta.startsWith("/seguimiento")) {

                if (!"Administrador".equalsIgnoreCase(rol)
                        && !"Colaborador".equalsIgnoreCase(rol)) {

                    response.sendError(
                            HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }
            }
        }

        return true;
    }
}
