package com.grupo6.sistema.repository;

import com.grupo6.sistema.model.EstacionMonitoreo;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio CRUD de estaciones de monitoreo en memoria.
 */
public class EstacionMonitoreoRepository {

    private final List<EstacionMonitoreo> datos = new CopyOnWriteArrayList<>();
    private final AtomicLong siguienteId = new AtomicLong(1);

    public EstacionMonitoreo agregar(EstacionMonitoreo estacionMonitoreo) {
        estacionMonitoreo.setId(siguienteId.getAndIncrement());
        datos.add(estacionMonitoreo);
        return estacionMonitoreo;
    }

    public List<EstacionMonitoreo> listar() {
        return List.copyOf(datos);
    }

    public Optional<EstacionMonitoreo> buscarPorId(long id) {
        return datos.stream().filter(x -> x.getId() == id).findFirst();
    }

    public Optional<EstacionMonitoreo> buscarPorCodigo(String codigo) {
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

    /** Solo las estaciones activas pueden recibir nuevos registros de sismos. */
    public List<EstacionMonitoreo> listarActivas() {
        return datos.stream().filter(EstacionMonitoreo::isActiva).toList();
    }

    public boolean actualizar(EstacionMonitoreo estacionMonitoreo) {
        Optional<EstacionMonitoreo> actual = buscarPorId(estacionMonitoreo.getId());
        if (actual.isEmpty()) {
            return false;
        }
        datos.set(datos.indexOf(actual.get()), estacionMonitoreo);
        return true;
    }

    public boolean eliminar(long id) {
        return datos.removeIf(x -> x.getId() == id);
    }
}
