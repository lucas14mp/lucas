<!-- =========================== EDIT =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha13Controller" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha13" %>
<%
    Ficha13Controller fichaController = new Ficha13Controller();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha13 ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 13</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha13.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>

                <h2>Atualização Ficha 13 - Empréstimo não-intercompanhia</h2>
                <form action="<%=request.getContextPath()%>/ficha13" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>

                    <div class="label-container">
                        <label for="pais">País: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Escolher o país do devedor do empréstimo no exterior.</p>
                    <select name="pais" id="pais" required>
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
                    <p class="descricao">Selecionar a moeda em que está referenciado o empréstimo. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}"
                                    ${moeda.getId() == ficha.getMoeda().getId() ? 'selected' : ''}>
                                ${moeda.getNome()} | ${moeda.getSigla()}
                            </option>
                        </c:forEach>
                    </select>


                    <div class="label-container">
                        <label>Prazo original do empréstimo: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar uma opção para o prazo original do empréstimo, dividido em duas categorias: 'Até 12 meses' ou 'Mais de 12 meses'. Na hipótese de prazo flexível ou indefinido, utilize sua melhor expectativa.</p>
                    <br>
                    <c:choose>
                        <c:when test="${ficha.getPrazoEmprestimo() == 'Até 12 meses'}">
                            <label>
                                <input type="radio" name="resposta-prazo" value="Até 12 meses" required checked>
                                Até 12 meses
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="resposta-prazo" value="Mais de 12 meses" required>
                                Mais de 12 meses
                            </label>
                        </c:when>
                        <c:otherwise>
                            <label>
                                <input type="radio" name="resposta-prazo" value="Até 12 meses" required>
                                Até 12 meses
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="resposta-prazo" value="Mais de 12 meses" required checked>
                                Mais de 12 meses
                            </label>
                        </c:otherwise>
                    </c:choose>
                    <br><br>

                    <div class="label-container">
                        <label for="saldo">Saldo na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o saldo nominal do empréstimo na data-base. O saldo nominal do empréstimo consiste na soma do saldo a receber de principal, incluindo os juros incorridos e não recebidos (devidos e não recebidos). Corresponde, portanto, ao principal do empréstimo concedido inicialmente, somado a quaisquer valores concedidos posteriormente e aos juros incorridos e não recebidos, subtraídos os recebimentos (amortizações) de principal. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas"> ${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="saldo" required id="saldo" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getSaldoDatabase())}">
                    </div>

                    <div class="label-container">
                        <label for="juros">Juros recebidos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o somatório dos juros recebidos no período-base relativos ao empréstimo declarado. O valor do campo deve ser maior ou igual a zero. O fluxo deve ser auferido somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas"> ${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="juros" required id="juros" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getJurosPeriodoBase())}">
                    </div>

                    <input type="submit" value="Salvar" class="btn salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>
