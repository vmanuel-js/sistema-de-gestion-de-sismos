# Sistema de Gestión de Sismos — Sprint 1 (PA1)

Grupo 6 · Desarrollo de Aplicaciones Empresariales Avanzado · ISIL 2026-20

## Estado de las HU del sprint 1

| HU | Descripción | Responsable | Estado |
|----|-------------|-------------|--------|
| HU-01 | Listar sismos | Víctor | ✅ |
| HU-02 | Registrar un nuevo sismo | Nick | ✅ |
| HU-03 | Validar campos obligatorios y código único | José | ✅ |
| HU-04 | Validar magnitud, profundidad, coordenadas y fecha | Cristian | ✅ (dentro de `SismoValidador`, revisar) |
| HU-05 | Ver el detalle de un sismo | José | ✅ |
| HU-06 | Editar un sismo | José | ✅ |
| HU-07 | Eliminar un sismo con confirmación | José | ✅ |
| HU-08 | Gestionar el estado del evento | Cristian | ✅ (estado automático al registrar y selector en Editar) |

## Estructura

```
src/main/java/com/grupo6/sistema/
├── model/        Entidades (JavaBeans): Sismo, Departamento, Provincia, Distrito,
│                 EstacionMonitoreo, ReporteAfectacion, SeguimientoSismo
├── repository/   Un repositorio CRUD en memoria por entidad
├── validacion/   SismoFormulario (valores del formulario) y SismoValidador (reglas)
├── listener/     AplicacionListener: crea los repositorios y carga los datos de ejemplo
└── servlet/      Controladores del CRUD de sismos
src/main/webapp/WEB-INF/views/
├── sismos/       lista.jsp, formulario.jsp (Nuevo y Editar), detalle.jsp
└── error/        404.jsp
```

## Reglas para no romper el código de los demás

- **Repositorios:** todos los servlets los toman del `ServletContext` con las constantes de
  `AplicacionListener` (`REPOSITORIO_SISMOS`, `REPOSITORIO_ESTACIONES`, etc.). Nunca crear uno nuevo.
- **Datos de ejemplo:** se cargan en `AplicacionListener`, no en los repositorios.
- **Estados:** usar las constantes de `Sismo` (`ESTADO_REGISTRADO`, `ESTADOS_VALIDOS`, etc.), nunca escribir los textos a mano.
- **Validaciones:** usar siempre `SismoValidador`; no repetir validaciones dentro de los servlets.

## Rutas del CRUD

| Método y ruta | Servlet | Qué hace |
|---|---|---|
| GET `/sismos` | SismoListarServlet | Lista los sismos |
| GET/POST `/sismos/nuevo` | SismoNuevoServlet | Formulario y registro (estado Registrado automático) |
| GET `/sismos/detalle?id=X` | SismoDetalleServlet | Detalle o 404 |
| GET/POST `/sismos/editar?id=X` | SismoEditarServlet | Formulario precargado, mismas validaciones y cambio de estado |
| GET/POST `/sismos/eliminar?id=X` | SismoEliminarServlet | GET confirma, POST elimina; bloquea los sismos En seguimiento |

Todas las operaciones que modifican datos usan POST y redirigen al terminar (Post/Redirect/Get).

## Datos de ejemplo (AplicacionListener)

- 5 sismos (SIS-001 a SIS-005) con estados distintos y su estación.
- 5 departamentos con su provincia y distrito.
- 6 estaciones; **TAC-02 está inactiva** para probar que no acepta nuevos registros.
- 1 reporte de afectación y 1 seguimiento para SIS-001.
- **SIS-001 está En seguimiento**: sirve para probar que no se puede eliminar.

## Cómo ejecutarlo

Requisitos: JDK 17 o superior, Maven y **Tomcat 10.1 u 11** (Jakarta EE 10).

- **NetBeans:** Run con el servidor Tomcat configurado.
- **IntelliJ IDEA Ultimate:** Run → Edit Configurations → Tomcat Server → Local → Deployment →
  artifact `sistema-de-gestion-de-sismos:war exploded`, con Application context `/sistema-de-gestion-de-sismos`.

Abrir `http://localhost:8080/sistema-de-gestion-de-sismos/sismos`.

Después de cada `git pull`, recargar Maven (NetBeans: Reload POM · IntelliJ: Reload All Maven Projects).
