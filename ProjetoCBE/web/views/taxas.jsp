<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="PtaxController" class="br.com.bb.cbe.controllers.PtaxController"/>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>

<%@page import="br.com.bb.cbe.DAO.*"%>
<%@page import="br.com.bb.cbe.Bean.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="java.time.LocalDate"%>

<%
    LocalDate hoje = LocalDate.now();
    int anoSelecionado = hoje.getYear();
    int trimSelecionado = (hoje.getMonthValue() - 1) / 3 + 1;

    String paramAno = request.getParameter("filtroAno");
    String paramTrim = request.getParameter("filtroTrimestre");

    if (paramAno != null && !paramAno.isEmpty()) {
        try { anoSelecionado = Integer.parseInt(paramAno); } catch(Exception e){}
    }
    if (paramTrim != null && !paramTrim.isEmpty()) {
        try { trimSelecionado = Integer.parseInt(paramTrim); } catch(Exception e){}
    }

    System.out.println("====== DEBUG TAXAS.JSP ======");
    System.out.println("Filtrando -> Ano: " + anoSelecionado + " | Trimestre: " + trimSelecionado);

    // --- 2. BUSCANDO DADOS DO PTAX ---
    List<Ptax> listaPtaxFiltrada = PtaxController.getTaxasFiltradas(anoSelecionado, trimSelecionado);
    String taxasJson = new Gson().toJson(listaPtaxFiltrada);
    List<Integer> anosDisponiveis = PtaxController.getAnosDisponiveis();

    // --- 3. BUSCANDO FICHAS DIRETO DO DAO (Blindado contra erro de Controller) ---
    // Nota: O seu DAO usa a ordem (trimestre, ano)
    
    List<Ficha01> listaFicha01 = Ficha01DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    System.out.println("Ficha 01: " + listaFicha01.size() + " registros.");

    List<Ficha02> listaFicha02 = Ficha02DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    System.out.println("Ficha 02: " + listaFicha02.size() + " registros.");

    List<Ficha03> listaFicha03 = Ficha03DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    
    List<Ficha06> listaFicha06 = Ficha06DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    
    List<Ficha07> listaFicha07 = Ficha07DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    
    List<Ficha08> listaFicha08 = Ficha08DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    
    List<Ficha09> listaFicha09 = Ficha09DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    
    List<Ficha12> listaFicha12 = Ficha12DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    
    List<Ficha13> listaFicha13 = Ficha13DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    
    List<Ficha16> listaFicha16 = Ficha16DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    
    List<Ficha17> listaFicha17 = Ficha17DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    
    List<Ficha18> listaFicha18 = Ficha18DAO.getAllFichasByTrimestreAno(trimSelecionado, anoSelecionado);
    
    // Passando para o escopo da página
    request.setAttribute("listaFicha01", listaFicha01);
    request.setAttribute("listaFicha02", listaFicha02);
    request.setAttribute("listaFicha03", listaFicha03);
    request.setAttribute("listaFicha06", listaFicha06);
    request.setAttribute("listaFicha07", listaFicha07);
    request.setAttribute("listaFicha08", listaFicha08);
    request.setAttribute("listaFicha09", listaFicha09);
    request.setAttribute("listaFicha12", listaFicha12);
    request.setAttribute("listaFicha13", listaFicha13);
    request.setAttribute("listaFicha16", listaFicha16);
    request.setAttribute("listaFicha17", listaFicha17);
    request.setAttribute("listaFicha18", listaFicha18);
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>PTAX e Fichas</title>
    <link rel="stylesheet" href="../resources/css/ptax.css">
    <link rel="stylesheet" href="../resources/css/style.css">
    <style>
        .ficha-container { display: none; }
        #container-ficha01 { display: block; }
    </style>
</head>
<body>
    <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
    <%@include file="../topo.jsp"%>

    <div class="view-container">
        <div class="topo-view" style="flex-direction: column; align-items: flex-start;">
            <h2>PTAX e Conversões</h2>

            <div style="display: flex; width: 100%; justify-content: flex-end;">
                <button type="button" class="btn" id="voltar"
                        onclick="location.href='../index.jsp'"
                        style="display: inline-block; width: auto;">
                    Voltar
                </button>
            </div>
            
            <form action="taxas.jsp" method="GET" style="margin-top: 15px; background: #e8f0fe; padding: 15px; border-radius: 8px; display: flex; gap: 15px; align-items: flex-end;">
                <div style="display: flex; flex-direction: column;">
                    <label for="filtroAno" style="font-weight: bold; color: #0038a8;">Ano:</label>
                    <select name="filtroAno" id="filtroAno" style="padding: 5px; border-radius: 4px;">
                        <% for(Integer ano : anosDisponiveis) { %>
                            <option value="<%= ano %>" <%= (ano == anoSelecionado) ? "selected" : "" %>><%= ano %></option>
                        <% } %>
                        <% if(anosDisponiveis.isEmpty()) { %>
                             <option value="<%= anoSelecionado %>" selected><%= anoSelecionado %></option>
                        <% } %>
                    </select>
                </div>
                <div style="display: flex; flex-direction: column;">
                    <label for="filtroTrimestre" style="font-weight: bold; color: #0038a8;">Trimestre:</label>
                    <select name="filtroTrimestre" id="filtroTrimestre" style="padding: 5px; border-radius: 4px;">
                        <option value="1" <%= (trimSelecionado == 1) ? "selected" : "" %>>1º Trimestre</option>
                        <option value="2" <%= (trimSelecionado == 2) ? "selected" : "" %>>2º Trimestre</option>
                        <option value="3" <%= (trimSelecionado == 3) ? "selected" : "" %>>3º Trimestre</option>
                        <option value="4" <%= (trimSelecionado == 4) ? "selected" : "" %>>4º Trimestre</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary" style="background-color: #0038a8; color: white; border: none; padding: 7px 15px; border-radius: 4px; cursor: pointer; height: 35px;">
                    Filtrar PTAX
                </button>
            </form>
        </div>
    </div>

    <div class="view-container">
        <h3 style="margin-left: 2.5%; color: #555;">Taxas PTAX - Referência: <%= trimSelecionado %>º Tri/<%= anoSelecionado %></h3>
        <table class="table-lista-fichas">
            <tr><th>Moeda</th> <th>Compra</th> <th>Venda</th> <th>Data Ref.</th> <th>Trimestre</th></tr>
            <% for(Ptax ptax : listaPtaxFiltrada) { %>
            <tr>
                <td><%= ptax.getMoeda().getSigla() %> - <%= ptax.getMoeda().getNome() %></td>
                <td><%= String.format("%.4f", ptax.getCompra()) %></td>
                <td><%= String.format("%.4f", ptax.getVenda()) %></td>
                <td><%= new java.text.SimpleDateFormat("dd/MM/yyyy").format(ptax.getData_criacao()) %></td>
                <td><%= ptax.getTrimestre() %>º</td>
            </tr>
            <% } %>
            <% if(listaPtaxFiltrada.isEmpty()) { %>
            <tr><td colspan="5" style="text-align:center;">Nenhuma taxa encontrada.</td></tr>
            <% } %>
        </table>
    </div>

    <div class="filtro-container table-lista-fichas" style="margin-top: 20px;">
         <label for="selectFicha" style="font-size: 1.1rem; color: #333;">Visualizar Ficha:</label>
         <select id="selectFicha" onchange="mudarAbaFicha()" style="font-size: 1rem; padding: 5px;">
            <option value="ficha01">Ficha 1 - Ações negociadas em bolsa</option>
            <option value="ficha02">Ficha 2 - Brazilian Depositary Receipt</option>
            <option value="ficha03">Ficha 3 - Câmbio Manual</option>
            <option value="ficha06">Ficha 6 - Depositary Receipt (BR)</option>
            <option value="ficha07">Ficha 7 - Depositary Receipt (Não-BR)</option>
            <option value="ficha08">Ficha 8 - Depósitos à vista e a prazo</option>
            <option value="ficha09">Ficha 9 - Derivativo</option>
            <option value="ficha12">Ficha 12 - Empréstimo intercompanhia</option>
            <option value="ficha13">Ficha 13 - Empréstimo não-intercompanhia</option>
            <option value="ficha16">Ficha 16 - Outros direitos</option>
            <option value="ficha17">Ficha 17 - Título de dívida intercompanhia</option>
            <option value="ficha18">Ficha 18 - Título de dívida não-intercompanhia</option>
         </select>
    </div>

    <div class="view-container ficha-container" id="container-ficha01">
        <div class="topo-view"><h2>Ficha 1 - Ações negociadas em bolsa</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>Mercado</th> <th>Moeda</th> <th>Valor Data-Base</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th> <th>Func.</th></tr>
            <c:forEach items="${listaFicha01}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getPais().getNome()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                    <td class="taxa">--</td> <td class="conversao">--</td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td> <td>${ficha.getFuncionario().getNome()}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha01}"><tr><td colspan="8" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha02">
        <div class="topo-view"><h2>Ficha 2 - BDR</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>País</th> <th>Valor Mercado</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th> <th>Func.</th></tr>
            <c:forEach items="${listaFicha02}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getPais().getNome()}</td>
                    <td>${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td> <td>${ficha.getFuncionario().getNome()}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha02}"><tr><td colspan="7" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha03">
        <div class="topo-view"><h2>Ficha 3 - Câmbio Manual</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>Moeda</th> <th>Valor</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th></tr>
            <c:forEach items="${listaFicha03}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha03}"><tr><td colspan="6" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha06">
        <div class="topo-view"><h2>Ficha 6 - DR (BR)</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>País</th> <th>Moeda</th> <th>Valor</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th></tr>
            <c:forEach items="${listaFicha06}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getPais().getNome()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha06}"><tr><td colspan="7" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha07">
        <div class="topo-view"><h2>Ficha 7 - DR (Não-BR)</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>País</th> <th>Moeda</th> <th>Valor</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th></tr>
            <c:forEach items="${listaFicha07}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getPaisNegociacao().getNome()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha07}"><tr><td colspan="7" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha08">
        <div class="topo-view"><h2>Ficha 8 - Depósitos</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>País</th> <th>Moeda</th> <th>Saldo</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th></tr>
            <c:forEach items="${listaFicha08}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getSaldoDatabase())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getPais().getNome()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getSaldoDatabase())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha08}"><tr><td colspan="7" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha09">
        <div class="topo-view"><h2>Ficha 9 - Derivativo</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>País</th> <th>Moeda</th> <th>Valor</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th></tr>
            <c:forEach items="${listaFicha09}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getPais().getNome()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha09}"><tr><td colspan="7" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha12">
        <div class="topo-view"><h2>Ficha 12 - Empréstimo Inter.</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>Empresa</th> <th>Moeda</th> <th>Saldo</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th></tr>
            <c:forEach items="${listaFicha12}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getSaldoDatabase())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getEmpresa().getNome()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getSaldoDatabase())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha12}"><tr><td colspan="7" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha13">
        <div class="topo-view"><h2>Ficha 13 - Empréstimo Não-Inter.</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>País</th> <th>Moeda</th> <th>Saldo</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th></tr>
            <c:forEach items="${listaFicha13}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getSaldoDatabase())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getPais().getNome()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getSaldoDatabase())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha13}"><tr><td colspan="7" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha16">
        <div class="topo-view"><h2>Ficha 16 - Outros</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>Tipo</th> <th>Moeda</th> <th>Valor</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th></tr>
            <c:forEach items="${listaFicha16}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getValorDatabase())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getTipoOutrosDireito()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha16}"><tr><td colspan="7" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha17">
        <div class="topo-view"><h2>Ficha 17 - Dívida Inter.</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>Empresa</th> <th>Moeda</th> <th>Valor</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th></tr>
            <c:forEach items="${listaFicha17}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getValorMercado())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getEmpresa().getNome()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorMercado())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha17}"><tr><td colspan="7" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <div class="view-container ficha-container" id="container-ficha18">
        <div class="topo-view"><h2>Ficha 18 - Dívida Não-Inter.</h2></div>
        <table class="table-lista-fichas">
            <tr><th>Status</th> <th>País</th> <th>Moeda</th> <th>Valor</th> <th>PTAX</th> <th>Conversão</th> <th>Atualização</th></tr>
            <c:forEach items="${listaFicha18}" var="ficha">
                <tr class="linha-ficha" data-moeda="${ficha.getMoeda().getSigla()}" data-saldo="${numeroUtils.doubleToString(ficha.getValorMercado())}">
                    <td>${ficha.getStatus().getStatus()}</td> <td>${ficha.getPais().getNome()}</td> <td>${ficha.getMoeda().getSigla()}</td>
                    <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorMercado())}</td>
                    <td class="taxa"></td> <td class="conversao"></td>
                    <td>${dataUtils.formatarData(ficha.getDataCriacao())}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty listaFicha18}"><tr><td colspan="7" style="text-align:center;">Sem dados.</td></tr></c:if>
        </table>
    </div>

    <script>
        var taxasJson = '<%= taxasJson %>';
        var taxasData = [];
        try { taxasData = JSON.parse(taxasJson); } catch(e) { console.error(e); }

        function mudarAbaFicha() {
            var select = document.getElementById("selectFicha");
            var opcao = select.value;
            var containers = document.getElementsByClassName("ficha-container");
            for (var i = 0; i < containers.length; i++) {
                containers[i].style.display = "none";
                if (containers[i].id === "container-" + opcao) {
                    containers[i].style.display = "block";
                }
            }
        }

        window.onload = function() {
            console.log("Iniciando cálculos...");
            mudarAbaFicha();

            var linhas = document.querySelectorAll('.linha-ficha');
            linhas.forEach(function(linha) {
                var moedaSigla = linha.getAttribute('data-moeda');
                var saldoTexto = linha.getAttribute('data-saldo');
                var tdTaxa = linha.querySelector('.taxa');
                var tdConversao = linha.querySelector('.conversao');

                if (!tdTaxa || !tdConversao) return;

                if (moedaSigla === "BRL" || moedaSigla === "R$") {
                    tdTaxa.innerText = "1,0000";
                    tdConversao.innerText = saldoTexto;
                    return;
                }

                var taxaObj = taxasData.find(t => t.moeda.sigla === moedaSigla);

                if (taxaObj) {
                    var valorTaxa = taxaObj.compra;
                    tdTaxa.innerText = valorTaxa.toFixed(4).replace('.', ',');
                    
                    var saldoFloat = parseFloat(saldoTexto.replace(/\./g, '').replace(',', '.'));
                    var resultado = saldoFloat * valorTaxa;
                    tdConversao.innerText = resultado.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
                } else {
                    tdTaxa.innerText = "-";
                    tdConversao.innerText = "S/ Cotação";
                    tdConversao.style.color = "red";
                }
            });
        };
    </script>
    <script src="/ProjetoCBE/resources/js/CalcularDiferenca.js"></script>
</body>
</html>