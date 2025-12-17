<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha09Controller" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha09" %>
<%
    Ficha09Controller fichaController = new Ficha09Controller();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha09 ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="../resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 9</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha09.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>

                <h3>Atualização Ficha 9 - Derivativo - Futuro e <em>swap</em></h3>
                <br>
                <form action="<%=request.getContextPath()%>/ficha09" method="post" class="form">
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
                    <p class="descricao">Selecionar um método de valoração para os montantes a receber ou pagar na data-base, escolhendo entre 'Marcação a mercado' ou 'Valor a receber ou pagar excluindo-se a margem de garantia.'</p>
                    <br>
                    <c:choose>
                        <c:when test="${ficha.getMetodoValoracao() == 'Marcação a mercado'}">
                            <label>
                                <input type="radio" name="resposta-metodo" value="Marcação a mercado" required checked>
                                Marcação a mercado
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="resposta-metodo" value=" Valor a receber ou pagar excluindo-se a margem de garantia" required>
                                Valor a receber ou pagar excluindo-se a margem de garantia
                            </label>
                        </c:when>
                        <c:otherwise>
                            <label>
                                <input type="radio" name="resposta-metodo" value="Marcação a mercado" required>
                                Marcação a mercado
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="resposta-metodo" value=" Valor a receber ou pagar excluindo-se a margem de garantia" required checked>
                                Valor a receber ou pagar excluindo-se a margem de garantia
                            </label>
                        </c:otherwise>
                    </c:choose>
                    <br>
                    <br>
                    
                    <div class="label-container">
                        <label for="valor">Valor receber(+) / pagar(-) na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <div class="box-moedas">
                        <div class="simbolo-moedas"> ${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite o valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
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
