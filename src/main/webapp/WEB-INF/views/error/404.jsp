<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>No encontrado | Monitoreo Sismico v0.1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
<main class="contenedor">
    <header>
        <span class="version">Monitoreo Sismico v0.1</span>
        <h1>404 - No encontrado</h1>
    </header>
    <section class="tarjeta">
        <p><c:out value="${empty mensajeError ? 'El recurso solicitado no existe.' : mensajeError}"/></p>
        <a class="boton" href="${pageContext.request.contextPath}/sismos">Volver a la lista de sismos</a>
    </section>
</main>
</body>
</html>