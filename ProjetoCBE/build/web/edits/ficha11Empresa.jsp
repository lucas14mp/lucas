<!-- =========================== EDIT =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="ficha11MaiorController" class="br.com.bb.cbe.controllers.Ficha11MaiorController"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha11EmpresaController" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha11Controle" %>
<%
    int idFicha = Integer.parseInt(request.getParameter("id"));
    int idFichaMaior = Integer.parseInt(request.getParameter("idFichaMaior"));
    Ficha11EmpresaController fichaController = new Ficha11EmpresaController();
    Ficha11Controle ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Atualização Ficha 11 - Empresas</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/empresas-controladas11.jsp?id=${ficha.getFicha11Controladora().getId()}"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Atualização Ficha 11 - Empresas - Participação no capital</h2>

                <form action="<%=request.getContextPath()%>/ficha11Empresa" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>
                    <input type="number" name="idFichaMaior" hidden value="<%=idFichaMaior%>"/>

                    <div class="label-container">
                        <label for="nomeControlador">Empresa controladora:</label>
                    </div>
                    <input type="text" id="nomeControlador" name="nomeControlador" required disabled placeholder="Digite o nome da empresa" value="${ficha.getFicha11Controladora().getEmpresa().getNome()}">

                    <div class="label-container">
                        <label for="nome">Nome da empresa controlada: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o nome da empresa controlada.</p>
                    <input type="text" id="nome" name="nome" required placeholder="Digite o nome da empresa" value="${ficha.getNome()}">

                    <div class="label-container">
                        <label for="pais">Selecionar país: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o país da empresa controlada.</p>
                    <select id="pais" name="pais" required>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}"
                                    ${pais.getId() == ficha.getPais().getId() ? 'selected' : ''}>
                                ${pais.getNome()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="atividade">Atividade econômica principal: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione a atividade econômica principal da empresa controlada, independentemente da atividade do fundo ou da declarante.</p>
                    <select id="atividade" name="atividade">
                        <option value="" selected>Selecione a atividade econômica</option>
                        <option value="Negociação de títulos" ${ficha.getAtividadeEcn() == 'Negociação de títulos' ? 'selected' : ''}>Negociação de títulos</option>
                        <option value="Viagens e Turismo" ${ficha.getAtividadeEcn() == 'Viagens e Turismo' ? 'selected' : ''}>Viagens e Turismo</option>
                        <option value="Recuperação de créditos" ${ficha.getAtividadeEcn() == 'Recuperação de créditos' ? 'selected' : ''}>Recuperação de créditos</option>
                        <option value="Financeira" ${ficha.getAtividadeEcn() == 'Financeira' ? 'selected' : ''}>Bens</option>
                        <option value="Gestão de Fundos" ${ficha.getAtividadeEcn() == 'Gestão de Fundos' ? 'selected' : ''}>Gestão de Fundos</option>
                    </select>

                    <div class="label-container">
                        <label for="participacao">Percentual de participação no capital social: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o percentual de participação no capital social que a empresa no exterior possui da controlada. Informe um valor maior que zero e menor ou igual a 100. Note que deve ser informado o percentual como múltiplo de 100, por exemplo, o valor 15 representa 15%. O percentual que a empresa ou fundo no exterior (no qual o declarante possui participação direta) detém da controlada. Ex.: a participação em “B2” é de 55% multiplicada por 65%: 36%</p>
                    <input type="text" id="participacao" name="participacao" placeholder="Digite um valor entre 1 e 100" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${numeroUtils.doubleToString(ficha.getParticipacaoCapital())}">

                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda em que está denominado o patrimônio do fundo. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select id="moeda" name="moeda" required>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}"
                                    ${moeda.getId() == ficha.getMoeda().getId() ? 'selected' : ''}>
                                ${moeda.getNome()} | ${moeda.getSigla()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="patrimonio">Patrimônio líquido total na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor total do patrimônio líquido da empresa controlada na data-base. Este campo pode assumir valores positivos, nulos ou negativos.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="patrimonio" name="patrimonio" placeholder="Digite o valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${ficha.getPatrimonioLiquido() != -0.01 ? numeroUtils.doubleToString(ficha.getPatrimonioLiquido()) : ''}">
                    </div>
                    <div class="label-container">
                        <label for="valor">Valor de mercado na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Estimar o valor de mercado da empresa a partir de métodos de valoração preferencialmente distintos do patrimônio líquido.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="valor" name="valor" placeholder="Digite o valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${ficha.getValorMercado() != -0.01 ? numeroUtils.doubleToString(ficha.getValorMercado()) : ''}">
                    </div>
                    <div class="label-container">
                        <label>A empresa está ao final da cadeia de controle? <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Responder afirmativamente caso a empresa esteja no último nível de seu ramo da cadeia de controle (ex.: empresa 'B2').</p>
                    <br>
                    <c:choose>
                        <c:when test="${ficha.isFinalCadeia()}">
                            <label>
                                <input type="radio" name="final" value="true" checked>
                                Sim
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="final" value="false">
                                Não
                            </label>
                        </c:when>
                        <c:otherwise>
                            <label>
                                <input type="radio" name="final" value="true">
                                Sim
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="final" value="false" checked>
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
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>