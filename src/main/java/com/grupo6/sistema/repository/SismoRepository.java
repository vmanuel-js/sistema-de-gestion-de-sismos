package com.grupo6.sistema.repository;

import com.grupo6.sistema.model.Sismo;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio CRUD de sismos en memoria. Los datos de ejemplo los carga AplicacionListener.
 */
public class SismoRepository {
 
    private final List<Sismo> sismos = new CopyOnWriteArrayList<>();
    private final AtomicLong siguienteId = new AtomicLong(1);
 
    public List<Sismo> listar() {
        return sismos.stream()
                .sorted((a, b) -> b.getFechaHora().compareTo(a.getFechaHora()))
                .toList();
    }
 
    public Optional<Sismo> buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return Optional.empty();
        }
        return sismos.stream()
                .filter(s -> s.getCodigo().equalsIgnoreCase(codigo.trim()))
                .findFirst();
    }
 
    public Optional<Sismo> buscarPorId(long id) {
        return sismos.stream().filter(s -> s.getId() == id).findFirst();
    }
 
    public boolean existeCodigo(String codigo) {
        return buscarPorCodigo(codigo).isPresent();
    }

    /**
     * Para Editar: indica si el código ya pertenece a OTRO sismo distinto del que se está editando.
     */
    public boolean existeCodigoEnOtroSismo(String codigo, long idActual) {
        return buscarPorCodigo(codigo)
                .map(s -> s.getId() != idActual)
                .orElse(false);
    }
 
    public Sismo agregar(Sismo sismo) {
        sismo.setId(siguienteId.getAndIncrement());
        sismos.add(sismo);
        return sismo;
    }
 
    public boolean actualizar(Sismo sismo) {
        Optional<Sismo> actual = buscarPorId(sismo.getId());
        if (actual.isEmpty()) {
            return false;
        }
        int indice = sismos.indexOf(actual.get());
        sismos.set(indice, sismo);
        return true;
    }
 
    public boolean eliminar(long id) {
        return sismos.removeIf(s -> s.getId() == id);
    }
}