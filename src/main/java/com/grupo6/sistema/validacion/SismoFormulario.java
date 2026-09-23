package com.grupo6.sistema.validacion;

import com.grupo6.sistema.model.Sismo;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

/**
 * Guarda los valores del formulario de sismo tal como los escribió el usuario (como texto).
 * Sirve para dos cosas:
 * 1. Volver a mostrar lo que escribió el usuario cuando hay errores de validación.
 * 2. Precargar el formulario de Editar con los datos de un sismo existente.
 *
 * Se usa igual en Nuevo (HU-02) y en Editar (HU-06).
 */
public class SismoFormulario {

    private String codigo = "";
    private String fechaHora = "";
    private String magnitud = "";
    private String profundidad = "";
    private String latitud = "";
    private String longitud = "";
    private String departamento = "";
    private String provincia = "";
    private String distritoReferencia = "";
    private String intensidad = "";
    private String codigoEstacion = "";
    private String estado = "";

    /** Lee los campos enviados por el formulario. */
    public static SismoFormulario desdeRequest(HttpServletRequest request) {
        SismoFormulario f = new SismoFormulario();
        f.codigo = limpiar(request.getParameter("codigo")).toUpperCase();
        f.fechaHora = limpiar(request.getParameter("fechaHora"));
        f.magnitud = limpiar(request.getParameter("magnitud"));
        f.profundidad = limpiar(request.getParameter("profundidad"));
        f.latitud = limpiar(request.getParameter("latitud"));
        f.longitud = limpiar(request.getParameter("longitud"));
        f.departamento = limpiar(request.getParameter("departamento"));
        f.provincia = limpiar(request.getParameter("provincia"));
        f.distritoReferencia = limpiar(request.getParameter("distritoReferencia"));
        f.intensidad = limpiar(request.getParameter("intensidad"));
        f.codigoEstacion = limpiar(request.getParameter("codigoEstacion"));
        f.estado = limpiar(request.getParameter("estado"));
        return f;
    }

    /** Copia los datos de un sismo existente (para precargar Editar). */
    public static SismoFormulario desdeSismo(Sismo sismo) {
        SismoFormulario f = new SismoFormulario();
        f.codigo = sismo.getCodigo();
        f.fechaHora = sismo.getFechaHoraInput();
        f.magnitud = String.valueOf(sismo.getMagnitud());
        f.profundidad = String.valueOf(sismo.getProfundidad());
        f.latitud = String.valueOf(sismo.getLatitud());
        f.longitud = String.valueOf(sismo.getLongitud());
        f.departamento = texto(sismo.getDepartamento());
        f.provincia = texto(sismo.getProvincia());
        f.distritoReferencia = texto(sismo.getDistritoReferencia());
        f.intensidad = texto(sismo.getIntensidad());
        f.codigoEstacion = texto(sismo.getCodigoEstacion());
        f.estado = texto(sismo.getEstado());
        return f;
    }

    /**
     * Pasa los valores al objeto Sismo. Solo debe llamarse cuando SismoValidador no encontró errores,
     * porque convierte los textos a número y fecha.
     */
    public void copiarEn(Sismo sismo) {
        sismo.setCodigo(codigo);
        sismo.setFechaHora(LocalDateTime.parse(fechaHora));
        sismo.setMagnitud(SismoValidador.aNumero(magnitud));
        sismo.setProfundidad(SismoValidador.aNumero(profundidad));
        sismo.setLatitud(SismoValidador.aNumero(latitud));
        sismo.setLongitud(SismoValidador.aNumero(longitud));
        sismo.setDepartamento(departamento);
        sismo.setProvincia(provincia);
        sismo.setDistritoReferencia(distritoReferencia);
        sismo.setIntensidad(intensidad.isBlank() ? null : intensidad);
        sismo.setCodigoEstacion(codigoEstacion.isBlank() ? null : codigoEstacion.toUpperCase());
        if (!estado.isBlank()) {
            sismo.setEstado(estado);
        }
    }

    private static String limpiar(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private static String texto(String valor) {
        return valor == null ? "" : valor;
    }

    public String getCodigo() { return codigo; }
    public String getFechaHora() { return fechaHora; }
    public String getMagnitud() { return magnitud; }
    public String getProfundidad() { return profundidad; }
    public String getLatitud() { return latitud; }
    public String getLongitud() { return longitud; }
    public String getDepartamento() { return departamento; }
    public String getProvincia() { return provincia; }
    public String getDistritoReferencia() { return distritoReferencia; }
    public String getIntensidad() { return intensidad; }
    public String getCodigoEstacion() { return codigoEstacion; }
    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado == null ? "" : estado; }
}
