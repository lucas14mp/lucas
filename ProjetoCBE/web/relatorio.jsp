<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uor_equipe" value="${sessionScope.uorEquipe}" />
<jsp:useBean id="ficha01Controller" class="br.com.bb.cbe.controllers.Ficha01Controller"/>
<jsp:useBean id="ficha02Controller" class="br.com.bb.cbe.controllers.Ficha02Controller"/>
<jsp:useBean id="ficha03Controller" class="br.com.bb.cbe.controllers.Ficha03Controller"/>
<jsp:useBean id="ficha06Controller" class="br.com.bb.cbe.controllers.Ficha06Controller"/>
<jsp:useBean id="ficha07Controller" class="br.com.bb.cbe.controllers.Ficha07Controller"/>
<jsp:useBean id="ficha08Controller" class="br.com.bb.cbe.controllers.Ficha08Controller"/>
<jsp:useBean id="ficha09Controller" class="br.com.bb.cbe.controllers.Ficha09Controller"/>
<jsp:useBean id="ficha10Controller" class="br.com.bb.cbe.controllers.Ficha10Controller"/>
<jsp:useBean id="ficha11EmpresaController" class="br.com.bb.cbe.controllers.Ficha11EmpresaController"/>
<jsp:useBean id="ficha11MaiorController" class="br.com.bb.cbe.controllers.Ficha11MaiorController"/>
<jsp:useBean id="ficha11MenorController" class="br.com.bb.cbe.controllers.Ficha11MenorController"/>
<jsp:useBean id="ficha12Controller" class="br.com.bb.cbe.controllers.Ficha12Controller"/>
<jsp:useBean id="ficha13Controller" class="br.com.bb.cbe.controllers.Ficha13Controller"/>
<jsp:useBean id="ficha14EmpresaController" class="br.com.bb.cbe.controllers.Ficha14EmpresaController"/>
<jsp:useBean id="ficha14MaiorController" class="br.com.bb.cbe.controllers.Ficha14MaiorController"/>
<jsp:useBean id="ficha14MenorController" class="br.com.bb.cbe.controllers.Ficha14MenorController"/>
<jsp:useBean id="ficha15Controller" class="br.com.bb.cbe.controllers.Ficha15Controller"/>
<jsp:useBean id="ficha16Controller" class="br.com.bb.cbe.controllers.Ficha16Controller"/>
<jsp:useBean id="ficha17Controller" class="br.com.bb.cbe.controllers.Ficha17Controller"/>
<jsp:useBean id="ficha18Controller" class="br.com.bb.cbe.controllers.Ficha18Controller"/>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>
<%
    String trimestre = request.getParameter("trimestre");
    String ano = request.getParameter("ano");
    pageContext.setAttribute("trimestre", trimestre);
    pageContext.setAttribute("ano", ano);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resources/css/style.css"/>
        <title>Relatório CBE</title>
    </head>
    <body>
        <%@include file="topo.jsp"%>

        <div class="view-container">
            <div class="topo-view">
                <h1>Relatório de todas as fichas referentes ao ${trimestre}º trimestre de ${ano}</h1>
                <input type="text" id="valorTrimestre" value="<%= trimestre%>" style="display:none;">
                <input type="text" id="valorAno" value="<%= ano%>" style="display:none;">
                <div>
                    <a href="index.jsp"><input type="button" value="Voltar" class="btn" id="voltar"></a>
                    <input class="btn" type="submit" value="Imprimir" id="imprimir"/>
                </div>
            </div>
            <h4 class="topo-view" style="color: #0038a8;">Clique em ">" para visualizar a tabela que deseja.</h4>

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Lista de Empresas</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Nome</th>
                        <th>País</th>
                        <th>A empresa transaciona quase exclusivamente com pessoas ou empresas de países diferentes?</th>
                        <th>CDNR</th>
                        <th>Relação com o declarante</th>
                        <th>Nº de empregados</th>
                        <th>Atividade econômica</th>
                        <th>Detalhamento</th>
                    </tr>
                    <c:forEach items="${empresaController.listarEmpresas()}" var="empresa">
                        <tr>
                            <td>${empresa.getNome()}</td>
                            <td>${empresa.getPais().getNome()}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${empresa.isTransacionaPaisesDiferentes()}">
                                        <img src="resources/imgs/yes.png" alt="Sim" width="20">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="resources/imgs/no.png" alt="Não" width="20">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${empresa.getCdnr() == 0}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        ${empresa.getCdnr()}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${empresa.getRelacaoDeclarante()}</td>
                            <td>${empresa.getNumeroEmpregados()}</td>
                            <td>${empresa.getAtividadeEconomica()}</td>
                            <td>${empresa.getDetalhamentoAtividadeEconomica()}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 1-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 1 - Ações negociadas em bolsa</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>Mercado de negociação</th>
                        <th>Moeda</th>
                        <th>Valor</th>
                        <th>Dividendos recebidos</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                        <th>Justificativa</th>
                    </tr>
                    <c:forEach items="${ficha01Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getDividendos())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                            <td style="text-align: center; vertical-align: middle;">
                                <c:choose>
                                    <c:when test="${not empty ficha.justificativaGestor}">
                                        <img src="${pageContext.request.contextPath}/resources/imgs/justificativa.png" 
                                             style="cursor: pointer; width: 24px;"
                                             onclick="verJustificativa('<c:out value="${ficha.justificativaGestor}"/>', ${ficha.id}, 'ficha01')">
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 2-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 2 - <em>Brazilian Depositary Receipt</em></h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>País da empresa</th>
                        <th>Dividendos e outros<br>
                            rendimentos recebidos</th>
                        <th>Valor de mercado</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha02Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getDividendos())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 3-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 3 - Câmbio Manual</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>Moeda</th>
                        <th>Valor</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                        <th>Justificativa</th>
                    </tr>
                    <c:forEach items="${ficha03Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                            <td style="text-align: center; vertical-align: middle;">
                                <c:choose>
                                    <c:when test="${not empty ficha.justificativaGestor}">
                                        <img src="${pageContext.request.contextPath}/resources/imgs/justificativa.png" 
                                             style="cursor: pointer; width: 24px;"
                                             onclick="verJustificativa('<c:out value="${ficha.justificativaGestor}"/>', ${ficha.id}, 'ficha03')">
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 6-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 6 - <em>Depositary Receipt</em> - Empresa brasileira</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>País de negociação</th>
                        <th>Moeda</th>
                        <th>Valor</th>
                        <th>Dividendos e outros<br>
                            rendimentos recebidos</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha06Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getDividendos())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 7-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 7 - <em>Depositary Receipt</em> - Empresa não-brasileira</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>País de negociação</th>
                        <th>País da empresa emissora</th>
                        <th>Moeda</th>
                        <th>Valor de mercado</th>
                        <th>Dividendos e outros rendimentos recebidos</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha07Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPaisNegociacao().getNome()}</td>
                            <td>${ficha.getPaisEmissor().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getDividendos())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 8-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 8 - Depósitos à vista e a prazo</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>País</th>
                        <th>Moeda</th>
                        <th>Saldo na data-base</th>
                        <th style='width: 350px;'>Rendimentos no período-base</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                        <th>Justificativa</th>
                    </tr>
                    <c:forEach items="${ficha08Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getSaldoDatabase())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getRendimentos())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                            <td style="text-align: center; vertical-align: middle;">
                                <c:choose>
                                    <c:when test="${not empty ficha.justificativaGestor}">
                                        <img src="${pageContext.request.contextPath}/resources/imgs/justificativa.png" 
                                             style="cursor: pointer; width: 24px;"
                                             onclick="verJustificativa('<c:out value="${ficha.justificativaGestor}"/>', ${ficha.id}, 'ficha08')">
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 9-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 9 - Derivativo - Futuro e <em>swap</em></h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio"> 
                    <tr>
                        <th>Status</th>
                        <th>País responsável</th>
                        <th>Moeda</th>
                        <th>Método de valoração</th>
                        <th>Valor receber(+)/pagar(-)</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha09Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${ficha.getMetodoValoracao()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 10-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 10 - Derivativo - Opção</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>País responsável</th>
                        <th>Moeda</th>
                        <th>Método de valoração</th>
                        <th>Valor</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha10Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${ficha.getMetodoValoracao()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 11-->
            <!--Poder de voto menor que 10%-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <div>
                        <h2>Ficha 11 - Empresas - Participação no capital</h2>
                        <h3>Porcentagem de poder de voto na empresa menor que 10%</h3>
                    </div>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>País da empresa no exterior</th>
                        <th>Moeda do país da empresa no exterior</th>
                        <th>Método de valoração</th>
                        <th>Valor de participação na empresa na data-base</th>
                        <th>Lucro distribuído ao declarante</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha11MenorController.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${ficha.getMetodoValoracao()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorParticipacao())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getLucroDistribuido())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Poder de voto maior ou igual a 10%-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <div>
                        <h2>Ficha 11 - Empresas - Participação no capital</h2>
                        <h3>Porcentagem de poder de voto na empresa maior ou igual a 10%</h3>
                    </div>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <div class="table-wrapper" id="twFicha11">
                    <table class="table-lista-fichas-relatorio maior">
                        <tr>
                            <th>Status</th>
                            <th>Empresa</th>
                            <th>Esta empresa possui cotação em bolsa de valores no exterior?</th>
                            <th>Moeda do país da empresa no exterior</th>
                            <th>Método de valoração</th>
                            <th>A empresa no exterior controla outras empresas?</th>
                            <th>Valor da empresa na data-base</th>
                            <th>Patrimônio líquido total na data-base</th>
                            <th>Percentual de participação no capital social</th>
                            <th>Percentual de poder de voto</th>
                            <th>Ativo na data-base</th>
                            <th>Passivo exigível na data-base</th>
                            <th>Valor total do lucro ou prejuízo líquidos da empresa no exterior</th>
                            <th>Resultado líquido de itens não recorrentes</th>
                            <th>Resultado Líquido de reavaliações (ex. impairment):</th>
                            <th>Resultado líquido de variação cambial</th>
                            <th>Lucro distribuído no período-base</th>
                            <th class="naoImprimir">Última atualização</th>
                            <th class="naoImprimir">Funcionário</th>
                        </tr>
                        <c:forEach items="${ficha11MaiorController.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                            <tr>
                                <td>${ficha.getStatus().getStatus()}</td>
                                <td>${ficha.getEmpresa().getNome()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${ficha.isPossuiCotacaoEmBolsa()}">
                                            Sim
                                        </c:when>
                                        <c:otherwise>
                                            Não
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                                <td>${ficha.getMetodoValoracao()}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${ficha.isControlaEmpresa()}">
                                            <p>Sim</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p>Não</p>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${ ficha.getValorEmpresa() != -0.01 ? numeroUtils.doubleToString(ficha.getValorEmpresa()) : "Não informado" }</td>
                                <td>${ ficha.getPatrimonioTotal() != -0.01 ? numeroUtils.doubleToString(ficha.getPatrimonioTotal()) : "Não informado"}</td>
                                <td>${ ficha.getPorcentoParticipacaoCapital() != -0.01 ? numeroUtils.doubleToString(ficha.getPorcentoParticipacaoCapital()) : "Não informado"}</td>
                                <td>${ ficha.getPorcentoPoderVoto() != -0.01 ? numeroUtils.doubleToString(ficha.getPorcentoPoderVoto()) : "Não informado"}</td>
                                <td>${ ficha.getAtivoDatabase() != -0.01 ? numeroUtils.doubleToString(ficha.getAtivoDatabase()) : "Não informado"}</td>
                                <td>${ ficha.getPassivoExigivel() != -0.01 ? numeroUtils.doubleToString(ficha.getPassivoExigivel()) : "Não informado"}</td>
                                <td>${ ficha.getValorTotalLucroPrejuizo() != -0.01 ? numeroUtils.doubleToString(ficha.getValorTotalLucroPrejuizo()) : "Não informado"}</td>
                                <td>${ ficha.getResultadoLiquidoItensNaoRecorrentes() != -0.01 ? numeroUtils.doubleToString(ficha.getResultadoLiquidoItensNaoRecorrentes()) : "Não informado"}</td>
                                <td>${ ficha.getResultadoLiquidoReavaliacoes() != -0.01 ? numeroUtils.doubleToString(ficha.getResultadoLiquidoReavaliacoes()) : "Não informado"}</td>
                                <td>${ ficha.getResultadoLiquidoVariacaoCambial() != -0.01 ? numeroUtils.doubleToString(ficha.getResultadoLiquidoVariacaoCambial()) : "Não informado"}</td>
                                <td>${ ficha.getLucroDistribuido() != -0.01 ? numeroUtils.doubleToString(ficha.getLucroDistribuido()) : "Não informado"}</td>
                                <td class="naoImprimir">${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                                <td class="naoImprimir">
                                    ${ficha.getFuncionario().getNome()}
                                    <br>
                                    ${ficha.getFuncionario().getDependencia().getNome()}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>

            <!--Ficha 11 - Empresas controladas-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <div>
                        <h2>Ficha 11 - Empresas - Participação no capital</h2>
                        <h3>Porcentagem de poder de voto na empresa maior ou igual a 10%</h3>
                        <h3>Empresas controladas</h3>
                    </div>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <div class="table-wrapper" id="twFicha11Empresa">
                    <table class="table-lista-fichas-relatorio" >
                        <tr>
                            <th>Status</th>
                            <th>Nome da empresa controladora</th>
                            <th>Nome da empresa controlada</th>
                            <th>País da empresa controlada</th>
                            <th>Atividade econômica principal</th>
                            <th>Percentual de participação no capital social</th>
                            <th>Moeda</th>
                            <th>Patrimônio líquido total na data-base</th>
                            <th>Valor de mercado na data-base</th>
                            <th>A empresa está ao final da cadeia de controle?</th>
                            <th>Última atualização</th>
                            <th>Funcionário</th>
                        </tr>
                        <c:forEach items="${ficha11EmpresaController.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                            <tr>
                                <td>${ficha.getFicha11Controladora().getStatus().getStatus()}</td>
                                <td>${ficha.getFicha11Controladora().getEmpresa().getNome()}</td>
                                <td>${ficha.getNome()}</td>
                                <td>${ficha.getPais().getNome()}</td>
                                <td>${ficha.getAtividadeEcn()}</td>
                                <td>${numeroUtils.doubleToString(ficha.getParticipacaoCapital())}%</td>
                                <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                                <td>${ ficha.getPatrimonioLiquido() != -0.01 ? numeroUtils.doubleToString(ficha.getPatrimonioLiquido()) : "Não informado" }</td>
                                <td>${ ficha.getValorMercado() != -0.01 ? numeroUtils.doubleToString(ficha.getValorMercado()) : "Não informado" }</td>
                                <td>${ficha.isFinalCadeia() ? "Sim" : "Não"}</td>
                                <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                                <td>
                                    ${ficha.getFuncionario().getNome()}
                                    <br>
                                    ${ficha.getFuncionario().getDependencia().getNome()}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>

            <!--Ficha 12-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 12 - Empréstimo intercompanhia</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio ">
                    <tr>
                        <th>Status</th>
                        <th>Empresa devedora do empréstimo</th>
                        <th>Moeda</th>
                        <th>Prazo original do empréstimo</th>
                        <th>Saldo nominal do empréstimo</th>
                        <th>Juros recebidos</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha12Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getEmpresa().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${ficha.getPrazoEmprestimo()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getSaldoDatabase())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getJurosPeriodoBase())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 13-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 13 - Empréstimo não-intercompanhia</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>País responsável</th>
                        <th>Moeda</th>
                        <th>Prazo de Empréstimo</th>
                        <th>Saldo data base</th>
                        <th>Juros recebido</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha13Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${ficha.getPrazoEmprestimo()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getSaldoDatabase())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getJurosPeriodoBase())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 14-->
            <!--Participação no capital menor que 10%-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <div>
                        <h2>Ficha 14 - Fundos de Investimento</h2>
                        <h3>Porcentagem de participação no capital total do fundo de investimento menor que 10%</h3>
                    </div>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <div class="table-wrapper">
                    <table class="table-lista-fichas-relatorio">
                        <tr>
                            <th>Status</th>
                            <th>País</th>
                            <th>Moeda</th>
                            <th>Valor de participação na data-base</th>
                            <th>Rendimentos distribuídos ao declarante</th>
                            <th>Última atualização</th>
                            <th>Funcionário</th>
                        </tr>
                        <c:forEach items="${ficha14MenorController.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                            <tr>
                                <td>${ficha.getStatus().getStatus()}</td>
                                <td>${ficha.getPais().getNome()}</td>
                                <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                                <td>${numeroUtils.doubleToString(ficha.getValorParticipacao())}</td>
                                <td>${numeroUtils.doubleToString(ficha.getRendimentoDistribuido())}</td>
                                <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                                <td>
                                    ${ficha.getFuncionario().getNome()}
                                    <br>
                                    ${ficha.getFuncionario().getDependencia().getNome()}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>


            <!--Participação no capital maior ou igual a 10%-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <div>
                        <h2>Ficha 14 - Fundos de Investimento</h2>
                        <h3>Porcentagem de participação no capital total do fundo de investimento maior ou igual a 10%</h3>
                    </div>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>Fundo de investimento</th>
                        <th>Moeda</th>
                        <th>Patrimônio líquido na data-base</th>
                        <th>Percentual de participação no patrimônio</th>
                        <th>Rendimentos (positivos ou negativos) do fundo no período-base</th>
                        <th>Rendimentos distribuídos no período-base</th>
                        <th>Controla outras empresas que estão no final da cadeia de controle?</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha14MaiorController.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getEmpresa().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getPatrimonioLiquido())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getParticipacaoPatrimonio())}%</td>
                            <td>${numeroUtils.doubleToString(ficha.getRendimentosFundo())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getRendimentosDistribuidos())}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${ficha.isControlaEmpresas()}">
                                        <p>Sim</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Não</p>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 14 - Empresas controladas-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <div>
                        <h2>Ficha 14 - Fundos de Investimento</h2>
                        <h3>Porcentagem de participação no capital total do fundo de investimento maior ou igual a 10%</h3>
                        <h3>Empresas controladas</h3>
                    </div>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>Nome do fundo controlador</th>
                        <th>Nome da empresa controlada</th>
                        <th>País da empresa controlada</th>
                        <th>Atividade econômica principal</th>
                        <th>Percentual de participação no capital social</th>
                        <th>Moeda</th>
                        <th>Patrimônio líquido total na data-base</th>
                        <th>Valor de mercado na data-base</th>
                        <th>A empresa está ao final da cadeia de controle?</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha14EmpresaController.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getFicha14Controladora().getStatus().getStatus()}</td>
                            <td>${ficha.getFicha14Controladora().getEmpresa().getNome()}</td>
                            <td>${ficha.getNome()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getAtividadeEcn()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getPorcentoCapitalSocial())}%</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getPatrimonioLiquido())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorMercado())}</td>
                            <td>${ficha.isFinalCadeia() ? "Sim" : "Não"}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 15-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 15 - Imóvel</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>País</th>
                        <th>Moeda</th>
                        <th>Método de valoração</th>
                        <th>Valor</th>
                        <th>Imóvel está quitado?</th>
                        <th>Saldo devedor</th>
                        <th>Aluguéis recebidos no período</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha15Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${ficha.getMetodoValoracao()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${ficha.isImovelQuitado()}">
                                        Sim
                                    </c:when>
                                    <c:otherwise>
                                        Não
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${ficha.getSaldoDatabase() == 0}">
                                        -
                                    </c:when>
                                    <c:otherwise>
                                        ${numeroUtils.doubleToString(ficha.getSaldoDatabase())}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${numeroUtils.doubleToString(ficha.getAluguelRecebido())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 16-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 16 - Outros direitos</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>Tipos de outros direitos</th>
                        <th>País responsável</th>
                        <th>Moeda</th>
                        <th>Valor data base</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                        <th>Justificativa</th>
                    </tr>
                    <c:forEach items="${ficha16Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getTipoOutrosDireito()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                            <td style="text-align: center; vertical-align: middle;">
                                <c:choose>
                                    <c:when test="${not empty ficha.justificativaGestor}">
                                        <img src="${pageContext.request.contextPath}/resources/imgs/justificativa.png" 
                                             style="cursor: pointer; width: 24px;"
                                             onclick="verJustificativa('<c:out value="${ficha.justificativaGestor}"/>', ${ficha.id}, 'ficha16')">
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 17-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 17 - Título de dívida intercompanhia</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>País emissor</th>
                        <th>Moeda</th>
                        <th>Prazo original do título de dívida</th>
                        <th>Valor de mercado</th>
                        <th>Juros recebidos</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha17Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getEmpresa().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${ficha.getPrazoDivida()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorMercado())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getJurosRecebidos())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <!--Ficha 18-->

            <div class="view-relatorio">
                <div class="topo-view-relatorio">
                    <h2>Ficha 18 - Título de dívida não-intercompanhia</h2>
                    <div class="border-img-expandir">
                        <img class="img-expandir" src="resources/imgs/expandir.png" alt="Expandir"/>
                    </div>
                </div>
                <table class="table-lista-fichas-relatorio">
                    <tr>
                        <th>Status</th>
                        <th>País responsável</th>
                        <th>Moeda</th>
                        <th>Prazo original do título de dívida</th>
                        <th>Valor de mercado</th>
                        <th>Juros recebidos</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                        <th>Justificativa</th>
                    </tr>
                    <c:forEach items="${ficha18Controller.getAllFichasByTrimestreAno(trimestre, ano)}" var="ficha">
                        <tr>
                            <td>${ficha.getStatus().getStatus()}</td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${ficha.getPrazoDivida()}</td>
                            <td>${numeroUtils.doubleToString(ficha.getValorMercado())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getJurosRecebidos())}</td>
                            <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                            <td>
                                ${ficha.getFuncionario().getNome()}
                                <br>
                                ${ficha.getFuncionario().getDependencia().getNome()}
                            </td>
                            <td style="text-align: center; vertical-align: middle;">
                                <c:choose>
                                    <c:when test="${not empty ficha.justificativaGestor}">
                                        <img src="${pageContext.request.contextPath}/resources/imgs/justificativa.png" 
                                             style="cursor: pointer; width: 24px;"
                                             onclick="verJustificativa('<c:out value="${ficha.justificativaGestor}"/>', ${ficha.id}, 'ficha18')">
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>

        <div id="modalVisualizarJustificativa" style="display:none; position:fixed; z-index:9999; left:0; top:0; width:100%; height:100%; background-color:rgba(0,0,0,0.6);">
            <div style="background-color:#fff; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); padding:25px; border:1px solid #888; width:50%; border-radius:8px; box-shadow: 0 4px 8px rgba(0,0,0,0.2); text-align:center; font-family: Arial, sans-serif;">

                <h2 style="color: #003366; margin-bottom: 15px;">Justificativa do Gestor</h2>
                <hr style="border: 0; border-top: 1px solid #eee; margin-bottom: 20px;">

                <input type="hidden" id="modalFichaId">
                <input type="hidden" id="modalFichaServlet">

                <p id="conteudoJustificativaTexto" style="font-size: 16px; color: #333; margin-bottom: 30px; text-align: justify; line-height: 1.5; background: #f9f9f9; padding: 15px; border-radius: 5px; border: 1px solid #eee; max-height: 300px; overflow-y: auto;">
                </p>

                <div style="display: flex; justify-content: center; gap: 10px;">
                    <button type="button" onclick="recusarJustificativa()" class="btn-vermelho">Recusar Justificativa</button>

                    <button type="button" onclick="fecharModalJustificativa()" class="btn">Fechar</button>
                </div>
            </div>
        </div>
                
        <script>
            var ano = '${ano}';
            var trimestre = '${trimestre}';
        </script>
        <script src="resources/js/relatorio.js"></script>
        <script src="resources/js/temas.js"></script>    
        <script>
//            const subtitulo = document.getElementById("subtitulo");
//            const trimestre = document.getElementById("valorTrimestre").value;
//            const ano = document.getElementById("valorAno").value;
//            let trimestreReferente = trimestre - 1;
//            let anoReferente = ano;
//
//            console.log("Trimestre:", trimestre);
//            console.log("Ano:", ano);
//
//
//
//            if (trimestre == 1) {
//                trimestreReferente = 4;
//                anoReferente = ano - 1;
//                console.log(trimestreReferente);
//                console.log(anoReferente);
//            }
//
//            function inserirTextoNoH3(texto) {
//                subtitulo.textContent = texto;
//            }
//
//            inserirTextoNoH3("(Dados referentes ao período-base do " + trimestreReferente + "º trimestre de " + anoReferente + ")");
        </script>
    </body>
</html>
