<%-- 
    Document   : consolidado
    Created on : 17 de set. de 2025, 16:37:38
    Author     : T1092489
--%>

<%@page import="java.lang.String"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="PtaxController" class="br.com.bb.cbe.controllers.PtaxController"/>
<jsp:useBean id="ficha08Controller" class="br.com.bb.cbe.controllers.Ficha08Controller"/>
<jsp:useBean id="ficha03Controller" class="br.com.bb.cbe.controllers.Ficha03Controller"/>
<jsp:useBean id="ficha16Controller" class="br.com.bb.cbe.controllers.Ficha16Controller"/>
<jsp:useBean id="ContabilController" class="br.com.bb.cbe.controllers.ContabilController"/>
<jsp:useBean id="ConsolidadoController" class="br.com.bb.cbe.controllers.ConsolidadoController"/>
<%@ page import="br.com.bb.cbe.controllers.ConsolidadoController" %>
<%@page import="br.com.bb.cbe.Bean.Ptax"%>

<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%@ page import ="com.google.gson.Gson" %>
<%@ page  import="com.google.gson.reflect.TypeToken"%>
<%@ page import="java.lang.reflect.Type"%>
<%@ pageimport ="java.util.List"%>
<%@ page import ="java.util.Map"%>

<!-- =========================== VIEW =========================== -->
<%@page import="br.com.bb.cbe.Bean.Moeda"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page import="br.com.bb.cbe.Bean.Ficha08"%>
<%@page import="br.com.bb.cbe.Bean.Ficha03"%>
<%@page import="br.com.bb.cbe.Bean.Ficha16"%>
<jsp:useBean id="ficha11EmpresaController" class="br.com.bb.cbe.controllers.Ficha11EmpresaController"/>
<jsp:useBean id="MoedaController" class="br.com.bb.cbe.controllers.MoedaController"/>
<!DOCTYPE html>

<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tabela Conciliação</title>    
    </head>
    <body>
        
         <%
               
                int trimestreSelecionado = Integer.parseInt(request.getParameter("tri"));
                int anoSelecionado = Integer.parseInt(request.getParameter("ano"));

            String contabilJson = ConsolidadoController.obterJsonComparacao(trimestreSelecionado, anoSelecionado);
            System.out.println("TRIMESTRE: ");
 

        
            String taxasJson = PtaxController.getAllTaxasJson();
            String fichasJson8 = ficha08Controller.getAllFichasJson();
            String fichasJson16 = ficha16Controller.getAllFichasJson();
            String fichasJson3 = ficha03Controller.getAllFichasJson();
//            String contabilJson = ContabilController.getAllCosifsJson();
          
            System.out.println("TRIMESTRE PARAM: ");
//            System.out.println(trimestreParam);
            //contabilJson = ConsolidadoController.obterJsonComparacao(trimestreParam);

            request.setAttribute("taxasJson", taxasJson);
            request.setAttribute("fichasJson8", fichasJson8);
            request.setAttribute("fichasJson16", fichasJson16);
            request.setAttribute("fichasJson3", fichasJson3);
            request.setAttribute("contabilJson", contabilJson);
            
        
        Gson gson = new Gson();
        Type tipoListaMap = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> lista = gson.fromJson(contabilJson, tipoListaMap);
        request.setAttribute("contabilJson", lista);

          
        %>
       <!--CONTEX PATH PRA USAR NA VALIDAÇÃO-->
       <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
        <%@include file="../topo.jsp"%>
        
        <div class="view-container">
            <div class="topo-view">
                <h2>Conciliação Contábil X Dados Gestor</h2>
                
                <!--NÃO PRECISA DESSA PARTE DA VALIDAÇÃO-->
                
                <div>
                    <c:choose>
                        <c:when test = "${comissao.startsWith('GER SOLUCOES')}">
                            <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha" id="valida" title="É necessário ter o cargo de gerente para validar as informações">
                        </c:when>
                    </c:choose>
                    <a href="../index.jsp"><input type="button" value="Voltar" class="btn" id="voltar"></a>
                </div>
            </div>

            <table border="1" class="table-lista-fichas">
                <thead>
                    <tr>
                        <th>Ficha</th>
                        <th>COSIF</th>
                        <th>Valor Contábil</th>
                        <th>Valor Gestor</th>
                        <th>Diferença</th>
                        <th>(%)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="grupo" items="${contabilJson}" varStatus="groupStatus">
                        <c:forEach var="cosif" items="${grupo.cosifs}" varStatus="loop">

                            <!-- Cada TR carrega só metadados em data-* -->
                            <tr
                                data-group-index="${groupStatus.index}"
                                data-rowcount="${grupo.rowCount}"
                                data-ficha="${grupo.ficha}"

                                data-valor-gestor-agg="${grupo.valorGestorAgregado}"
                                data-diferenca-agg="${grupo.diferencaAgregada}"
                                data-porcentagem-agg="${grupo.porcentagemAgregada}"

                                data-valor-ficha="${cosif.valorFicha}"
                                data-diferenca="${cosif.diferenca}"
                                data-porcentagem="${cosif.porcentagem}"
                                >

                                <!-- Coluna Ficha (mantemos server-side o rowspan para já funcionar) -->
                                <c:if test="${loop.first}">
                                    <td rowspan="${grupo.rowCount}"
                                        class="col-ficha texto-esquerda"
                                        style="vertical-align: middle; text-align: center;"
                                        data-ficha="${grupo.ficha}"
                                        data-nome-ficha="${grupo.nome_ficha}">
                                        <!-- conteúdo será preenchido pelo JS -->
                                    </td>
                                </c:if>

                                <!-- COSIF: fica só com data-*; texto será preenchido pelo JS -->
                                <td class="texto-esquerda cosif-cell"
                                    data-cosif="<c:out value='${cosif.cosif}'/>"
                                    data-nome="<c:out value='${cosif.nomeCosif}'/>">
                                </td>

                                <!-- Valor Contábil: só data; formatação no JS -->
                                <td class="valor-contabil" data-value="${cosif.consolidado}"></td>

                                <!-- As 3 colunas a seguir são sempre renderizadas;
                                     o JS decide se usa agregados (fichas 8/11) com rowspan
                                     ou valores da linha (demais fichas) -->
                                <td class="valor-gestor"></td>
                                <td class="diferenca"></td>
                                <td class="porcentagem"></td>
                            </tr>

                        </c:forEach>
                    </c:forEach>
                </tbody>
                <tfoot id="tfoot-total">
                    </tfoot>
            </table>

        </div>
        <div class="sobreposicao-tela-preta">
            <div class="excluir-confirma">
                <p><strong>ATENÇÃO: </strong>Todos os dados serão perdidos e não poderão ser recuperados.</p>
                <p>Tem certeza que deseja excluir esta linha?</p>
                <div>     
                    <button class="btn cancela">Cancelar</button>
                    <button class="btn exclui">Excluir</button>
                </div>
            </div>
        </div>
        <!--LEMBRA DE IGNORAR O CONTABIL-->
        <script>


//            var taxasJson = '$//{taxasJson}';
//            var fichasJson8 = '$//{fichasJson8}';
            var fichasJson3 = '$//{fichasJson3}';
//            var fichasJson16 = '$//{fichasJson16}';
//            var contabilJson = '$//{contabilJson}';
        </script>
        <script src="/ProjetoCBE/resources/js/CalcularDiferenca.js"></script>
        <script src="/ProjetoCBE/resources/js/validacao.js"></script>
        <script src="/ProjetoCBE/resources/js/delecao.js"></script>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/consolidado.js?v=<%= new java.util.Date().getTime() %>" defer></script>

        <script>
            // Executa quando o conteúdo da página estiver totalmente carregado
            document.addEventListener('DOMContentLoaded', function() {
               
                /**
                 * Aplica o tema de cores na tabela de conciliação com base no mês atual.
                 */
                function estilizarTabelaConsolidado() {
                    const data = new Date();
                    const mesAtual = data.getMonth(); // Janeiro é 0, Outubro é 9

                    // Seleciona a tabela específica desta página
                    const tabelaConsolidado = document.querySelector('.table-lista-fichas');

                    // Se a tabela não for encontrada, interrompe a execução
                    if (!tabelaConsolidado) {
                        console.error("Tabela de conciliação não encontrada.");
                        return;
                    }

                    // Verifica se o mês é Outubro (índice 9)
                    if (mesAtual === 9) {
                        tabelaConsolidado.classList.add('table-outubro-consolidado');
                       
                        // Também aplica o tema rosa no cabeçalho
                        const cabecalho = tabelaConsolidado.querySelectorAll('th');
                        cabecalho.forEach(th => {
                            th.style.backgroundColor = '#fc8b9f';
                        });
                    }
                }

                // Chama a função para estilizar a tabela
                estilizarTabelaConsolidado();
            });
        </script>
    </body>
</html>
 