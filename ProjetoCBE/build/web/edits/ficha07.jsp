<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha07Controller" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha07" %>
<%
    Ficha07Controller fichaController = new Ficha07Controller();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha07 ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="../resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 7</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha07.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>

                <h3>Atualização Ficha 7 - <em>Depositary Receipt</em> - Empresa não-brasileira</h3>
                <br>
                <form action="<%=request.getContextPath()%>/ficha07" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>
                    
                    <div class="label-container">
                        <label for="pais-negociacao">País de negociação: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione o país de negociação do certificado.</p>
                    <select name="pais-negociacao" id="pais-negociacao" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="paisNegociacao">
                            <option value="${paisNegociacao.getId()}"
                                    ${paisNegociacao.getId() == ficha.getPaisNegociacao().getId() ? 'selected' : ''}>
                                ${paisNegociacao.getNome()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="pais-emissor">País da empresa não-brasileira: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione o país da empresa não-brasileira emissor do certificado.</p>
                    <select name="pais-emissor" id="pais-emissor" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="paisEmissor">
                            <option value="${paisEmissor.getId()}"
                                    ${paisEmissor.getId() == ficha.getPaisEmissor().getId() ? 'selected' : ''}>
                                ${paisEmissor.getNome()}
                            </option>
                        </c:forEach>
                    </select>
                    
                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione a moeda original em que está referenciado o valor do ativo. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}"
                                    ${moeda.getId() == ficha.getMoeda().getId() ? 'selected' : ''}>
                                ${moeda.getNome()} | ${moeda.getSigla()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="valor">Valor de mercado na data-base: <span class="asterisco">*</span></label>
                    </div>
                     <div class="box-moedas">
                        <div class="simbolo-moedas"> ${moeda.getSimbolo()}</div>
                    <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{3})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    </div>
                    <div class="label-container">
                        <label for="dividendos">Dividendos e outros rendimentos recebidos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <div class="box-moedas">
                        <div class="simbolo-moedas"> ${moeda.getSimbolo()}</div>
                    <input class="input-moedas" type="text" name="dividendos" required id="dividendos" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getDividendos())}">
                    </div>

                    <input type="submit" value="Salvar" class="btn salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>
