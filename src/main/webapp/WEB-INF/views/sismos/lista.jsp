<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sismos | Monitoreo Sismico</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
    </head>
    <body>
        <div class="barra-superior">
            <span class="marca">CSSP <span>Sistema de Gestion de Sismos</span></span>
            <a href="${pageContext.request.contextPath}/sismos">Sismos</a>
        </div>
        <main class="contenedor">
            <header>
                <h1>Lista de sismos</h1>
            </header>

            <nav class="acciones">
                <a class="boton" href="${pageContext.request.contextPath}/sismos/nuevo">+ Nuevo sismo</a>
            </nav>

            <c:if test="${param.eliminado eq 'true'}">
                <div class="alerta alerta-exito">Sismo eliminado correctamente.</div>
            </c:if>

            <section class="tarjeta">
                <h2>Sismos registrados: <c:out value="${totalSismos}"/></h2>
                <div class="tabla-scroll">
                    <table>
                        <thead>
                            <tr>
                                <th>Codigo</th><th>Fecha</th><th>Magnitud</th><th>Profundidad</th>
                                <th>Departamento</th><th>Estado</th><th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty sismos}">
                                    <tr><td colspan="7" class="vacio">No hay sismos registrados.</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${sismos}" var="sismo">
                                        <tr>
                                            <td><c:out value="${sismo.codigo}"/></td>
                                            <td><c:out value="${sismo.fechaHoraTexto}"/></td>
                                            <td><c:out value="${sismo.magnitud}"/></td>
                                            <td><c:out value="${sismo.profundidad}"/> km</td>
                                            <td><c:out value="${sismo.departamento}"/></td>
                                            <td><c:out value="${sismo.estado}"/></td>
                                            <td class="acciones-tabla">
                                                <a href="${pageContext.request.contextPath}/sismos/detalle?id=${sismo.id}">Ver</a>
                                                <a href="${pageContext.request.contextPath}/sismos/editar?id=${sismo.id}">Editar</a>
                                                <a class="enlace-peligro" href="${pageContext.request.contextPath}/sismos/eliminar?id=${sismo.id}">Eliminar</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </section>
        </main>
    </body>
</html>