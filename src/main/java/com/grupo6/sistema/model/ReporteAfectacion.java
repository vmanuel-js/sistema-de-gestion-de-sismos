package com.grupo6.sistema.model;

import java.time.LocalDateTime;

/**
 * Reporte de afectación registrado después de un sismo (información simulada).
 */
public class ReporteAfectacion {

    public static final String ESTADO_PENDIENTE = "Pendiente";
    public static final String ESTADO_VERIFICADO = "Verificado";
    public static final String ESTADO_CERRADO = "Cerrado";

    private Long id;
    private Long sismoId;
    private String ubicacion;
    private LocalDateTime fechaHora;
    private String tipoAfectacion;
    private String descripcion;
    private String nivel;
    private int personasAfectadas;
    private String estado;

    public ReporteAfectacion() {
    }

    public ReporteAfectacion(Long id, Long sismoId, String ubicacion, LocalDateTime fechaHora, String tipoAfectacion, String descripcion, String nivel, int personasAfectadas, String estado) {
        this.id = id;
        this.sismoId = sismoId;
        this.ubicacion = ubicacion;
        this.fechaHora = fechaHora;
        this.tipoAfectacion = tipoAfectacion;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.personasAfectadas = personasAfectadas;
        this.estado = estado;
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipoAfectacion() {
        return tipoAfectacion;
    }

    public void setTipoAfectacion(String tipoAfectacion) {
        this.tipoAfectacion = tipoAfectacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getPersonasAfectadas() {
        return personasAfectadas;
    }

    public void setPersonasAfectadas(int personasAfectadas) {
        this.personasAfectadas = personasAfectadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
