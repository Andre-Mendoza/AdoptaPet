
package com.adoptaPet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "solicitud")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Integer idSolicitud;

    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    @Column(name = "estado_solicitud", nullable = false, length = 30)
    private String estadoSolicitud;

    @Column(name = "informacion_hogar", length = 255)
    private String informacionHogar;
    
        /*
   
     * Función: Almacenar la información del hogar
     * proporcionada por la persona que solicita la adopción.
     */

    @Column(name = "tipo_vivienda", length = 100)
    private String tipoVivienda;

    @Column(name = "viven_ninos", length = 50)
    private String vivenNinos;

    @Column(name = "tiene_mascotas", length = 100)
    private String tieneMascotas;

    @Column(name = "tiempo_fuera", length = 50)
    private String tiempoFuera;

    @Column(name = "motivacion", length = 255)
    private String motivacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota mascota;

    public Solicitud() {
    }
}

 