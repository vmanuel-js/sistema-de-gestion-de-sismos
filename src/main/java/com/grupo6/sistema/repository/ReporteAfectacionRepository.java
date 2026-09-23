package com.grupo6.sistema.repository;

import com.grupo6.sistema.model.ReporteAfectacion;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repositorio CRUD de reportes de afectación en memoria.
 */
public class ReporteAfectacionRepository {

    private final List<ReporteAfectacion> datos = new CopyOnWriteArrayList<>();
    private final AtomicLong siguienteId = new AtomicLong(1);

    public ReporteAfectacion agregar(ReporteAfectacion reporteAfectacion) {
        reporteAfectacion.setId(siguienteId.getAndIncrement());
        datos.add(reporteAfectacion);
        return reporteAfectacion;
    }

    public List<ReporteAfectacion> listar() {
        return List.copyOf(datos);
    }

    public Optional<ReporteAfectacion> buscarPorId(long id) {
        return datos.stream().filter(x -> x.getId() == id).findFirst();
    }

    public List<ReporteAfectacion> listarPorSismo(long sismoId) {
        return datos.stream().filter(r -> r.getSismoId() == sismoId).toList();
    }

    public boolean actualizar(ReporteAfectacion reporteAfectacion) {
        Optional<ReporteAfectacion> actual = buscarPorId(reporteAfectacion.getId());
        if (actual.isEmpty()) {
            return false;
        }
        datos.set(datos.indexOf(actual.get()), reporteAfectacion);
        return true;
    }

    public boolean eliminar(long id) {
        return datos.removeIf(x -> x.getId() == id);
    }
}
