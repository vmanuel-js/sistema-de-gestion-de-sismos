<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nuevo sismo | CSSP</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<div class="barra-superior">
    <span class="marca">CSSP <span>Sistema de Gestion de Sismos</span></span>
    <a href="${pageContext.request.contextPath}/sismos">Sismos</a>
</div>
<main class="contenedor">
    <p class="breadcrumb"><a href="${pageContext.request.contextPath}/sismos">Sismos</a> / Nuevo sismo</p>
    <h1>Nuevo sismo</h1>
    <p class="subtitulo">Los campos marcados con * son obligatorios.</p>

    <c:if test="${not empty errores}">
        <div class="alerta alerta-error">
            No se pudo registrar el sismo. Revise los siguientes datos:
            <ul>
                <c:forEach items="${errores}" var="err">
                    <li><c:out value="${err}"/></li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <section class="tarjeta">
        <form method="post" action="${pageContext.request.contextPath}/sismos/nuevo">

            <div class="seccion-titulo">Datos del evento</div>
            <div class="formulario-grid">
                <label>
                    <span class="campo-obligatorio">Codigo</span>
                    <input name="codigo" value="<c:out value='${codigoIngresado}'/>">
                </label>
                <label>
                    <span class="campo-obligatorio">Fecha y hora</span>
                    <input name="fechaHora" type="datetime-local" value="<c:out value='${fechaHoraIngresada}'/>">
                    <span class="ayuda">No puede ser una fecha futura.</span>
                </label>
                <label>
                    <span>Intensidad</span>
                    <input name="intensidad" value="<c:out value='${intensidadIngresada}'/>" placeholder="Ej. III (MM)">
                </label>

                <label>
                    <span class="campo-obligatorio">Magnitud</span>
                    <input name="magnitud" type="number" step="0.1" value="<c:out value='${magnitudIngresada}'/>">
                    <span class="ayuda">Debe ser mayor que cero.</span>
                </label>
                <label>
                    <span class="campo-obligatorio">Profundidad (km)</span>
                    <input name="profundidad" type="number" step="0.1" value="<c:out value='${profundidadIngresada}'/>">
                    <span class="ayuda">No puede ser negativa.</span>
                </label>
                <label>
                    <span>Estado</span>
                    <input value="Registrado" disabled>
                    <span class="ayuda">Se asigna automaticamente al registrar.</span>
                </label>
            </div>

            <div class="seccion-titulo">Ubicacion</div>
            <div class="formulario-grid">
                <label>
                    <span class="campo-obligatorio">Latitud</span>
                    <input name="latitud" type="number" step="0.0001" value="<c:out value='${latitudIngresada}'/>">
                    <span class="ayuda">Entre -90 y 90.</span>
                </label>
                <label>
                    <span class="campo-obligatorio">Longitud</span>
                    <input name="longitud" type="number" step="0.0001" value="<c:out value='${longitudIngresada}'/>">
                    <span class="ayuda">Entre -180 y 180.</span>
                </label>
                <label>
                    <span class="campo-obligatorio">Departamento</span>
                    <input name="departamento" value="<c:out value='${departamentoIngresado}'/>">
                </label>

                <label>
                    <span class="campo-obligatorio">Provincia</span>
                    <input name="provincia" value="<c:out value='${provinciaIngresada}'/>">
                </label>
                <label>
                    <span class="campo-obligatorio">Distrito o referencia</span>
                    <input name="distritoReferencia" value="<c:out value='${distritoReferenciaIngresado}'/>">
                </label>
            </div>

            <div class="acciones" style="margin-top: 20px;">
                <button class="boton" type="submit">Guardar</button>
                <a class="boton-secundario" href="${pageContext.request.contextPath}/sismos">Cancelar</a>
            </div>
        </form>
    </section>
</main>
</body>
</html>