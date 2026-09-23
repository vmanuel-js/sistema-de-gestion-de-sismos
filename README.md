# Sistema de Gestión de Sismos — Estado del Sprint 1 

## HU completadas en este avance

| HU | Descripción | Archivos |
|----|-------------|----------|
| HU-01 | Listar sismos | `servlet/SismoListarServlet.java`, `WEB-INF/views/sismos/lista.jsp` |
| HU-02 | Registrar un nuevo sismo | `servlet/SismoNuevoServlet.java`, `WEB-INF/views/sismos/formulario.jsp` |
| HU-05 | Ver el detalle de un sismo | `servlet/SismoDetalleServlet.java`, `WEB-INF/views/sismos/detalle.jsp` |
| HU-08 | Gestionar el estado del evento | Lógica en `model/Sismo.java` (constantes + validación), aplicada en el registro |

También se agregó `WEB-INF/views/error/404.jsp` (usado cuando se busca un sismo con un id que no existe) y `css/estilos.css` (estilo visual compartido por todas las pantallas del módulo).

## Cómo está armado (por si necesitan tocar algo)

- **Modelo:** `model/Sismo.java`. Usa un solo campo `LocalDateTime fechaHora` (no fecha y hora separadas). El estado se guarda como texto legible: `"Registrado"`, `"En evaluacion"`, `"En seguimiento"`, `"Cerrado"` — están como constantes públicas (`Sismo.ESTADO_REGISTRADO`, etc.) y hay un método `Sismo.esEstadoValido(String)` ya listo para validar cualquier valor que llegue de un formulario.
- **Repositorio:** `repository/SismoRepository.java`. En memoria (`CopyOnWriteArrayList`), con 5 sismos de ejemplo cargados al iniciar. `listar()` ya devuelve los sismos ordenados por fecha descendente.
- **Listener:** `listener/AplicacionListener.java` crea el repositorio compartido en el `ServletContext` bajo la clave `AplicacionListener.REPOSITORIO_SISMOS`. **Todos los servlets del módulo (incluidos los de Editar y Eliminar que faltan) deben tomar el repositorio de ahí**, no crear uno nuevo.
- **Validaciones (HU-03/HU-04):** están dentro de `SismoNuevoServlet.doPost()`, acumulando todos los errores en una lista antes de responder (para mostrarlos juntos en el formulario, como pide el prototipo). Si ya tienen una clase de validación aparte, avísenme y muevo esa lógica ahí para no duplicar código cuando hagan Editar.

## Pendiente de integración con el resto del equipo

Estos puntos son importantes para que sus HU no rompan las mías (o viceversa):

1. **Departamento / Provincia / Distrito (HU-09 y HU-21 — Jose/quien las tenga):** por ahora son campos de texto libre (`<input type="text">`) en el formulario, sin catálogo ni cascada. Cuando esté listo el repositorio de ubicaciones, solo hay que:
   - Cambiar esos 3 `<input>` en `formulario.jsp` por `<select>`.
   - En `SismoNuevoServlet`, validar contra el catálogo en vez de solo comprobar que no esté vacío.
   - El modelo `Sismo` no cambia: sigue guardando `departamento`, `provincia`, `distritoReferencia` como `String`.

2. **Estación (HU-10/HU-11 — quien las tenga):** el modelo `Sismo` **todavía no tiene** un campo para relacionar la estación. Si van a agregarlo, avísenme el nombre exacto del campo/getter que van a usar para no chocar cuando yo toque `Sismo.java` de nuevo (por ejemplo, para Eliminar).

3. **Editar (HU-06 — Nick):** el `<select>` de estado con las 4 opciones fijas debe usar `Sismo.ESTADOS_VALIDOS` (el arreglo ya definido en el modelo) en vez de escribir los strings de nuevo, para evitar que se desincronicen los valores válidos entre pantallas.

4. **Eliminar (HU-07 — Victor, pendiente):** falta por hacer. Va a necesitar el mismo repositorio (`SismoRepository.eliminar(id)` ya existe y funciona) y revisar la regla de "no se puede eliminar si está En seguimiento".

5. **Nombres de URL usados hasta ahora** (para que Nick/Jose no inventen otros):
   - `GET /sismos` → listar
   - `GET /sismos/nuevo` y `POST /sismos/nuevo` → registrar
   - `GET /sismos/detalle?id=X` → ver detalle
   - `GET /sismos/editar?id=X` y `POST /sismos/editar` → **(pendiente, para Nick — HU-06)**
   - `GET /sismos/eliminar?id=X` y `POST /sismos/eliminar` → **(pendiente, para Victor — HU-07)**

## Dependencias agregadas al `pom.xml`

Se agregaron 2 dependencias de JSTL (`jakarta.servlet.jsp.jstl-api` y `org.glassfish.web:jakarta.servlet.jsp.jstl`) porque sin ellas las JSP con `<c:...>` tiraban `JasperException` al no encontrar el TLD. Si a alguien más le sale ese mismo error al bajar la última versión del repo, es que le falta recargar Maven después del `pull`.

## Cómo probarlo localmente

1. `mvn clean package` (o el botón equivalente del IDE).
2. Desplegar el WAR en Tomcat / reiniciar el servidor.
3. Abrir `http://localhost:8080/<nombre-del-contexto>/sismos`.
