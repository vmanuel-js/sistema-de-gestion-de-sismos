package com.grupo6.sistema.validacion;

import com.grupo6.sistema.model.EstacionMonitoreo;
import com.grupo6.sistema.model.Sismo;
import com.grupo6.sistema.repository.EstacionMonitoreoRepository;
import com.grupo6.sistema.repository.SismoRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Validaciones del sismo en el servidor. Se usa en Nuevo (HU-02) y en Editar (HU-06) para no
 * repetir código.
 *
 * HU-03: campos obligatorios y código único.
 * HU-04: magnitud, profundidad, coordenadas y fecha no futura.
 * HU-08: estado válido (solo al editar; al registrar siempre es Registrado).
 * Regla de negocio: una estación inactiva no puede recibir nuevos registros.
 *
 * Devuelve un mapa campo -> mensaje. Si el mapa está vacío, los datos son válidos.
 * La clave del mapa es el nombre del campo del formulario, para mostrar cada mensaje
 * junto a su campo en la JSP (por ejemplo ${errores.codigo}).
 */
public class SismoValidador {

    private final SismoRepository sismos;
    private final EstacionMonitoreoRepository estaciones;

    public SismoValidador(SismoRepository sismos, EstacionMonitoreoRepository estaciones) {
        this.sismos = sismos;
        this.estaciones = estaciones;
    }

    /**
     * @param f          datos escritos en el formulario
     * @param sismoActual null al registrar; el sismo que se está editando al editar
     */
    public Map<String, String> validar(SismoFormulario f, Sismo sismoActual) {
        Map<String, String> errores = new LinkedHashMap<>();
        boolean esNuevo = sismoActual == null;

        // ---------- HU-03: campos obligatorios ----------
        obligatorio(errores, "codigo", f.getCodigo(), "El código es obligatorio.");
        obligatorio(errores, "fechaHora", f.getFechaHora(), "La fecha y hora son obligatorias.");
        obligatorio(errores, "magnitud", f.getMagnitud(), "La magnitud es obligatoria.");
        obligatorio(errores, "profundidad", f.getProfundidad(), "La profundidad es obligatoria.");
        obligatorio(errores, "latitud", f.getLatitud(), "La latitud es obligatoria.");
        obligatorio(errores, "longitud", f.getLongitud(), "La longitud es obligatoria.");
        obligatorio(errores, "departamento", f.getDepartamento(), "El departamento es obligatorio.");
        obligatorio(errores, "provincia", f.getProvincia(), "La provincia es obligatoria.");
        obligatorio(errores, "distritoReferencia", f.getDistritoReferencia(),
                "El distrito o referencia es obligatorio.");

        // ---------- HU-03: código único ----------
        if (!errores.containsKey("codigo")) {
            boolean repetido = esNuevo
                    ? sismos.existeCodigo(f.getCodigo())
                    : sismos.existeCodigoEnOtroSismo(f.getCodigo(), sismoActual.getId());
            if (repetido) {
                errores.put("codigo", "El código " + f.getCodigo() + " ya está registrado.");
            }
        }

        // ---------- HU-04: fecha no futura ----------
        if (!errores.containsKey("fechaHora")) {
            try {
                LocalDateTime fecha = LocalDateTime.parse(f.getFechaHora());
                if (fecha.isAfter(LocalDateTime.now())) {
                    errores.put("fechaHora", "La fecha de ocurrencia no puede ser futura.");
                }
            } catch (DateTimeParseException ex) {
                errores.put("fechaHora", "El formato de fecha y hora no es válido.");
            }
        }

        // ---------- HU-04: magnitud, profundidad y coordenadas ----------
        rango(errores, "magnitud", f.getMagnitud(), "La magnitud",
                0, false, null, "La magnitud debe ser mayor que cero.");
        rango(errores, "profundidad", f.getProfundidad(), "La profundidad",
                0, true, null, "La profundidad no puede ser negativa.");
        rango(errores, "latitud", f.getLatitud(), "La latitud",
                -90, true, 90.0, "La latitud debe estar entre -90 y 90.");
        rango(errores, "longitud", f.getLongitud(), "La longitud",
                -180, true, 180.0, "La longitud debe estar entre -180 y 180.");

        // ---------- HU-08: estado válido (solo al editar) ----------
        if (!esNuevo) {
            if (f.getEstado().isBlank()) {
                errores.put("estado", "El estado es obligatorio.");
            } else if (!Sismo.esEstadoValido(f.getEstado())) {
                errores.put("estado", "El estado seleccionado no es válido.");
            }
        }

        // ---------- Intensidad (opcional, pero debe ser de la escala) ----------
        if (!f.getIntensidad().isBlank() && !esIntensidadValida(f.getIntensidad())) {
            errores.put("intensidad", "La intensidad seleccionada no es válida.");
        }

        // ---------- Regla de negocio: estación existente y activa ----------
        if (!f.getCodigoEstacion().isBlank()) {
            Optional<EstacionMonitoreo> estacion = estaciones.buscarPorCodigo(f.getCodigoEstacion());
            boolean mismaEstacion = !esNuevo
                    && f.getCodigoEstacion().equalsIgnoreCase(String.valueOf(sismoActual.getCodigoEstacion()));
            if (estacion.isEmpty()) {
                errores.put("codigoEstacion", "La estación seleccionada no existe.");
            } else if (!estacion.get().isActiva() && !mismaEstacion) {
                errores.put("codigoEstacion", "La estación " + estacion.get().getCodigo()
                        + " está inactiva y no puede recibir nuevos registros.");
            }
        }

        return errores;
    }

    private void obligatorio(Map<String, String> errores, String campo, String valor, String mensaje) {
        if (valor == null || valor.isBlank()) {
            errores.put(campo, mensaje);
        }
    }

    /**
     * Valida que el texto sea un número y que esté dentro del rango.
     *
     * @param minimo        límite inferior
     * @param incluyeMinimo true: se acepta el mínimo (>=); false: debe ser mayor (>)
     * @param maximo        límite superior incluido, o null si no hay
     */
    private void rango(Map<String, String> errores, String campo, String valor, String nombre,
                       double minimo, boolean incluyeMinimo, Double maximo, String mensajeRango) {
        if (errores.containsKey(campo)) {
            return; // ya tiene el error de obligatorio
        }
        Double numero;
        try {
            numero = aNumero(valor);
        } catch (NumberFormatException ex) {
            errores.put(campo, nombre + " debe ser un número válido.");
            return;
        }
        boolean bajoMinimo = incluyeMinimo ? numero < minimo : numero <= minimo;
        boolean sobreMaximo = maximo != null && numero > maximo;
        if (bajoMinimo || sobreMaximo || numero.isNaN() || numero.isInfinite()) {
            errores.put(campo, mensajeRango);
        }
    }

    private boolean esIntensidadValida(String intensidad) {
        for (String valida : Sismo.INTENSIDADES) {
            if (valida.equals(intensidad)) {
                return true;
            }
        }
        return false;
    }

    /** Convierte un texto a número aceptando coma o punto decimal (4,5 o 4.5). */
    public static double aNumero(String texto) {
        return Double.parseDouble(texto.trim().replace(',', '.'));
    }
}
