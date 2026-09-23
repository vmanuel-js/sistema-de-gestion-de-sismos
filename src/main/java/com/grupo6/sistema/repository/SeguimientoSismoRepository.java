package com.grupo6.sistema.repository;

import com.grupo6.sistema.model.SeguimientoSismo;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio CRUD de actualizaciones de seguimiento en memoria.
 */
public class SeguimientoSismoRepository {

    private final List<SeguimientoSismo> datos = new CopyOnWriteArrayList<>();
    private final AtomicLong siguienteId = new AtomicLong(1);

    public SeguimientoSismo agregar(SeguimientoSismo seguimientoSismo) {
        seguimientoSismo.setId(siguienteId.getAndIncrement());
        datos.add(seguimientoSismo);
        return seguimientoSismo;
    }

    public List<SeguimientoSismo> listar() {
        return List.copyOf(datos);
    }

    public Optional<SeguimientoSismo> buscarPorId(long id) {
        return datos.stream().filter(x -> x.getId() == id).findFirst();
    }

    public List<SeguimientoSismo> listarPorSismo(long sismoId) {
        return datos.stream().filter(s -> s.getSismoId() == sismoId).toList();
    }

    public boolean actualizar(SeguimientoSismo seguimientoSismo) {
        Optional<SeguimientoSismo> actual = buscarPorId(seguimientoSismo.getId());
        if (actual.isEmpty()) {
            return false;
        }
        datos.set(datos.indexOf(actual.get()), seguimientoSismo);
        return true;
    }

    public boolean eliminar(long id) {
        return datos.removeIf(x -> x.getId() == id);
    }
}
