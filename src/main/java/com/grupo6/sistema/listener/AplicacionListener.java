package com.grupo6.sistema.listener;

import com.grupo6.sistema.model.Departamento;
import com.grupo6.sistema.model.Distrito;
import com.grupo6.sistema.model.EstacionMonitoreo;
import com.grupo6.sistema.model.Provincia;
import com.grupo6.sistema.model.ReporteAfectacion;
import com.grupo6.sistema.model.SeguimientoSismo;
import com.grupo6.sistema.model.Sismo;
import com.grupo6.sistema.repository.DepartamentoRepository;
import com.grupo6.sistema.repository.DistritoRepository;
import com.grupo6.sistema.repository.EstacionMonitoreoRepository;
import com.grupo6.sistema.repository.ProvinciaRepository;
import com.grupo6.sistema.repository.ReporteAfectacionRepository;
import com.grupo6.sistema.repository.SeguimientoSismoRepository;
import com.grupo6.sistema.repository.SismoRepository;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Se ejecuta una sola vez al iniciar la aplicación: crea un repositorio compartido por cada
 * entidad, carga los datos de ejemplo y los guarda en el ServletContext para que todos los
 * servlets trabajen sobre la misma información.
 */
@WebListener
public class AplicacionListener implements ServletContextListener {

    public static final String REPOSITORIO_SISMOS = "repositorioSismos";
    public static final String REPOSITORIO_DEPARTAMENTOS = "repositorioDepartamentos";
    public static final String REPOSITORIO_PROVINCIAS = "repositorioProvincias";
    public static final String REPOSITORIO_DISTRITOS = "repositorioDistritos";
    public static final String REPOSITORIO_ESTACIONES = "repositorioEstaciones";
    public static final String REPOSITORIO_REPORTES = "repositorioReportes";
    public static final String REPOSITORIO_SEGUIMIENTOS = "repositorioSeguimientos";

    @Override
    public void contextInitialized(ServletContextEvent event) {
        SismoRepository sismos = new SismoRepository();
        DepartamentoRepository departamentos = new DepartamentoRepository();
        ProvinciaRepository provincias = new ProvinciaRepository();
        DistritoRepository distritos = new DistritoRepository();
        EstacionMonitoreoRepository estaciones = new EstacionMonitoreoRepository();
        ReporteAfectacionRepository reportes = new ReporteAfectacionRepository();
        SeguimientoSismoRepository seguimientos = new SeguimientoSismoRepository();

        cargarUbicaciones(departamentos, provincias, distritos);
        cargarEstaciones(estaciones);
        cargarSismos(sismos);
        cargarReportesYSeguimientos(sismos, reportes, seguimientos);

        ServletContext contexto = event.getServletContext();
        contexto.setAttribute(REPOSITORIO_SISMOS, sismos);
        contexto.setAttribute(REPOSITORIO_DEPARTAMENTOS, departamentos);
        contexto.setAttribute(REPOSITORIO_PROVINCIAS, provincias);
        contexto.setAttribute(REPOSITORIO_DISTRITOS, distritos);
        contexto.setAttribute(REPOSITORIO_ESTACIONES, estaciones);
        contexto.setAttribute(REPOSITORIO_REPORTES, reportes);
        contexto.setAttribute(REPOSITORIO_SEGUIMIENTOS, seguimientos);
    }

    private void cargarUbicaciones(DepartamentoRepository departamentos,
                                   ProvinciaRepository provincias,
                                   DistritoRepository distritos) {
        // departamento, provincia, distrito (códigos de ubigeo)
        agregarUbicacion(departamentos, provincias, distritos, "04", "Arequipa", "0405", "Caylloma", "040510", "Chivay");
        agregarUbicacion(departamentos, provincias, distritos, "15", "Lima", "1505", "Cañete", "150501", "San Vicente de Cañete");
        agregarUbicacion(departamentos, provincias, distritos, "08", "Cusco", "0809", "La Convención", "080902", "Echarati");
        agregarUbicacion(departamentos, provincias, distritos, "11", "Ica", "1103", "Nasca", "110305", "Vista Alegre");
        agregarUbicacion(departamentos, provincias, distritos, "23", "Tacna", "2301", "Tacna", "230110", "Pocollay");
    }

    private void agregarUbicacion(DepartamentoRepository departamentos, ProvinciaRepository provincias,
                                  DistritoRepository distritos,
                                  String codDep, String nomDep, String codProv, String nomProv,
                                  String codDist, String nomDist) {
        Departamento departamento = departamentos.buscarPorCodigo(codDep)
                .orElseGet(() -> departamentos.agregar(new Departamento(null, codDep, nomDep)));
        Provincia provincia = provincias.buscarPorCodigo(codProv)
                .orElseGet(() -> provincias.agregar(new Provincia(null, codProv, nomProv, departamento.getId())));
        distritos.agregar(new Distrito(null, codDist, nomDist, provincia.getId()));
    }

    private void cargarEstaciones(EstacionMonitoreoRepository estaciones) {
        estaciones.agregar(new EstacionMonitoreo(null, "ARE-01", "Estación Chivay", "Arequipa", "Caylloma",
                -15.638, -71.601, LocalDate.of(2019, 3, 12), EstacionMonitoreo.ESTADO_ACTIVA));
        estaciones.agregar(new EstacionMonitoreo(null, "LIM-02", "Estación Cañete", "Lima", "Cañete",
                -13.078, -76.387, LocalDate.of(2020, 6, 5), EstacionMonitoreo.ESTADO_ACTIVA));
        estaciones.agregar(new EstacionMonitoreo(null, "CUS-01", "Estación Echarati", "Cusco", "La Convención",
                -12.770, -72.580, LocalDate.of(2018, 11, 20), EstacionMonitoreo.ESTADO_ACTIVA));
        estaciones.agregar(new EstacionMonitoreo(null, "ICA-01", "Estación Nasca", "Ica", "Nasca",
                -14.830, -74.940, LocalDate.of(2021, 1, 15), EstacionMonitoreo.ESTADO_ACTIVA));
        estaciones.agregar(new EstacionMonitoreo(null, "TAC-01", "Estación Pocollay", "Tacna", "Tacna",
                -17.995, -70.221, LocalDate.of(2017, 8, 30), EstacionMonitoreo.ESTADO_ACTIVA));
        // Estación inactiva: sirve para probar la regla "una estación inactiva no puede recibir nuevos registros"
        estaciones.agregar(new EstacionMonitoreo(null, "TAC-02", "Estación Candarave", "Tacna", "Candarave",
                -17.268, -70.250, LocalDate.of(2015, 4, 2), EstacionMonitoreo.ESTADO_INACTIVA));
    }

    private void cargarSismos(SismoRepository sismos) {
        sismos.agregar(new Sismo(null, "SIS-001", LocalDateTime.of(2026, 8, 18, 14, 32),
                5.6, 34, -15.638, -71.601, "Arequipa", "Caylloma", "Chivay", "IV (MM)",
                Sismo.ESTADO_EN_SEGUIMIENTO, "ARE-01"));
        sismos.agregar(new Sismo(null, "SIS-002", LocalDateTime.of(2026, 8, 2, 3, 15),
                4.5, 48, -13.078, -76.387, "Lima", "Cañete", "San Vicente de Cañete", "III (MM)",
                Sismo.ESTADO_REGISTRADO, "LIM-02"));
        sismos.agregar(new Sismo(null, "SIS-003", LocalDateTime.of(2026, 7, 21, 22, 47),
                4.8, 110, -12.770, -72.580, "Cusco", "La Convención", "Echarati", "III (MM)",
                Sismo.ESTADO_EN_EVALUACION, "CUS-01"));
        sismos.agregar(new Sismo(null, "SIS-004", LocalDateTime.of(2026, 7, 9, 10, 5),
                5.1, 28, -14.850, -74.940, "Ica", "Nasca", "Vista Alegre", "V (MM)",
                Sismo.ESTADO_CERRADO, "ICA-01"));
        sismos.agregar(new Sismo(null, "SIS-005", LocalDateTime.of(2026, 6, 30, 18, 40),
                4.2, 62, -17.995, -70.221, "Tacna", "Tacna", "Pocollay", "III (MM)",
                Sismo.ESTADO_REGISTRADO, "TAC-01"));
    }

    private void cargarReportesYSeguimientos(SismoRepository sismos, ReporteAfectacionRepository reportes,
                                             SeguimientoSismoRepository seguimientos) {
        sismos.buscarPorCodigo("SIS-001").ifPresent(sismo -> {
            reportes.agregar(new ReporteAfectacion(null, sismo.getId(), "Chivay, Caylloma, Arequipa",
                    LocalDateTime.of(2026, 8, 18, 16, 10), "Vivienda",
                    "Fisuras en muros de adobe de viviendas del casco urbano.", "Medio", 35,
                    ReporteAfectacion.ESTADO_VERIFICADO));
            seguimientos.agregar(new SeguimientoSismo(null, sismo.getId(), LocalDateTime.of(2026, 8, 19, 9, 0),
                    "Analista de turno", Sismo.ESTADO_EN_SEGUIMIENTO,
                    "Se confirman daños leves en viviendas de adobe.",
                    "Coordinación con el gobierno local para la evaluación de viviendas."));
        });
    }
}
