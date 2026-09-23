package com.grupo6.sistema.model;

import java.time.LocalDate;

/**
 * Estación de monitoreo que registra eventos sísmicos. Una estación puede registrar muchos sismos.
 */
public class EstacionMonitoreo {

    public static final String ESTADO_ACTIVA = "Activa";
    public static final String ESTADO_INACTIVA = "Inactiva";

    private Long id;
    private String codigo;
    private String nombre;
    private String departamento;
    private String provincia;
    private double latitud;
    private double longitud;
    private LocalDate fechaInstalacion;
    private String estado;

    public EstacionMonitoreo() {
    }

    public EstacionMonitoreo(Long id, String codigo, String nombre, String departamento, String provincia, double latitud, double longitud, LocalDate fechaInstalacion, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.departamento = departamento;
        this.provincia = provincia;
        this.latitud = latitud;
        this.longitud = longitud;
        this.fechaInstalacion = fechaInstalacion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public LocalDate getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(LocalDate fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /** Una estación inactiva no puede recibir nuevos registros de sismos. */
    public boolean isActiva() {
        return ESTADO_ACTIVA.equals(estado);
    }
}
