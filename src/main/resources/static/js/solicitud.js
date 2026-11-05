
// Desarrollado por: Isabel
// Función: Controlar los pasos del formulario de solicitud

document.addEventListener('DOMContentLoaded', function () {
    console.log("SOLICITUD.JS SE ESTÁ EJECUTANDO");

    // CONTROLAR LOS PASOS DEL FORMULARIO

    const pasos = document.querySelectorAll('#pasosSolicitud .nav-link');

    const secciones = [
        document.getElementById('datosPersonales'),
        document.getElementById('informacionHogar'),
        document.getElementById('resumenSolicitud')
    ];

    function activarPaso(indice) {

        pasos.forEach(function (paso, i) {

            paso.classList.remove('active');
            paso.style.backgroundColor = '';
            paso.style.color = '#244C38';

            const numero = paso.querySelector('.badge');

            if (numero) {
                numero.className = 'badge rounded-pill text-bg-success';
            }

            if (i === indice) {

                paso.classList.add('active');
                paso.style.backgroundColor = '#244C38';
                paso.style.color = 'white';

                if (numero) {
                    numero.className = 'badge rounded-pill bg-light text-success';
                }
            }

        });

    }

    pasos.forEach(function (enlace, indice) {

        enlace.addEventListener('click', function () {
            activarPaso(indice);
        });

    });

    secciones.forEach(function (seccion, indice) {

        if (seccion) {

            seccion.addEventListener('click', function () {
                activarPaso(indice);
            });

            seccion.addEventListener('focusin', function () {
                activarPaso(indice);
            });

            seccion.addEventListener('change', function () {
                activarPaso(indice);
            });

        }

    });


    // BUSCAR Y FILTRAR SOLICITUDES

    const buscador = document.getElementById('buscarSolicitud');
    const filtroEstado = document.getElementById('filtroEstado');
    const tabla = document.getElementById('tablaSolicitudes');

    if (buscador && filtroEstado && tabla) {

        function filtrarSolicitudes() {

            const texto = buscador.value.toLowerCase().trim();
            const estado = filtroEstado.value.toLowerCase().trim();

            const filas = tabla.querySelectorAll('tr');

            filas.forEach(function (fila) {

                const contenido = fila.textContent.toLowerCase();

                // Obtener el estado de la solicitud
                const estadoFila = fila.dataset.estado
                        ? fila.dataset.estado.toLowerCase().trim()
                        : fila.cells[4].textContent.toLowerCase().trim();

                const coincideTexto = contenido.includes(texto);

                const coincideEstado = estado === '' ||
                        estadoFila === estado;

                fila.style.display =
                        coincideTexto && coincideEstado ? '' : 'none';

            });

        }

        buscador.addEventListener('input', filtrarSolicitudes);
        filtroEstado.addEventListener('change', filtrarSolicitudes);

    }



});
