<!-- =========================== EDIT =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha11MaiorController" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha11Maior" %>
<%
    Ficha11MaiorController fichaController = new Ficha11MaiorController();
    int idFicha = Integer.parseInt(request.getParameter("idMaior"));
    Ficha11Maior ficha = fichaController.getFichaById(idFicha);
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

                <p>Participação <b>maior</b> que 10%</p>

                <form action="<%=request.getContextPath()%>/ficha11/maior" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="edit" hidden>
                    <input type="number" name="id" hidden value="<%=idFicha%>"/>

                    <div class="label-container">
                        <label for="empresa">Selecionar empresa: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Escolher, entre as empresas no exterior previamente cadastradas, aquela na qual o declarante possui participação em poder de voto igual ou superior a 10%. Somente podem ser vinculadas ao ativo 'Empresas – Participação no capital' aquelas empresas com as quais o declarante possui a relação 'Empresa declarante é investidora direta na empresa no exterior'.</p>
                    <select id="empresa" name="empresa" required>
                        <c:forEach items="${empresaController.listarEmpresas()}" var="empresa">
                            <option value="${empresa.getId()}"
                                    ${empresa.getId() == ficha.getEmpresa().getId() ? 'selected' : ''}>
                                ${empresa.getNome()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label>Esta empresa possui cotação em bolsa de valores no exterior?</label>
                    </div>
                    <p class="descricao">Selecione a opção 'Sim' ou 'Não', para informar se a empresa possui cotação em bolsa de valores no exterior. Caso seja selecionada a opção 'Sim', o método de valoração deverá ser obrigatoriamente 'Cotação em bolsa'.</p>
                    <br>
                    <c:choose>
                        <c:when test="${ficha.isPossuiCotacaoEmBolsa()}">
                            <label>
                                <input type="radio" name="cotacao" value="true" checked>
                                Sim
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="cotacao" value="false">
                                Não
                            </label>
                        </c:when>
                        <c:otherwise>
                            <label>
                                <input type="radio" name="cotacao" value="true">
                                Sim
                            </label>
                            <br>
                            <br>
                            <label>
                                <input type="radio" name="cotacao" value="false" checked>
                                Não
                            </label>
                        </c:otherwise>
                    </c:choose>
                    <br>
                    <br>

                    <div class="label-container">
                        <label for="moeda">Moeda do país da empresa no exterior:</label>
                    </div>
                    <p class="descricao">Selecionar a moeda em que está referenciada a participação na empresa. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select id="moeda" name="moeda">
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}"
                                    ${moeda.getId() == ficha.getMoeda().getId() ? 'selected' : ''}>
                                ${moeda.getNome()} | ${moeda.getSigla()}
                            </option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="valoracao">Método de valoração:</label>
                    </div>
                    <p class="descricao">Selecionar um método de valoração para a participação na empresa na data-base, escolhendo entre 'Avaliação por especialista', 'Cotação em bolsa', 'Fluxo de caixa descontado', 'Negociação recente de parcela do capital' e 'Valor total do patrimônio líquido'. Caso a empresa possua cotação em bolsa de valores no exterior, o método de valoração deverá ser obrigatoriamente 'Cotação em bolsa'.</p>
                    <select id="valoracao" name="valoracao">
                        <option value="" selected>Selecione o método</option>
                        <option value="Avaliação por especialista" ${ficha.getMetodoValoracao() == 'Avaliação por especialista' ? 'selected' : ''}>Avaliação por especialista</option>
                        <option value="Cotação em bolsa" ${ficha.getMetodoValoracao() == 'Cotação em bolsa' ? 'selected' : ''}>Cotação em bolsa</option>
                        <option value="Fluxo de caixa descontado" ${ficha.getMetodoValoracao() == 'Fluxo de caixa descontado' ? 'selected' : ''}>Fluxo de caixa descontado</option>
                        <option value="Negociação recente de parcela do capital" ${ficha.getMetodoValoracao() == 'Negociação recente de parcela do capital' ? 'selected' : ''}>Negociação recente de parcela do capital</option>
                        <option value="Valor total do patrimônio líquido" ${ficha.getMetodoValoracao() == 'Valor total do patrimônio líquido' ? 'selected' : ''}>Valor total do patrimônio líquido</option>
                    </select>

                    <div class="label-container">
                        <label for="valorDataBase">Valor da empresa na data-base:</label>
                    </div>
                    <p class="descricao">Informe o valor integral da empresa na data-base, conforme o método de valoração escolhido. Este campo pode assumir valores positivos, nulos ou negativos.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="valorDataBase" name="valorDataBase" placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${ficha.getValorEmpresa() != -0.01 ? numeroUtils.doubleToString(ficha.getValorEmpresa()) : ''}">
                    </div>
                    <div class="label-container">
                        <label for="patrimonioLiquido">Patrimônio líquido total na data-base:</label>
                    </div>
                    <p class="descricao">Informe o valor total do patrimônio líquido da empresa na data-base. Este campo pode assumir valores positivos, nulos ou negativos.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="patrimonioLiquido" name="patrimonioLiquido" placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${ficha.getPatrimonioTotal() != -0.01 ? numeroUtils.doubleToString(ficha.getPatrimonioTotal()) : ''}">
                    </div>
                    <div class="label-container">
                        <label for="porcentagemSocial">Percentual de participação no capital social:</label>
                    </div>
                    <p class="descricao">Informe o percentual de participação no capital social detido pelo declarante. Informe um valor maior que zero e menor ou igual a 100. Note que deve ser informado o percentual como múltiplo de 100, por exemplo, o valor 15 representa 15%.</p>
                    <input type="text" id="porcentagemSocial" name="porcentagemSocial" placeholder="Digite um valor entre 1 e 100" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${ficha.getPorcentoParticipacaoCapital() != -0.01 ? numeroUtils.doubleToString(ficha.getPorcentoParticipacaoCapital()) : ''}">

                    <div class="label-container">
                        <label for="porcentagemVoto">Percentual de poder de voto:</label>
                    </div>
                    <p class="descricao">Informe o percentual de participação no poder de voto na empresa detido pelo declarante. Informe um valor maior ou igual a 10 e menor ou igual a 100. Note que deve ser informado o percentual como múltiplo de 100, por exemplo, o valor 15 representa 15%.</p>
                    <input type="text" id="porcentagemVoto" name="porcentagemVoto" placeholder="Digite um valor entre 10 e 100" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(\d+(\.\d+)*)(,\d+)?$" value="${ficha.getPorcentoPoderVoto() != -0.01 ? numeroUtils.doubleToString(ficha.getPorcentoPoderVoto()) : ''}">

                    <div class="label-container">
                        <label for="ativoDataBase">Ativo na data-base:</label>
                    </div>
                    <p class="descricao">Informe o ativo total da empresa na data-base. Este campo deve ser maior ou igual a zero.</p>

                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="ativoDataBase" name="ativoDataBase" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${ficha.getAtivoDatabase() != -0.01 ? numeroUtils.doubleToString(ficha.getAtivoDatabase()) : ''}">
                    </div>

                    <div class="label-container">
                        <label for="passivoExigivel">Passivo exigível na data-base:</label>
                    </div>
                    <p class="descricao">Informe o passivo exigível da empresa na data-base. Este campo deve ser maior ou igual a zero.</p>

                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="passivoExigivel" name="passivoExigivel" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${ficha.getPassivoExigivel() != -0.01 ? numeroUtils.doubleToString(ficha.getPassivoExigivel()) : ''}">
                    </div>

                    <div class="label-container">
                        <label for="valorTotal">Valor total do lucro ou prejuízo líquidos da empresa no exterior:</label>
                    </div>
                    <p class="descricao">Informe o total do lucro ou prejuízo líquido auferido pela empresa na data-base. Este campo pode assumir valores positivos, nulos ou negativos. ATENÇÃO: Os valores dos campos número 'Valor total do lucro ou prejuízo líquidos da empresa no exterior' a 'Lucro distribuído no período-base' são fluxos auferidos somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base.</p>

                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="valorTotal" name="valorTotal" placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${ficha.getValorTotalLucroPrejuizo() != -0.01 ? numeroUtils.doubleToString(ficha.getValorTotalLucroPrejuizo()) : ''}">
                    </div>

                    <div class="label-container">
                        <label for="resultadoLiquidoItens">Resultado líquido de itens não recorrentes:</label>
                    </div>
                    <p class="descricao">Informe, em termos líquidos, os ganhos (positivo) ou perdas (negativo) decorrentes de eventos não usuais às atividades da empresa no exterior e que tenham transitado pelo resultado do exercício, tal como resultado de operações descontinuadas (venda de ativos, incluindo participações em empresas), perdas judiciais, multas, dentre outros.</p>

                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="resultadoLiquidoItens" name="resultadoLiquidoItens" placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${ficha.getResultadoLiquidoItensNaoRecorrentes() != -0.01 ? numeroUtils.doubleToString(ficha.getResultadoLiquidoItensNaoRecorrentes()) : ''}">
                    </div>

                    <div class="label-container">
                        <label for="resultadoLiquidoReavaliacoes">Resultado Líquido de reavaliações (ex. impairment):</label>
                    </div>
                    <p class="descricao">Informe, em termos líquidos, os ganhos (positivo) ou perdas (negativo), que tenham transitado no resultado do exercício: i) não realizados decorrentes de reavaliação de ativos (clientes, estoques, investimentos, imobilizado e intangível) e de passivo (constituição/reversão de despesas com provisões), e ii) realizados na negociação de ativos (exceto estoque) e passivos.</p>

                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="resultadoLiquidoReavaliacoes" name="resultadoLiquidoReavaliacoes" placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${ficha.getResultadoLiquidoReavaliacoes() != -0.01 ? numeroUtils.doubleToString(ficha.getResultadoLiquidoReavaliacoes()) : ''}">
                    </div>

                    <div class="label-container">
                        <label for="resultadoLiquidoCambial">Resultado líquido de variação cambial:</label>
                    </div>
                    <p class="descricao">Informe, em termos líquidos, os ganhos (positivo) ou perdas (negativo) decorrentes de variação cambial (monetária) de passivos (obrigações) e ativos (incluindo créditos) que tenham transitado no resultado do exercício.</p>

                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="resultadoLiquidoCambial" name="resultadoLiquidoCambial" placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${ficha.getResultadoLiquidoVariacaoCambial() != -0.01 ? numeroUtils.doubleToString(ficha.getResultadoLiquidoVariacaoCambial()) : ''}">
                    </div>

                    <div class="label-container">
                        <label for="lucroDistribuido">Lucro distribuído no período-base:</label>
                    </div>
                    <p class="descricao">Informe o lucro total aprovado para distribuição aos sócios (dividendos) pela empresa no período-base, inclusive dividendos provenientes de reserva de lucros (períodos anteriores). Este campo deve ser maior ou igual a zero.</p>

                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="lucroDistribuido" name="lucroDistribuido" placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$" value="${ficha.getLucroDistribuido() != -0.01 ? numeroUtils.doubleToString(ficha.getLucroDistribuido()) : ''}">
                    </div>

                    <div class="label-container">
                        <label>O fundo no exterior controla outras empresas direta ou indiretamente, também no exterior, que estão ao final da cadeia de controle? <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe 'Sim' ou 'Não' para a pergunta. Caso seja respondido afirmativamente, será necessário o preenchimento de informações adicionais.</p>
                    <br>
                    <c:choose>
                        <c:when test="${ficha.isControlaEmpresa()}">
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
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>
