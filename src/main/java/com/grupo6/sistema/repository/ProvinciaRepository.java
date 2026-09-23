package com.grupo6.sistema.repository;

import com.grupo6.sistema.model.Provincia;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio CRUD de provincias en memoria.
 */
public class ProvinciaRepository {

    private final List<Provincia> datos = new CopyOnWriteArrayList<>();
    private final AtomicLong siguienteId = new AtomicLong(1);

    public Provincia agregar(Provincia provincia) {
        provincia.setId(siguienteId.getAndIncrement());
        datos.add(provincia);
        return provincia;
    }

    public List<Provincia> listar() {
        return List.copyOf(datos);
    }

    public Optional<Provincia> buscarPorId(long id) {
        return datos.stream().filter(x -> x.getId() == id).findFirst();
    }

    public Optional<Provincia> buscarPorCodigo(String codigo) {
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

    public List<Provincia> listarPorDepartamento(long departamentoId) {
        return datos.stream().filter(p -> p.getDepartamentoId() == departamentoId).toList();
    }

    public boolean actualizar(Provincia provincia) {
        Optional<Provincia> actual = buscarPorId(provincia.getId());
        if (actual.isEmpty()) {
            return false;
        }
        datos.set(datos.indexOf(actual.get()), provincia);
        return true;
    }

    public boolean eliminar(long id) {
        return datos.removeIf(x -> x.getId() == id);
    }
}
