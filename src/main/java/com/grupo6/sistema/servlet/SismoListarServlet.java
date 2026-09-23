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
import java.util.*;

@WebServlet(name = "SismoListarServlet", urlPatterns = {"/sismos"})
public class SismoListarServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SismoRepository repositorio = repositorio(request);
        List<Sismo> sismos = repositorio.listar();

        request.setAttribute("sismos", sismos);
        request.setAttribute("totalSismos", sismos.size());
        
        request.getRequestDispatcher("/WEB-INF/views/sismos/lista.jsp")
                .forward(request, response);
    }
    
    private SismoRepository repositorio(HttpServletRequest request) {
        return (SismoRepository) request.getServletContext()
                .getAttribute(AplicacionListener.REPOSITORIO_SISMOS);
    }
}
