<!-- =========================== EDIT =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha14MaiorController" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha14Maior" %>
<%
    Ficha14MaiorController fichaController = new Ficha14MaiorController();
    int idFicha = Integer.parseInt(request.getParameter("idMaior"));
    Ficha14Maior ficha = fichaController.getFichaById(idFicha);
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

        <p>Participação <b>maior</b> que 10%</p>

        <form action="<%=request.getContextPath()%>/ficha14/maior" method="post" class="form">
          <input type="text" name="tipo-requisicao" value="edit" hidden>
          <input type="number" name="id" hidden value="<%=idFicha%>"/>

          <div class="label-container">
            <label for="empresa">Selecionar fundo: <span class="asterisco">*</span></label>
          </div>
          <p class="descricao">Escolher entre os fundos no exterior previamente cadastrados, o fundo no qual possui participação igual ou superior a 10%.</p>
          <select id="empresa" name="empresa" required>
            <c:forEach items="${empresaController.listarEmpresas()}" var="empresa">
              <option value="${empresa.getId()}"
                      ${empresa.getId() == ficha.getEmpresa().getId() ? 'selected' : ''}>
                ${empresa.getNome()}
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
            <label for="patrimonio">Patrimônio líquido na data-base: <span class="asterisco">*</span></label>
          </div>
          <p class="descricao">Informe o valor total do patrimônio líquido do fundo na data-base. Este campo pode assumir valores positivos, nulos ou negativos.</p>
          <input type="text" id="patrimonio" name="patrimonio" required placeholder="Digite o valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getPatrimonioLiquido())}">

          <div class="label-container">
            <label for="percentual">Percentual de participação no patrimônio: <span class="asterisco">*</span></label>
          </div>
          <p class="descricao">Informe o percentual de participação detido pelo declarante no patrimônio do fundo. Informe um valor entre 10 e 100. Deve ser informado o percentual como múltiplo de 100, por exemplo, o valor 15 representa 15%.</p>
          <input type="text" id="percentual" name="percentual" required placeholder="Digite o valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getParticipacaoPatrimonio())}">

          <div class="label-container">
            <label for="rendimento-fundo">Rendimentos (positivos ou negativos) do fundo no período-base: <span class="asterisco">*</span></label>
          </div>
          <p class="descricao">Informe o valor total auferido como rendimentos pelo fundo no período-base. Este campo pode assumir valores positivos, nulos ou negativos. ATENÇÃO: Os valores dos campos 'Rendimentos (positivos ou negativos) do fundo no período-base' e 'Rendimentos distribuídos no período-base' são fluxos auferidos somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base.</p>
          <input type="text" id="rendimento-fundo" name="rendimento-fundo" required placeholder="Digite o valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getRendimentosFundo())}">

          <div class="label-container">
            <label for="rendimento-distribuido">Rendimentos distribuídos no período-base: <span class="asterisco">*</span></label>
          </div>
          <p class="descricao">Informe o valor total distribuído em rendimentos pelo fundo no período-base. Este campo deve ser maior ou igual a zero.</p>
          <input type="text" id="rendimento-distribuido" name="rendimento-distribuido" required placeholder="Digite o valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getRendimentosDistribuidos())}">

          <div class="label-container">
            <label>O fundo no exterior controla outras empresas direta ou indiretamente, também no exterior, que estão ao final da cadeia de controle? <span class="asterisco">*</span></label>
          </div>
          <p class="descricao">Informe 'Sim' ou 'Não' para a pergunta. Caso seja respondido afirmativamente, será necessário o preenchimento de informações adicionais.</p>
          <c:choose>
            <c:when test="${ficha.isControlaEmpresas()}">
              <label>
                <input type="radio" name="controla" value="true" required checked>
                Sim
              </label>
              <br>
              <br>
              <label>
                <input type="radio" name="controla" value="false" required>
                Não
              </label>
            </c:when>
            <c:otherwise>
              <label>
                <input type="radio" name="controla" value="true" required>
                Sim
              </label>
              <br>
              <br>
              <label>
                <input type="radio" name="controla" value="false" required checked>
                Não
              </label>
            </c:otherwise>
          </c:choose>
          <br>
          <br>

          <input type="submit" value="Salvar" class="btn salvar">
        </form>
      </article>
    </main>
    <script src="/ProjetoCBE/resources/js/temas.js"></script>
  </body>
</html>
