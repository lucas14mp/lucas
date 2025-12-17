<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 19</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha19.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 19 - Exportação de mercadoria</h2>

                <p>
                    Devem ser declarados nesta ficha títulos de dívida emitidos por empresas no exterior não pertencentes ao mesmo grupo econômico do declarante.
                </p>

                <p>
                    A declarante tenha efetuado exportações de mercadorias (serviços não estão incluídos), ao longo do trimestre ou ano a que se refere a declaração.
                </p>

                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha19" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>

                    <div class="label-container">
                        <label>A empresa declarante exportou mercadorias durante o período-base da declaração? <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Responder "Sim" caso a declarante tenha efetuado exportações de mercadorias (serviços não estão incluídos), ao longo do trimestre ou ano a que se refere a declaração. Responder "Não" caso contrário.</p>
                    <br>
                    <label>
                        <input type="radio" name="resposta-exportacao" value="Sim" required>
                        Sim
                    </label>
                    <br>
                    <br>
                    <label>
                        <input type="radio" name="resposta-exportacao" value="Não" required>
                        Não
                    </label>
                    <br>
                    <br>

                    <input type="submit" value="Salvar" class="btn salvar" id="salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
    </body>
</html>
