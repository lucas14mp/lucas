<!-- =========================== EDIT =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha18Controller" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha18" %>
<%
    Ficha18Controller fichaController = new Ficha18Controller();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha18 ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 18</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha18.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>

                <h2>Atualização Ficha 18 - Título de dívida não-intercompanhia</h2>
                <br>
                <form action="<%=request.getContextPath()%>/ficha18" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>

                    <div class="label-container">
                        <label for="pais">País emissor: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Escolher o país do emissor do título de dívida. No caso de grupos econômicos de origem de capital brasileiro, que emitem títulos a partir de suas subsidiárias no exterior, deve-se considerar o país da subsidiária, e não da matriz brasileira.</p>
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
                    <p class="descricao">Selecione a moeda em que está referenciado o título de dívida. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}"
                                    ${moeda.getId() == ficha.getMoeda().getId() ? 'selected' : ''}>
                                ${moeda.getNome()} | ${moeda.getSigla()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label>Prazo original do título de dívida: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar uma opção para o prazo original do título de dívida, dividido em duas categorias: “Até 12 meses” ou “Mais de 12 meses”. Na hipótese de prazo flexível ou indefinido, utilize a melhor expectativa.</p>
                    <br>
                    <c:choose>
                        <c:when test="${ficha.getPrazoDivida() == 'Até 12 meses'}">
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
                        <label for="valor">Valor de mercado: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor de mercado do título de dívida na data-base. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getValorMercado())}">
                    </div>

                    <div class="label-container">
                        <label for="valor">Juros recebidos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o somatório dos juros recebidos no período-base relativos ao título declarado. O valor do campo deve ser maior ou igual a zero. O valor do campo 'Juros recebidos no período-base' é um fluxo auferido somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos base.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="juros" required id="juros" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getJurosRecebidos())}">
                    </div>
                    
                    <label for="justificativa_gestor">Justificativa do Gestor: <span class="asterisco">*</span></label></label>
                    </div>
                    <p class="descricao">Caso necessário, edite a justificativa para a divergência de valores.</p>

                    <textarea 
                        name="justificativa_gestor" 
                        id="justificativa_gestor" 
                        rows="5" 
                        style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; resize: vertical;"
                        placeholder="Nenhuma justificativa registrada."
                        >${ficha.justificativaGestor}</textarea>
                    <br><br>

                    <input type="submit" value="Salvar" class="btn salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>
