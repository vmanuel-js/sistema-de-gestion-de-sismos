package com.grupo6.sistema.repository;

import com.grupo6.sistema.model.Distrito;
import com.grupo6.sistema.model.EstacionMonitoreo;
import jakarta.persistence.EntityManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Repositorio JPA de estaciones de monitoreo. */
public class EstacionMonitoreoRepository extends RepositorioJpa<EstacionMonitoreo> {

    public EstacionMonitoreoRepository() {
        super(EstacionMonitoreo.class);
    }

    @Override
    protected String ordenListado() {
        return "e.codigo";
    }

    @Override
    protected void prepararRelaciones(EntityManager em, EstacionMonitoreo estacion) {
        estacion.setDistrito(em.getReference(Distrito.class, estacion.getDistrito().getId()));
    }

    /** Solo las estaciones activas pueden recibir nuevos registros de sismos. */
    public List<EstacionMonitoreo> listarActivas() {
        return consultar(em -> em.createQuery(
                        "SELECT e FROM EstacionMonitoreo e WHERE e.estado = :estado ORDER BY e.codigo",
                        EstacionMonitoreo.class)
                .setParameter("estado", EstacionMonitoreo.ESTADO_ACTIVA)
                .getResultList(), "No se pudo listar las estaciones activas.");
    }

    public Optional<EstacionMonitoreo> buscarPorCodigo(String codigo) {
        return buscarUno("SELECT e FROM EstacionMonitoreo e WHERE e.codigo = :valor", codigo);
    }

    /** HU-11: cantidad de sismos registrados por cada estación (id de estación -> total). */
    public Map<Long, Long> contarSismosPorEstacion() {
        return consultar(em -> {
            Map<Long, Long> totales = new HashMap<>();
            em.createQuery("SELECT s.estacion.id, COUNT(s) FROM Sismo s WHERE s.estacion IS NOT NULL GROUP BY s.estacion.id",
                            Object[].class)
                    .getResultList()
                    .forEach(fila -> totales.put((Long) fila[0], (Long) fila[1]));
            return totales;
        }, "No se pudo contar los sismos por estación.");
    }

    public long contarSismos(long estacionId) {
        return contar("SELECT COUNT(s) FROM Sismo s WHERE s.estacion.id = :valor", estacionId);
    }
}
