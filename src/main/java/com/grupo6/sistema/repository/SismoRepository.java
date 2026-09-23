package com.grupo6.sistema.repository;

import com.grupo6.sistema.model.Sismo;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class SismoRepository {
 
    private final List<Sismo> sismos = new CopyOnWriteArrayList<>();
    private final AtomicLong siguienteId = new AtomicLong(1);
 
    public SismoRepository() {
        agregar(new Sismo(null, "SIS-001", LocalDateTime.of(2026, 8, 18, 14, 32),
                5.6, 34, -16.409, -71.537, "Arequipa", "Arequipa", "Chivay", "IV (MM)",
                Sismo.ESTADO_EN_SEGUIMIENTO));
        agregar(new Sismo(null, "SIS-002", LocalDateTime.of(2026, 8, 2, 3, 15),
                4.5, 48, -13.078, -76.387, "Lima", "Cañete", "San Vicente de Cañete", "III (MM)",
                Sismo.ESTADO_REGISTRADO));
        agregar(new Sismo(null, "SIS-003", LocalDateTime.of(2026, 7, 21, 22, 47),
                4.8, 110, -13.531, -71.967, "Cusco", "Cusco", "Echarati", "III (MM)",
                Sismo.ESTADO_EN_EVALUACION));
        agregar(new Sismo(null, "SIS-004", LocalDateTime.of(2026, 7, 9, 10, 5),
                5.1, 28, -14.85, -74.94, "Ica", "Nasca", "Vista Alegre, Nasca", "V (MM)",
                Sismo.ESTADO_CERRADO));
        agregar(new Sismo(null, "SIS-005", LocalDateTime.of(2026, 6, 30, 18, 40),
                4.2, 62, -18.014, -70.253, "Tacna", "Tacna", "Pocollay", "III (MM)",
                Sismo.ESTADO_REGISTRADO));
    }
 
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