<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="/topo.jsp" %>
        <article class="article-erro">
            <h1>ERROR 404</h1>
            <p>Página não encontrada</p>
            <a href="/ProjetoCBE/index.jsp"><input type="button" value="Voltar" class="btn"></a>
        </article>
    </body>
</html>
