<!-- =========================== EDIT =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha01Controller" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha01" %>
<%
    Ficha01Controller fichaController = new Ficha01Controller();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha01 ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 1</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha01.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>

                <h2>Atualização Ficha 1 - Ações negociadas em bolsa</h2>
                <br>
                <form action="<%=request.getContextPath()%>/ficha01" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>

                    <div class="label-container">
                        <label for="pais">Mercado de negociação: <span class="asterisco">*</span></label>
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
                        <label for="valor">Valor na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <div class="box-moedas">
                        <div class="simbolo-moedas"> ${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="${ficha.getValorDatabase()}" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    </div>

                    <div class="label-container">
                        <label for="dividendos">Dividendos recebidos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <div class="box-moedas">
                        <div class="simbolo-moedas"> ${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="dividendos" required id="dividendos" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getDividendos())}">
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
        <!--<script src="/ProjetoCBE/resources/js/moedasEdit.js"></script>-->
        <script>

        </script>
    </body>
</html>