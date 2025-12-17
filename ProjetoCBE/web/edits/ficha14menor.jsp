<!-- =========================== EDIT =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha14MenorController" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha14Menor" %>
<%
    Ficha14MenorController fichaController = new Ficha14MenorController();
    int idFicha = Integer.parseInt(request.getParameter("idMenor"));
    Ficha14Menor ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 14</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha14.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Atualização Ficha 14 - Fundos de Investimento</h2>

                <p>Participação <b>menor</b> que 10%</p>

                <form action="<%=request.getContextPath()%>/ficha14/menor" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>

                    <div class="label-container">
                        <label for="pais">País: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Escolher o país onde está constituído o fundo no exterior. Não é permitido que o país selecionado seja 'Brasil'.</p>
                    <select id="pais" name="pais" required>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}"
                                    ${pais.getId() == ficha.getPais().getId() ? 'selected' : ''}>
                                ${pais.getNome()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda em que está denominado o patrimônio do fundo. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select id="moeda" name="moeda" required>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}"
                                    ${moeda.getId() == ficha.getMoeda().getId() ? 'selected' : ''}>
                                ${moeda.getSigla()} | ${moeda.getNome()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="valor">Valor de participação na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor de participação no fundo na data-base. Deve ser informado apenas o valor da participação do declarante no capital social do fundo. Este campo deve ser maior que zero.</p>
                    <input type="text" id="valor" name="valor" required placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getValorParticipacao())}">

                    <div class="label-container">
                        <label for="rendimentos">Rendimentos distribuídos ao declarante: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor do rendimento que foi efetivamente distribuído ao declarante. Este campo deve ser maior ou igual a zero. ATENÇÃO: O valor do campo 'Rendimentos distribuídos ao declarante' é um fluxo auferido somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base.</p>
                    <input type="text" id="rendimentos" name="rendimentos" required placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getRendimentoDistribuido())}">

                    <input type="submit" value="Salvar" class="btn salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
    </body>
</html>