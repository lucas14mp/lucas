<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha10Controller" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha10" %>
<%
    Ficha10Controller fichaController = new Ficha10Controller();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha10 ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="../resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 10</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha10.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>

                <h3>Atualização Ficha 10 - Derivativo - Opção</h3>
                <br>
                <form action="<%=request.getContextPath()%>/ficha10" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>
                    
                    <div class="label-container">
                        <label for="pais">País: <span class="asterisco">*</span></label>
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
                        <label>Método de valoração: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar um método de valoração para o valor na data-base, escolhendo entre “Valor intrínseco”, o valor imediato de uma opção em relação ao preço do ativo-objeto, e “Valor extrínseco”, parcela do prêmio de uma opção atribuída ao risco ou ao custo de oportunidade e expectativas sobre a evolução do ativo-objeto.</p>
                    <br>
                    <c:choose>
                        <c:when test="${ficha.getMetodoValoracao() == 'Valor intrínseco'}">
                            <label>
                                <input type="radio" name="metodo" value="Valor intrínseco" required checked>
                                Valor intrínseco
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="metodo" value=" Valor extrínseco" required>
                                Valor extrínseco
                            </label>
                        </c:when>
                        <c:otherwise>
                            <label>
                                <input type="radio" name="metodo" value="Valor intrínseco" required>
                                Valor intrínseco
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="metodo" value=" Valor extrínseco" required checked>
                                Valor extrínseco
                            </label>
                        </c:otherwise>
                    </c:choose>
                    <br>
                    <br>
                    <div class="label-container">
                        <label for="valor">Valor na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <div class="box-moedas">
                        <div class="simbolo-moedas"> ${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    </div>
                    <br>
                    <br>


                    <input type="submit" value="Salvar" class="btn salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>
