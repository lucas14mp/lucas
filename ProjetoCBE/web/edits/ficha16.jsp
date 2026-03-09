<!-- =========================== EDIT =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha16Controller" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha16" %>
<%
    Ficha16Controller fichaController = new Ficha16Controller();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha16 ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 16</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha16.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>

                <h2>Atualização Ficha 16 - Outros direitos</h2>
                <br>
                <form action="<%=request.getContextPath()%>/ficha16" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>

                    <div class="label-container">
                        <label for="direitos">Tipo de outros direitos: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione um item dentre as seguintes opções.</p>
                    <select name="direitos" id="direitos" required>

                        <option value="" selected>Selecione o tipo de outros direitos</option>
                        <option value="Bens" ${ficha.getTipoOutrosDireito() == 'Bens' ? 'selected' : ''}>Bens (exceto bens imóveis)</option>
                        <option value="Crédito de imposto" ${ficha.getTipoOutrosDireito() == 'Crédito de imposto' ? 'selected' : ''}>Crédito de imposto</option>
                        <option value="Direitos ou recebíveis que não se enquadram em créditos comerciais" ${ficha.getTipoOutrosDireito() == 'Direitos ou recebíveis que não se enquadram em créditos comerciais' ? 'selected' : ''}>Direitos ou recebíveis que não se enquadram em créditos comerciais</option>
                        <option value="Dividendos e outros reembolsos a receber"  ${ficha.getTipoOutrosDireito() == 'Dividendos e outros reembolsos a receber' ? 'selected' : ''}>Dividendos e outros reembolsos a receber</option>
                        <option value="Moedas virtuais" ${ficha.getTipoOutrosDireito() == 'Moedas virtuais' ? 'selected' : ''}>Moedas virtuais</option>
                        <option value="Previdência" ${ficha.getTipoOutrosDireito() == 'Previdência' ? 'selected' : ''}>Previdência</option>
                        <option value="Salários" ${ficha.getTipoOutrosDireito() == 'Salários' ? 'selected' : ''}>Salários</option>
                        <option value="Seguros" ${ficha.getTipoOutrosDireito() == 'Seguros' ? 'selected' : ''}>Seguros</option>
                        <option value="Sinistros ocorridos e indenizações a receber" ${ficha.getTipoOutrosDireito() == 'Sinistros ocorridos e indenizações a receber' ? 'selected' : ''}>Sinistros ocorridos e indenizações a receber</option>
                        <option value="Trust ou Fundação" ${ficha.getTipoOutrosDireito() == 'Trust ou Fundação' ? 'selected' : ''}>Trust ou Fundação</option>

                    </select>

                    <div class="label-container">
                        <label for="pais">País: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione o país do ativo</p>
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
                    <p class="descricao">Selecionar a moeda original em que está referenciado o valor do ativo. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
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
                    <p class="descricao">Informe o valor de mercado do título de dívida na data-base. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
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
