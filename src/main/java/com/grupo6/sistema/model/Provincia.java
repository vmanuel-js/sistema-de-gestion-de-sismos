package com.grupo6.sistema.model;

/**
 * Provincia perteneciente a un departamento.
 */
public class Provincia {

    private Long id;
    private String codigo;
    private String nombre;
    private Long departamentoId;

    public Provincia() {
    }

    public Provincia(Long id, String codigo, String nombre, Long departamentoId) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.departamentoId = departamentoId;
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

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }
}
