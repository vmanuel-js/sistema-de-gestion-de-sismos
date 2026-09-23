package com.grupo6.sistema.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.grupo6.sistema.listener.AplicacionListener;
import com.grupo6.sistema.model.Sismo;
import com.grupo6.sistema.repository.SismoRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SismoNuevoServlet", urlPatterns = "/sismos/nuevo")
public class SismoNuevoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        mostrarFormulario(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String codigo = limpiar(request.getParameter("codigo"));
        String fechaHoraTexto = limpiar(request.getParameter("fechaHora"));
        String magnitudTexto = limpiar(request.getParameter("magnitud"));
        String profundidadTexto = limpiar(request.getParameter("profundidad"));
        String latitudTexto = limpiar(request.getParameter("latitud"));
        String longitudTexto = limpiar(request.getParameter("longitud"));
        String departamento = limpiar(request.getParameter("departamento"));
        String provincia = limpiar(request.getParameter("provincia"));
        String distritoReferencia = limpiar(request.getParameter("distritoReferencia"));
        String intensidad = limpiar(request.getParameter("intensidad"));

        conservarFormulario(request, codigo, fechaHoraTexto, magnitudTexto, profundidadTexto,
                latitudTexto, longitudTexto, departamento, provincia, distritoReferencia, intensidad);

        List<String> errores = new ArrayList<>();

        if (codigo.isBlank()) errores.add("El codigo es obligatorio.");
        if (fechaHoraTexto.isBlank()) errores.add("La fecha y hora son obligatorias.");
        if (magnitudTexto.isBlank()) errores.add("La magnitud es obligatoria.");
        if (profundidadTexto.isBlank()) errores.add("La profundidad es obligatoria.");
        if (latitudTexto.isBlank()) errores.add("La latitud es obligatoria.");
        if (longitudTexto.isBlank()) errores.add("La longitud es obligatoria.");
        if (departamento.isBlank()) errores.add("El departamento es obligatorio.");
        if (provincia.isBlank()) errores.add("La provincia es obligatoria.");
        if (distritoReferencia.isBlank()) errores.add("El distrito o referencia es obligatorio.");

        SismoRepository repositorio = repositorio(request);
        if (!codigo.isBlank() && repositorio.existeCodigo(codigo)) {
            errores.add("El codigo " + codigo + " ya esta registrado.");
        }

        LocalDateTime fechaHora = null;
        Double magnitud = null;
        Double profundidad = null;
        Double latitud = null;
        Double longitud = null;

        if (!fechaHoraTexto.isBlank()) {
            try {
                fechaHora = LocalDateTime.parse(fechaHoraTexto);
                if (fechaHora.isAfter(LocalDateTime.now())) {
                    errores.add("No puede ser una fecha futura.");
                }
            } catch (DateTimeParseException ex) {
                errores.add("El formato de fecha y hora no es valido.");
            }
        }
        if (!magnitudTexto.isBlank()) {
            try {
                magnitud = Double.valueOf(magnitudTexto.replace(',', '.'));
                if (magnitud <= 0) errores.add("La magnitud debe ser mayor que cero.");
            } catch (NumberFormatException ex) {
                errores.add("La magnitud debe ser un numero valido.");
            }
        }
        if (!profundidadTexto.isBlank()) {
            try {
                profundidad = Double.valueOf(profundidadTexto.replace(',', '.'));
                if (profundidad < 0) errores.add("La profundidad no puede ser negativa.");
            } catch (NumberFormatException ex) {
                errores.add("La profundidad debe ser un numero valido.");
            }
        }
        if (!latitudTexto.isBlank()) {
            try {
                latitud = Double.valueOf(latitudTexto.replace(',', '.'));
                if (latitud < -90 || latitud > 90) errores.add("La latitud debe estar entre -90 y 90.");
            } catch (NumberFormatException ex) {
                errores.add("La latitud debe ser un numero valido.");
            }
        }
        if (!longitudTexto.isBlank()) {
            try {
                longitud = Double.valueOf(longitudTexto.replace(',', '.'));
                if (longitud < -180 || longitud > 180) errores.add("La longitud debe estar entre -180 y 180.");
            } catch (NumberFormatException ex) {
                errores.add("La longitud debe ser un numero valido.");
            }
        }

        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            mostrarFormulario(request, response);
            return;
        }

        Sismo creado = repositorio.agregar(new Sismo(null, codigo, fechaHora, magnitud,
                profundidad, latitud, longitud, departamento, provincia, distritoReferencia,
                intensidad, Sismo.ESTADO_REGISTRADO));

        response.sendRedirect(request.getContextPath()
                + "/sismos/detalle?id=" + creado.getId()
                + "&creado=1");
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/sismos/formulario.jsp")
                .forward(request, response);
    }

    private void conservarFormulario(HttpServletRequest request, String codigo, String fechaHora,
                                      String magnitud, String profundidad, String latitud, String longitud,
                                      String departamento, String provincia, String distritoReferencia,
                                      String intensidad) {
        request.setAttribute("codigoIngresado", codigo);
        request.setAttribute("fechaHoraIngresada", fechaHora);
        request.setAttribute("magnitudIngresada", magnitud);
        request.setAttribute("profundidadIngresada", profundidad);
        request.setAttribute("latitudIngresada", latitud);
        request.setAttribute("longitudIngresada", longitud);
        request.setAttribute("departamentoIngresado", departamento);
        request.setAttribute("provinciaIngresada", provincia);
        request.setAttribute("distritoReferenciaIngresado", distritoReferencia);
        request.setAttribute("intensidadIngresada", intensidad);
    }

    private SismoRepository repositorio(HttpServletRequest request) {
        return (SismoRepository) request.getServletContext()
                .getAttribute(AplicacionListener.REPOSITORIO_SISMOS);
    }

    private String limpiar(String valor) {
        return valor == null ? "" : valor.trim();
    }
}