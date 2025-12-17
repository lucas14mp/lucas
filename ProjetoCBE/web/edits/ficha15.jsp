<!-- =========================== EDIT =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha15Controller" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha15" %>
<%
    Ficha15Controller fichaController = new Ficha15Controller();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha15 ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 15</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha15.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>

                <h2>Atualização Ficha 15 - Imóvel</h2>
                <form action="<%=request.getContextPath()%>/ficha15" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>

                    <div class="label-container">
                        <label for="pais">País do imóvel: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Escolher o país do imóvel no exterior.</p>
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
                    <p class="descricao">Selecionar a moeda original em que está referenciado o valor do imóvel e saldo devedor. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}"
                                    ${moeda.getId() == ficha.getMoeda().getId() ? 'selected' : ''}>
                                ${moeda.getSigla()} | ${moeda.getNome()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="metodo">Método de valoração: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar um método de valoração do valor na data-base, escolhendo entre 'Valor de aquisição', 'Valor de aquisição com benfeitorias' e 'Valor de mercado.'</p>
                    <select name="metodo" id="metodo" required>
                        <option value="">Selecione o método de valoração</option>
                        <option value="Valor de aquisição" ${ficha.getMetodoValoracao() == 'Valor de aquisição' ? 'selected' : ''}>Valor de aquisição</option>
                        <option value="Valor de aquisição com benfeitorias" ${ficha.getMetodoValoracao() == 'Valor de aquisição com benfeitorias' ? 'selected' : ''}>Valor de aquisição com benfeitorias</option>
                        <option value="Valor de mercado" ${ficha.getMetodoValoracao() == 'Valor de mercado' ? 'selected' : ''}>Valor de mercado</option>
                    </select>

                    <div class="label-container">
                        <label for="valor">Valor na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o valor do imóvel na data-base, conforme o método de valoração escolhido. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                    <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    </div>

                    <div class="label-container">
                        <label>O imóvel está quitado? <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione uma opção. Caso escolha 'Sim', o campo “Saldo devedor na data-base” será desabilitado. Caso escolha não, o saldo devedor deverá ser preenchido.</p>
                    <c:choose>
                        <c:when test="${ficha.isImovelQuitado()}">
                            <label>
                                <input type="radio" name="quitado" value="true" required checked>
                                Sim
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="quitado" value="false" required>
                                Não
                            </label>
                        </c:when>
                        <c:otherwise>
                            <label>
                                <input type="radio" name="quitado" value="true" required>
                                Sim
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="quitado" value="false" required checked>
                                Não
                            </label>
                        </c:otherwise>
                    </c:choose>
                    <br>
                    <br>

                    <div class="label-container">
                        <label for="devedor">Saldo devedor na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o saldo devedor de financiamento remanescente na data-base. Caso tenha respondido que o imóvel está quitado, este campo ficará desabilitado. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                    <input class="input-moedas" type="text" name="devedor" required id="devedor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getSaldoDatabase())}">
                    </div>

                    <div class="label-container">
                        <label for="alugueis">Aluguéis recebidos no período: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o total dos aluguéis recebidos pelo imóvel no período base. O valor do campo deve ser maior ou igual a zero. O fluxo deve ser apurado somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                    <input class="input-moedas" type="text" name="alugueis" required id="alugueis" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getAluguelRecebido())}">
                    </div>

                    <input type="submit" value="Salvar" class="btn salvar" id="btnSalvarNaoQuitado">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/ficha15.js"></script>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>
