// modificado por isabel
package com.adoptaPet.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Table(name = "adopcion")
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adopcion")
    private Integer idAdopcion;

    @Column(name = "fecha_adopcion")
    private LocalDate fechaAdopcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota mascota;

    @OneToOne
    @JoinColumn(name = "id_solicitud", unique = true)
    private Solicitud solicitud;

    @Column(name = "estado_adopcion", length = 30)
    private String estadoAdopcion;

    @Column(name = "informacion_cierre")
    private String informacionCierre;

    public Adopcion() {
    }

    public Integer getIdAdopcion() {
        return idAdopcion;
    }

    public void setIdAdopcion(Integer idAdopcion) {
        this.idAdopcion = idAdopcion;
    }

    public LocalDate getFechaAdopcion() {
        return fechaAdopcion;
    }

    public void setFechaAdopcion(LocalDate fechaAdopcion) {
        this.fechaAdopcion = fechaAdopcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public String getEstadoAdopcion() {
        return estadoAdopcion;
    }

    public void setEstadoAdopcion(String estadoAdopcion) {
        this.estadoAdopcion = estadoAdopcion;
    }

    public String getInformacionCierre() {
        return informacionCierre;
    }

    public void setInformacionCierre(String informacionCierre) {
        this.informacionCierre = informacionCierre;
    }
}