package com.grupo6.sistema.servlet;

import com.grupo6.sistema.listener.AplicacionListener;
import com.grupo6.sistema.repository.SismoRepository;
import com.grupo6.sistema.model.Sismo;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

@WebServlet(name = "SismoDetalleServlet", urlPatterns = {"/sismos/detalle"})
public class SismoDetalleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = leerId(request.getParameter("id"));
        SismoRepository repositorio = (SismoRepository) getServletContext()
                .getAttribute(AplicacionListener.REPOSITORIO_SISMOS);
        Optional<Sismo> encontrado = id > 0 ? repositorio.buscarPorId(id) : Optional.empty();

        if (encontrado.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.setAttribute("mensajeError", "No existe un sismo con el identificador solicitado.");
            request.getRequestDispatcher("/WEB-INF/views/error/404.jsp")
                    .forward(request, response);
            return;
        }

        request.setAttribute("sismo", encontrado.get());
        request.setAttribute("sismoCreado", "1".equals(request.getParameter("creado")));
        request.getRequestDispatcher("/WEB-INF/views/sismos/detalle.jsp")
                .forward(request, response);
    }
    
    private long leerId(String texto) {
        try {
            return Long.parseLong(texto);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
