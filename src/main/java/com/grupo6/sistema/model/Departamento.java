package com.grupo6.sistema.model;

/**
 * Departamento del Perú (catálogo de ubicaciones).
 */
public class Departamento {

    private Long id;
    private String codigo;
    private String nombre;

    public Departamento() {
    }

    public Departamento(Long id, String codigo, String nombre) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
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
}
