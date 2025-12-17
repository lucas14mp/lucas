<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Relatórios</title>
        <link rel="stylesheet" href="resources/css/style.css"/>
    </head>
    <body>
        <%@include file="topo.jsp"%>
        <form action="relatorio.jsp">
            <div class="container-filtro-trimeste">
                <h1>Selecione ano e trimestre referentes:</h1>
                <br>
                <div class="div-opcoes">
                    <select name="ano" id="ano">
                    </select>
                    <br>
                    <select name="trimestre" id="trimestre">
                    </select>
                </div>
                <br>
                <button class="btn" type="submit">Buscar</button>
            </div>
        </form>
       <script src="resources/js/filtroRelatorio.js"></script>
    </body>
</html>