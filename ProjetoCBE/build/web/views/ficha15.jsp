<!-- =========================== VIEW =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.bb.cbe.Bean.Ficha15"%>

<jsp:useBean id="ficha15Controller" class="br.com.bb.cbe.controllers.Ficha15Controller"/>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>

<%
    // Lógica do Filtro Inteligente
    String anoFiltro = request.getParameter("anoFiltro");
    String trimestreFiltro = request.getParameter("trimestreFiltro");
    List<Ficha15> listaFichas;

    // Carrega as opções disponíveis
    List<Integer> anosDisponiveis = ficha15Controller.getAnosExistentes();
    List<Integer> trimestresDisponiveis = ficha15Controller.getTrimestresExistentes();

    // Verificadores
    boolean temAno = (anoFiltro != null && !anoFiltro.isEmpty() && !anoFiltro.equals("todos"));
    boolean temTrimestre = (trimestreFiltro != null && !trimestreFiltro.isEmpty() && !trimestreFiltro.equals("todos"));

    if (temAno && temTrimestre) {
        // CASO 1: Tem os DOIS filtros
        int ano = Integer.parseInt(anoFiltro);
        int trim = Integer.parseInt(trimestreFiltro);
        listaFichas = ficha15Controller.getAllFichasByTrimestreAno(trim, ano);

    } else if (temAno) {
        // CASO 2: Tem APENAS Ano
        int ano = Integer.parseInt(anoFiltro);
        listaFichas = ficha15Controller.getAllFichasByAno(ano);

    } else if (temTrimestre) {
        // CASO 3: Tem APENAS Trimestre
        int trim = Integer.parseInt(trimestreFiltro);
        listaFichas = ficha15Controller.getAllFichasByTrimestre(trim);

    } else {
        // CASO 4: Busca tudo
        listaFichas = ficha15Controller.getAllFichas();
    }
    
    pageContext.setAttribute("listaFichas", listaFichas);
%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Ficha 15</title>
    </head>
    <body>
        <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
        <%@include file="../topo.jsp"%>
        <div class="view-container">
            <div class="topo-view">
                <h2>Ficha 15 - Imóvel</h2>
                <div>
                    <c:choose>
                        <c:when test = "${comissao.startsWith('GER SOLUCOES')}">
                            <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha15" id="valida" title="É necessário ter o cargo de gerente para validar as informações">
                        </c:when>
                        <c:otherwise>
                             <input type="button" class="btn btn-validar btn-disabled" value="Validar" title="É necessário ter o cargo de gerente para validar as informações" disabled>
                        </c:otherwise>
                    </c:choose>
                    <a href="../index.jsp"><input type="button" value="Voltar" class="btn" id="voltar"></a>
                    <a href="../forms/ficha15.jsp"><input type="button" value="Adicionar" class="btn"></a>
                </div>
            </div>

            <div class="filtro-container" style="width: 95%; margin: 0 auto 20px auto; background-color: #f4f4f4; padding: 15px; border-radius: 8px; border: 1px solid #ddd;">
                <form action="ficha15.jsp" method="GET" style="display: flex; gap: 15px; align-items: center;">
                    
                    <div class="div-opcoes" style="display: flex; align-items: center;">
                        <label for="anoFiltro" style="font-weight: bold; color: #0038a8; margin-right: 5px;">Ano:</label>
                        <select name="anoFiltro" id="anoFiltro" style="padding: 8px; border-radius: 4px; border: 1px solid #ccc; width: auto; margin: 0;">
                            <option value="todos">Todos</option>
                            <% 
                               if (anosDisponiveis != null) {
                                   for(Integer ano : anosDisponiveis) { 
                                       String selected = (anoFiltro != null && anoFiltro.equals(String.valueOf(ano))) ? "selected" : "";
                            %>
                                <option value="<%= ano %>" <%= selected %>><%= ano %></option>
                            <% 
                                   }
                               }
                            %>
                        </select>
                    </div>

                    <div class="div-opcoes" style="display: flex; align-items: center;">
                        <label for="trimestreFiltro" style="font-weight: bold; color: #0038a8; margin-right: 5px;">Trimestre:</label>
                        <select name="trimestreFiltro" id="trimestreFiltro" style="padding: 8px; border-radius: 4px; border: 1px solid #ccc; width: auto; margin: 0;">
                            <option value="todos">Todos</option>
                            <% 
                               if (trimestresDisponiveis != null) {
                                   for(Integer trim : trimestresDisponiveis) { 
                                       String selected = (trimestreFiltro != null && trimestreFiltro.equals(String.valueOf(trim))) ? "selected" : "";
                            %>
                                <option value="<%= trim %>" <%= selected %>><%= trim %>º Trimestre</option>
                            <% 
                                   }
                               }
                            %>
                        </select>
                    </div>

                    <button type="submit" class="btn" style="margin: 0; height: 38px;">Filtrar</button>
                    
                    <% if ((anoFiltro != null && !anoFiltro.equals("todos")) || (trimestreFiltro != null && !trimestreFiltro.equals("todos"))) { %>
                        <a href="ficha15.jsp" style="text-decoration: none; color: #b5131d; font-weight: bold; margin-left: 10px;">Limpar Filtro</a>
                    <% } %>
                </form>
            </div>
            <table class="table-lista-fichas">
                <tr>
                    <th>Status</th>
                    <th>Opções</th>
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
                <c:forEach items="${listaFichas}" var="ficha">
                    <tr>
                        <td>${ficha.getStatus().getStatus()}</td>
                        <td class="opcoes-col">
                            <c:choose>
                                <c:when test="${ficha.getStatus().getId() == 2}">
                                    <a class="option-btn desabilitado">
                                        <img class="option-btn-img" src="../resources/imgs/editar.png" alt="editar"/>
                                    </a>
                                    <button class="option-btn delete desabilitado" disabled>
                                        <img class="option-btn-img" src="../resources/imgs/lixo.png" alt="alt"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <a href="../edits/ficha15.jsp?id=${ficha.getId()}" class="option-btn" title="Editar">
                                        <img class="option-btn-img" src="../resources/imgs/editar.png" alt="editar"/>
                                    </a>
                                    <button class="option-btn delete" title="Excluir" data-id="${ficha.getId()}" data-ficha="ficha15">
                                        <img class="option-btn-img" src="../resources/imgs/lixo.png" alt="alt"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${ficha.getPais().getNome()}</td>
                        <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                        <td>${ficha.getMetodoValoracao()}</td>
                        <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorDatabase())}</td>
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
                                    ${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getSaldoDatabase())}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getAluguelRecebido())}</td>
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
        <script src="/ProjetoCBE/resources/js/CalcularDiferenca.js"></script>
        <script src="/ProjetoCBE/resources/js/validacao.js"></script>
        <script src="/ProjetoCBE/resources/js/delecao.js"></script>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
    </body>
</html>