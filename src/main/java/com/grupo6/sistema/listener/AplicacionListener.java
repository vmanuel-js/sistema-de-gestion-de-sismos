package com.grupo6.sistema.listener;

import com.grupo6.sistema.repository.SismoRepository;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AplicacionListener implements ServletContextListener {
    
    public static final String REPOSITORIO_SISMOS = "repositorioSismos";
    
     @Override
     public void contextInitialized(ServletContextEvent event) {
         event.getServletContext().setAttribute(
                 REPOSITORIO_SISMOS, 
                 new SismoRepository()
         );
     }
}
