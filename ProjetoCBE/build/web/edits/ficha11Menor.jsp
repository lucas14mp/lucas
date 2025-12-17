<!-- =========================== EDIT =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha11MenorController" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha11Menor" %>
<%
    Ficha11MenorController fichaController = new Ficha11MenorController();
    int idFicha = Integer.parseInt(request.getParameter("idMenor"));
    Ficha11Menor ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 11</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha11.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Atualização Ficha 11 - Empresas - Participação no capital</h2>

                <p>Participação <b>menor</b> que 10%</p>

                <form action="<%=request.getContextPath()%>/ficha11/menor" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>

                    <div class="label-container">
                        <label for="pais">País da empresa no exterior: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Escolher o país da empresa no exterior. Não é permitido que o país selecionado seja 'Brasil'.</p>
                    <select id="pais" name="pais" required>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}"
                                    ${pais.getId() == ficha.getPais().getId() ? 'selected' : ''}>
                                ${pais.getNome()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="metodoValoracao">Método de valoração: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar um método de valoração para a participação na empresa na data-base, escolhendo entre 'Avaliação por especialista', 'Fluxo de caixa descontado', 'Negociação recente de parcela do capital' e 'Valor patrimonial'.</p>
                    <select id="metodoValoracao" name="metodoValoracao" required>
                        <option value="" selected>Selecione o método</option>
                        <option value="Avaliação por especialista" ${ficha.getMetodoValoracao() == 'Avaliação por especialista' ? 'selected' : ''}>Avaliação por especialista</option>
                        <option value="Fluxo de caixa descontado" ${ficha.getMetodoValoracao() == 'Fluxo de caixa descontado' ? 'selected' : ''}>Fluxo de caixa descontado</option>
                        <option value="Negociação recente de parcela do capital" ${ficha.getMetodoValoracao() == 'Negociação recente de parcela do capital' ? 'selected' : ''}>Negociação recente de parcela do capital</option>
                        <option value="Valor patrimonial" ${ficha.getMetodoValoracao() == 'Valor patrimonial' ? 'selected' : ''}>Valor patrimonial</option>
                    </select>

                    <div class="label-container">
                        <label for="moeda">Moeda do país da empresa no exterior: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda em que está referenciada a participação na empresa. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select id="moeda" name="moeda" required>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}"
                                    ${moeda.getId() == ficha.getMoeda().getId() ? 'selected' : ''}>
                                ${moeda.getNome()} | ${moeda.getSigla()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="valorParticipacao">Valor de participação na empresa na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor de participação na empresa na data-base, conforme o método de valoração escolhido anteriormente. Deve ser informado apenas o valor da participação do declarante no capital social da empresa. Este campo deve ser maior que zero.</p>

                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="valorParticipacao" name="valorParticipacao" required placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getValorParticipacao())}">
                    </div>
                    <div class="label-container">
                        <label for="lucroDistribuido">Lucro distribuído ao declarante: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor do lucro que foi efetivamente distribuído ao declarante no período-base. Este campo deve ser maior ou igual a zero. ATENÇÃO: O valor deste campo é um fluxo auferido somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="lucroDistribuido" name="lucroDistribuido" required placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getLucroDistribuido())}">
                    </div>

                    <input type="submit" value="Salvar" class="btn salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>
