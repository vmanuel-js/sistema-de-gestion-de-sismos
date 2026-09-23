package com.grupo6.sistema.model;

/**
 * Distrito perteneciente a una provincia.
 */
public class Distrito {

    private Long id;
    private String codigo;
    private String nombre;
    private Long provinciaId;

    public Distrito() {
    }

    public Distrito(Long id, String codigo, String nombre, Long provinciaId) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.provinciaId = provinciaId;
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

    public Long getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(Long provinciaId) {
        this.provinciaId = provinciaId;
    }
}
