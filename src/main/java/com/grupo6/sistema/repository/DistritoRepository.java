package com.grupo6.sistema.repository;

import com.grupo6.sistema.model.Distrito;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio CRUD de distritos en memoria.
 */
public class DistritoRepository {

    private final List<Distrito> datos = new CopyOnWriteArrayList<>();
    private final AtomicLong siguienteId = new AtomicLong(1);

    public Distrito agregar(Distrito distrito) {
        distrito.setId(siguienteId.getAndIncrement());
        datos.add(distrito);
        return distrito;
    }

    public List<Distrito> listar() {
        return List.copyOf(datos);
    }

    public Optional<Distrito> buscarPorId(long id) {
        return datos.stream().filter(x -> x.getId() == id).findFirst();
    }

    public Optional<Distrito> buscarPorCodigo(String codigo) {
        if (codigo == null) {
            return Optional.empty();
        }
        return datos.stream()
                .filter(x -> x.getCodigo().equalsIgnoreCase(codigo.trim()))
                .findFirst();
    }

    public boolean existeCodigo(String codigo) {
        return buscarPorCodigo(codigo).isPresent();
    }

    public List<Distrito> listarPorProvincia(long provinciaId) {
        return datos.stream().filter(d -> d.getProvinciaId() == provinciaId).toList();
    }

    public boolean actualizar(Distrito distrito) {
        Optional<Distrito> actual = buscarPorId(distrito.getId());
        if (actual.isEmpty()) {
            return false;
        }
        datos.set(datos.indexOf(actual.get()), distrito);
        return true;
    }

    public boolean eliminar(long id) {
        return datos.removeIf(x -> x.getId() == id);
    }
}
