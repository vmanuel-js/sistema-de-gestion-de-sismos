package com.grupo6.sistema.model;

import java.time.LocalDateTime;

/**
 * Actualización de seguimiento de un sismo relevante (Registrado → En evaluación → En seguimiento → Cerrado).
 */
public class SeguimientoSismo {

    private Long id;
    private Long sismoId;
    private LocalDateTime fechaActualizacion;
    private String responsable;
    private String estado;
    private String observaciones;
    private String accionesRealizadas;

    public SeguimientoSismo() {
    }

    public SeguimientoSismo(Long id, Long sismoId, LocalDateTime fechaActualizacion, String responsable, String estado, String observaciones, String accionesRealizadas) {
        this.id = id;
        this.sismoId = sismoId;
        this.fechaActualizacion = fechaActualizacion;
        this.responsable = responsable;
        this.estado = estado;
        this.observaciones = observaciones;
        this.accionesRealizadas = accionesRealizadas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSismoId() {
        return sismoId;
    }

    public void setSismoId(Long sismoId) {
        this.sismoId = sismoId;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getAccionesRealizadas() {
        return accionesRealizadas;
    }

    public void setAccionesRealizadas(String accionesRealizadas) {
        this.accionesRealizadas = accionesRealizadas;
    }
}
