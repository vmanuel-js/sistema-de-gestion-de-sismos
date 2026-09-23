package com.grupo6.sistema.repository;

import com.grupo6.sistema.model.Departamento;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio CRUD de departamentos en memoria.
 */
public class DepartamentoRepository {

    private final List<Departamento> datos = new CopyOnWriteArrayList<>();
    private final AtomicLong siguienteId = new AtomicLong(1);

    public Departamento agregar(Departamento departamento) {
        departamento.setId(siguienteId.getAndIncrement());
        datos.add(departamento);
        return departamento;
    }

    public List<Departamento> listar() {
        return List.copyOf(datos);
    }

    public Optional<Departamento> buscarPorId(long id) {
        return datos.stream().filter(x -> x.getId() == id).findFirst();
    }

    public Optional<Departamento> buscarPorCodigo(String codigo) {
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

    public boolean actualizar(Departamento departamento) {
        Optional<Departamento> actual = buscarPorId(departamento.getId());
        if (actual.isEmpty()) {
            return false;
        }
        datos.set(datos.indexOf(actual.get()), departamento);
        return true;
    }

    public boolean eliminar(long id) {
        return datos.removeIf(x -> x.getId() == id);
    }
}
