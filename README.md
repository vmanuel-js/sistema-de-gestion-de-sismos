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
| HU-06 | Editar un sismo | Nick | ⏳ Pendiente |
| HU-07 | Eliminar un sismo con confirmación | Víctor | ⏳ Pendiente |
| HU-08 | Gestionar el estado del evento | Cristian | ⏳ Falta el selector en Editar (va con HU-06) |

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

## Guía para HU-06 Editar (Nick)

`formulario.jsp` ya sirve para Editar. Solo falta crear `SismoEditarServlet` en `/sismos/editar`:

- **GET `/sismos/editar?id=X`**: buscar el sismo (si no existe, 404 como en `SismoDetalleServlet`) y enviar a la JSP:
  - `modo` = `"editar"` · `sismoId` = el id · `formulario` = `SismoFormulario.desdeSismo(sismo)`
  - `errores` = `Map.of()` · `estaciones`, `intensidades` y `estados` (igual que en `SismoNuevoServlet`)
- **POST `/sismos/editar`** (el id llega en el campo oculto `id`):
  1. `request.setCharacterEncoding("UTF-8")` y `SismoFormulario.desdeRequest(request)`.
  2. `validador.validar(formulario, sismoActual)`. Pasar el sismo actual hace que el código único ignore al propio sismo y que se valide el estado.
  3. Si hay errores, volver a la JSP con `formulario` y `errores`.
  4. Si no, `formulario.copiarEn(sismoActual)`, `repositorio.actualizar(sismoActual)` y redirigir a
     `/sismos/detalle?id=X&actualizado=1` (el detalle ya muestra "Sismo actualizado correctamente").

## Guía para HU-07 Eliminar (Víctor)

- **GET `/sismos/eliminar?id=X`**: mostrar `eliminar.jsp` con el código, fecha, magnitud, referencia y estado, la advertencia y los botones Confirmar (formulario POST) y Cancelar.
- **POST `/sismos/eliminar`**: si el estado es `Sismo.ESTADO_EN_SEGUIMIENTO`, no eliminar y volver a mostrar la confirmación con el motivo. Si no, `repositorio.eliminar(id)` y redirigir a `/sismos?eliminado=true` (la lista ya muestra el mensaje).
- Para probar el bloqueo: `SIS-001` está En seguimiento en los datos de ejemplo.

## Datos de ejemplo (AplicacionListener)

- 5 sismos (SIS-001 a SIS-005) con estados distintos y su estación.
- 5 departamentos con su provincia y distrito.
- 6 estaciones; **TAC-02 está inactiva** para probar que no acepta nuevos registros.
- 1 reporte de afectación y 1 seguimiento para SIS-001.

## Cómo ejecutarlo

Requisitos: JDK 17 o superior, Maven y **Tomcat 10.1 u 11** (Jakarta EE 10).

- **NetBeans:** Run con el servidor Tomcat configurado.
- **IntelliJ IDEA Ultimate:** Run → Edit Configurations → Tomcat Server → Local → Deployment →
  artifact `sistema-de-gestion-de-sismos:war exploded`, con Application context `/sistema-de-gestion-de-sismos`.

Abrir `http://localhost:8080/sistema-de-gestion-de-sismos/sismos`.

Después de cada `git pull`, recargar Maven (NetBeans: Reload POM · IntelliJ: Reload All Maven Projects).
