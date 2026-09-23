package com.grupo6.sistema.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Sismo {

    public static final String ESTADO_REGISTRADO = "Registrado";
    public static final String ESTADO_EN_EVALUACION = "En evaluación";
    public static final String ESTADO_EN_SEGUIMIENTO = "En seguimiento";
    public static final String ESTADO_CERRADO = "Cerrado";

    public static final String[] ESTADOS_VALIDOS = {
            ESTADO_REGISTRADO, ESTADO_EN_EVALUACION, ESTADO_EN_SEGUIMIENTO, ESTADO_CERRADO
    };

    /** Escala de Mercalli Modificada (MM). */
    public static final String[] INTENSIDADES = {
            "I (MM)", "II (MM)", "III (MM)", "IV (MM)", "V (MM)", "VI (MM)",
            "VII (MM)", "VIII (MM)", "IX (MM)", "X (MM)", "XI (MM)", "XII (MM)"
    };

    private static final DateTimeFormatter FORMATO_TEXTO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DateTimeFormatter FORMATO_INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private Long id;
    private String codigo;
    private LocalDateTime fechaHora;
    private double magnitud;
    private double profundidad;
    private double latitud;
    private double longitud;
    private String departamento;
    private String provincia;
    private String distritoReferencia;
    private String intensidad;
    private String estado;
    /** Código de la estación de monitoreo que registró el sismo (relación Estación 1 - N Sismo). */
    private String codigoEstacion;

    public Sismo() {
    }

    public Sismo(Long id, String codigo, LocalDateTime fechaHora, double magnitud,
                 double profundidad, double latitud, double longitud, String departamento,
                 String provincia, String distritoReferencia, String intensidad, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.fechaHora = fechaHora;
        this.magnitud = magnitud;
        this.profundidad = profundidad;
        this.latitud = latitud;
        this.longitud = longitud;
        this.departamento = departamento;
        this.provincia = provincia;
        this.distritoReferencia = distritoReferencia;
        this.intensidad = intensidad;
        this.estado = estado;
    }

    public Sismo(Long id, String codigo, LocalDateTime fechaHora, double magnitud,
                 double profundidad, double latitud, double longitud, String departamento,
                 String provincia, String distritoReferencia, String intensidad, String estado,
                 String codigoEstacion) {
        this(id, codigo, fechaHora, magnitud, profundidad, latitud, longitud, departamento,
                provincia, distritoReferencia, intensidad, estado);
        this.codigoEstacion = codigoEstacion;
    }

    public static boolean esEstadoValido(String estado) {
        if (estado == null) {
            return false;
        }
        for (String valido : ESTADOS_VALIDOS) {
            if (valido.equals(estado)) {
                return true;
            }
        }
        return false;
    }

    public String getClaseEstado() {
        if (estado == null) {
            return "";
        }
        return switch (estado) {
            case ESTADO_EN_SEGUIMIENTO -> "badge badge-seguimiento";
            case ESTADO_EN_EVALUACION -> "badge badge-evaluacion";
            case ESTADO_CERRADO -> "badge badge-cerrado";
            default -> "badge badge-registrado";
        };
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getFechaHoraTexto() {
        if (fechaHora == null) {
            return "";
        }
        return fechaHora.format(FORMATO_TEXTO);
    }

    /** Fecha en el formato que espera un input datetime-local (por ejemplo, 2026-08-18T14:32). */
    public String getFechaHoraInput() {
        if (fechaHora == null) {
            return "";
        }
        return fechaHora.format(FORMATO_INPUT);
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(double magnitud) {
        this.magnitud = magnitud;
    }

    public double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(double profundidad) {
        this.profundidad = profundidad;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistritoReferencia() {
        return distritoReferencia;
    }

    public void setDistritoReferencia(String distritoReferencia) {
        this.distritoReferencia = distritoReferencia;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoEstacion() {
        return codigoEstacion;
    }

    public void setCodigoEstacion(String codigoEstacion) {
        this.codigoEstacion = codigoEstacion;
    }
}
