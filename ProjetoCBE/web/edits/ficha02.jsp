<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha02Controller" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha02" %>
<%
    Ficha02Controller fichaController = new Ficha02Controller();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha02 ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="../resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 2</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha02.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>

                <h2>Atualização Ficha 2 - <em>Brazilian Depositary Receipt</em></h2>
                <br>
                <form action="<%=request.getContextPath()%>/ficha02" method="post" class="form">
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    
                    <div class="label-container">
                        <label for="pais">País da empresa: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione o país do mercado de negociação da ação.</p>
                    <select name="pais" id="pais" required>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}"
                                    ${pais.getId() == ficha.getPais().getId() ? 'selected' : ''}>
                                ${pais.getNome()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="valor">Dividendos e outros rendimentos recebidos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe a soma dos rendimentos recebidos no período-base para o ativo informado. Se o período-base for trimestral, corresponde apenas aos três meses que compõe o trimestre. Em caso da declaração anual (31/12), corresponde aos 12 meses do ano.</p>
                    <input type="text" name="dividendos" required id="dividendos" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getDividendos())}">


                    <div class="label-container">
                        <label for="valor">Valor de mercado na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor do ativo na data-base. O valor do campo deve ser maior que zero.</p>
                    <input type="text" name="valor" required id="valor" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getValorDatabase())}">

                    <input type="submit" value="Salvar" class="btn salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
    </body>
</html>
