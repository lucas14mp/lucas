<!-- =========================== VIEW =========================== -->
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%--<%@ page import="com.google.gson.Gson" %>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.com.bb.cbe.Bean.Ficha11Menor"%>
<%@page import="br.com.bb.cbe.Bean.Ficha11Maior"%>
<%@page import="br.com.bb.cbe.Bean.Moeda"%>
<%@page import="br.com.bb.cbe.Bean.Ptax"%>
<jsp:useBean id="ficha11EmpresaController" class="br.com.bb.cbe.controllers.Ficha11EmpresaController"/>
<jsp:useBean id="ficha11MaiorController" class="br.com.bb.cbe.controllers.Ficha11MaiorController"/>
<jsp:useBean id="ficha11MenorController" class="br.com.bb.cbe.controllers.Ficha11MenorController"/>
<jsp:useBean id="MoedaController" class="br.com.bb.cbe.controllers.MoedaController"/>
<jsp:useBean id="PtaxController" class="br.com.bb.cbe.controllers.PtaxController"/>
<jsp:useBean id="ContabilController" class="br.com.bb.cbe.controllers.ContabilController"/>
<jsp:useBean id="ficha11Menor" class="br.com.bb.cbe.Bean.Ficha11Menor"/>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%
    // Lógica do Filtro Inteligente Limpo
    String anoFiltro = request.getParameter("anoFiltro");
    String trimestreFiltro = request.getParameter("trimestreFiltro");
    String empresaFiltro = request.getParameter("empresaFiltro");
    
    // O DAO inteligente cuida de todas as combinações!
    List<br.com.bb.cbe.Bean.Ficha11Maior> listaFichasMaior = ficha11MaiorController.readComFiltros(trimestreFiltro, anoFiltro, empresaFiltro);
    List<br.com.bb.cbe.Bean.Ficha11Menor> listaFichasMenor = ficha11MenorController.readComFiltros(trimestreFiltro, anoFiltro);
    
    pageContext.setAttribute("listaFichasMaior", listaFichasMaior);
    pageContext.setAttribute("listaFichasMenor", listaFichasMenor);
    pageContext.setAttribute("anosDisponiveis", ficha11MaiorController.getAnosExistentes());
    pageContext.setAttribute("trimestresDisponiveis", ficha11MaiorController.getTrimestresExistentes());
%>

<!DOCTYPE html>  
<html>
  <head>
    <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lista Ficha 11</title>
  </head>
  <body>
      
    <%@include file="../topo.jsp"%>
      
    <div class="main-container" style="padding-top: 30px;">        
        
        <input type="hidden" id="taxas" value="${taxas}">
        <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
        
    <div class="view-container">
      <div class="topo-view">
        <h2>Ficha 11 - Empresas - Participação no capital</h2> 
        <div>
          <c:choose>
              <c:when test = "${comissao.startsWith('GER') || chave == 'T1092713'}">
                  <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha11/menor" style="display: none;" id="valida-menor" title="É necessário ter o cargo de gerente para validar as informações">
                  <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha11/maior" style="display: none;" id="valida-maior" title="É necessário ter o cargo de gerente para validar as informações">
              </c:when>
              <c:otherwise>
                  <input type="button" class="btn btn-validar btn-disabled" value="Validar" style="display: none;" id="valida-menor" title="É necessário ter o cargo de gerente para validar as informações" disabled>
                  <input type="button" class="btn btn-validar btn-disabled" value="Validar" style="display: none;" id="valida-maior" title="É necessário ter o cargo de gerente para validar as informações" disabled>
              </c:otherwise>
          </c:choose>
          <a href="../index.jsp"><input type="button" value="Voltar" class="btn" id="voltar"></a>
          <a href="../forms/ficha11.jsp"><input type="button" value="Adicionar" class="btn"></a>
        </div>
      </div>
      <div>
          <c:forEach var="taxa" items="${taxas}">
              <p>${taxa}</p>
          </c:forEach>
      </div>
              <div class="filtro-container" style="width: 95%; margin: 0 auto 20px auto; background-color: #f4f4f4; padding: 15px; border-radius: 8px; border: 1px solid #ddd;">
            <form action="ficha11.jsp" method="GET" style="display: flex; gap: 15px; align-items: center; flex-wrap: wrap;">
                
                <input type="hidden" name="resposta-participacao" id="inputTipoParticipacao" value="<%= request.getParameter("resposta-participacao") != null ? request.getParameter("resposta-participacao") : "menor-que-10" %>">

                <div class="div-opcoes" style="display: flex; align-items: center;">
                    <label for="anoFiltro" style="font-weight: bold; color: #0038a8; margin-right: 5px;">Ano:</label>
                    <select name="anoFiltro" id="anoFiltro" style="padding: 8px; border-radius: 4px; border: 1px solid #ccc; width: auto; margin: 0;">
                        <option value="todos">Todos</option>
                        <c:forEach items="${anosDisponiveis}" var="ano">
                            <option value="${ano}" ${param.anoFiltro == ano.toString() ? 'selected' : ''}>${ano}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="div-opcoes" style="display: flex; align-items: center;">
                    <label for="trimestreFiltro" style="font-weight: bold; color: #0038a8; margin-right: 5px;">Trimestre:</label>
                    <select name="trimestreFiltro" id="trimestreFiltro" style="padding: 8px; border-radius: 4px; border: 1px solid #ccc; width: auto; margin: 0;">
                        <option value="todos">Todos</option>
                        <c:forEach items="${trimestresDisponiveis}" var="trim">
                            <option value="${trim}" ${param.trimestreFiltro == trim.toString() ? 'selected' : ''}>${trim}º Trimestre</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="div-opcoes" id="blocoEmpresa" style="display: none; align-items: center;">
                    <label for="empresaFiltro" style="font-weight: bold; color: #0038a8; margin-right: 5px;">Empresa:</label>
                    <select name="empresaFiltro" id="empresaFiltro" style="padding: 8px; border-radius: 4px; border: 1px solid #ccc; width: auto; margin: 0; max-width: 250px;">
                        <option value="todos">Todas</option>
                        <c:forEach items="${empresaController.listarEmpresas()}" var="emp">
                            <option value="${emp.id}" ${param.empresaFiltro == emp.id.toString() ? 'selected' : ''}>${emp.nome}</option>
                        </c:forEach>
                    </select>
                </div>

                <button type="submit" class="btn" style="margin: 0; height: 40px; background-color: #0038a8; color: white;">Filtrar</button>
                
                <% if ((anoFiltro != null && !anoFiltro.equals("todos")) || 
                       (trimestreFiltro != null && !trimestreFiltro.equals("todos")) ||
                       (empresaFiltro != null && !empresaFiltro.equals("todos"))) { %>
                    <a href="ficha11.jsp" style="text-decoration: none; color: #b5131d; font-weight: bold; margin-left: 10px;">Limpar Filtro</a>
                <% } %>
            </form>
        </div>
      <div class="opcoes-ficha-container">
        <p>Porcentagem de poder de voto na empresa:</p>
        <br>
        <div class="label-container">
          <label class="label-radio-menor">
            <input type="radio" name="resposta-participacao" id="danger-outlined" value="menor">
            Poder de voto <b>menor</b> que 10%
          </label>
          <label class="label-radio-maior">
            <input type="radio" name="resposta-participacao" id="success-outlined" value="maior">
            Poder de voto <b>maior</b> ou <b>igual</b> a 10%
          </label>
        </div>
      </div>

      <!--Tabela menor que 10%-->
      <div id="tabelaMenor" style="display: none;">
        <table class="table-lista-fichas" id="menor">
          <tr>
            <th>Status</th>
            <th>Opções</th>
            <th>País da empresa no exterior</th>
            <th>Moeda do país da empresa no exterior</th>
            <th>Método de valoração</th>
            <th>Valor de participação na empresa na data-base</th>
            <th>Lucro distribuído ao declarante</th>
            <th>Última atualização</th>
            <th>Funcionário</th>
          </tr>
          <c:forEach items="${listaFichasMenor}" var="ficha">
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
                          <a href="../edits/ficha11Menor.jsp?idMenor=${ficha.getId()}" class="option-btn" title="Editar">
                            <img class="option-btn-img" src="../resources/imgs/editar.png" alt="editar"/>
                          </a>
                          <button class="option-btn delete" title="Excluir" data-id="${ficha.getId()}" data-ficha="ficha11/menor">
                            <img class="option-btn-img" src="../resources/imgs/lixo.png" alt="alt"/>
                          </button>
                      </c:otherwise>
                  </c:choose>
                </td>
                <td>${ficha.getPais().getNome()}</td>
                <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                <td>${ficha.getMetodoValoracao()}</td>
                <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorParticipacao())}</td>
                <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getLucroDistribuido())}</td>
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

      <!--Tabela maior que 10%-->
      <div id="tabelaMaior" class="container-maior" style="display: none;">
        <table class="table-lista-fichas maior" id="maior">
          <tr>
            <th>Status</th>
            <th>Opções</th>
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
            <th>Última atualização</th>
            <th>Funcionário</th>
          </tr>
          <c:forEach items="${listaFichasMaior}" var="ficha">
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
                          <a href="../edits/ficha11Maior.jsp?idMaior=${ficha.getId()}" class="option-btn" title="Editar">
                            <img class="option-btn-img" src="../resources/imgs/editar.png" alt="editar"/>
                          </a>
                          <button class="option-btn delete" title="Excluir" data-id="${ficha.getId()}" data-ficha="ficha11/maior">
                            <img class="option-btn-img" src="../resources/imgs/lixo.png" alt="alt"/>
                          </button>
                      </c:otherwise>
                  </c:choose>
                </td>
                <td>${ficha.getEmpresa().getNome()}</td>
                <td>
                  <c:choose>
                      <c:when test="${ficha.getPossuiCotacaoEmBolsa() != null && ficha.getPossuiCotacaoEmBolsa()}">
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
                      <c:when test="${ficha.getControlaEmpresa() != null && ficha.getControlaEmpresa()}">
                          <p>Sim</p>
                          <a class="option-btn visualizar" title="Visualizar empresas controladas" href="empresas-controladas11.jsp?id=${ficha.getId()}">
                            <img class="option-btn-img" src="../resources/imgs/lupa.png" alt="alt"/>
                          </a>
                      </c:when>
                      <c:otherwise>
                          <p>Não</p>
                      </c:otherwise>
                  </c:choose>
                </td>
                <td>${ficha.getValorEmpresa() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getValorEmpresa() != null ? numeroUtils.doubleToString(ficha.getValorEmpresa()) : "-"}</td>
                <td>${ficha.getPatrimonioTotal() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getPatrimonioTotal() != null ? numeroUtils.doubleToString(ficha.getPatrimonioTotal()) : "-"}</td>
                <td>${ficha.getPorcentoParticipacaoCapital() != null ? numeroUtils.doubleToString(ficha.getPorcentoParticipacaoCapital()) : "-"}${ficha.getPorcentoParticipacaoCapital() != null ? "%" : ""}</td>
                <td>${ficha.getPorcentoPoderVoto() != null ? numeroUtils.doubleToString(ficha.getPorcentoPoderVoto()) : "-"}${ficha.getPorcentoPoderVoto() != null ? "%" : ""}</td>
                <td>${ficha.getAtivoDatabase() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getAtivoDatabase() != null ? numeroUtils.doubleToString(ficha.getAtivoDatabase()) : "-"}</td>
                <td>${ficha.getPassivoExigivel() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getPassivoExigivel() != null ? numeroUtils.doubleToString(ficha.getPassivoExigivel()) : "-"}</td>
                <td>${ficha.getValorTotalLucroPrejuizo() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getValorTotalLucroPrejuizo() != null ? numeroUtils.doubleToString(ficha.getValorTotalLucroPrejuizo()) : "-"}</td>
                <td>${ficha.getResultadoLiquidoItensNaoRecorrentes() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getResultadoLiquidoItensNaoRecorrentes() != null ? numeroUtils.doubleToString(ficha.getResultadoLiquidoItensNaoRecorrentes()) : "-"}</td>
                <td>${ficha.getResultadoLiquidoReavaliacoes() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getResultadoLiquidoReavaliacoes() != null ? numeroUtils.doubleToString(ficha.getResultadoLiquidoReavaliacoes()) : "-"}</td>
                <td>${ficha.getResultadoLiquidoVariacaoCambial() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getResultadoLiquidoVariacaoCambial() != null ? numeroUtils.doubleToString(ficha.getResultadoLiquidoVariacaoCambial()) : "-"}</td>
                <td>${ficha.getLucroDistribuido() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getLucroDistribuido() != null ? numeroUtils.doubleToString(ficha.getLucroDistribuido()) : "-"}</td>
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
    <script>       
        document.addEventListener("DOMContentLoaded", function () {
            const respostaInputs = document.querySelectorAll('input[name="resposta-participacao"]');
            const perguntasFormMenor = document.getElementById('tabelaMenor');
            const perguntasFormMaior = document.getElementById('tabelaMaior');
            const validarMenor = document.querySelector("#valida-menor");
            const validarMaior = document.querySelector("#valida-maior");

            respostaInputs.forEach(input => {
                input.addEventListener('click', function () {
                    if (input.value === 'menor') {
                        window.localStorage.setItem("opcaoFicha11", "menor");
                        perguntasFormMenor.style.display = 'block';
                        perguntasFormMaior.style.display = 'none';
                        validarMenor.style.display = 'inline-block';
                        validarMaior.style.display = 'none';
                    } else if (input.value === 'maior') {
                        window.localStorage.setItem("opcaoFicha11", "maior");
                        perguntasFormMenor.style.display = 'none';
                        perguntasFormMaior.style.display = 'block';
                        validarMenor.style.display = 'none';
                        validarMaior.style.display = 'inline-block';
                    } else {
                        perguntasFormMenor.style.display = 'none';
                        perguntasFormMaior.style.display = 'none';
                    }
                });
            });
            if (window.localStorage.getItem("opcaoFicha11") === "maior") {
                perguntasFormMenor.style.display = 'none';
                perguntasFormMaior.style.display = 'block';
                validarMenor.style.display = 'none';
                validarMaior.style.display = 'inline-block';
                respostaInputs[1].checked = true;
            } else if (window.localStorage.getItem("opcaoFicha11") === "menor") {
                perguntasFormMenor.style.display = 'block';
                perguntasFormMaior.style.display = 'none';
                validarMenor.style.display = 'inline-block';
                validarMaior.style.display = 'none';
                respostaInputs[0].checked = true;
            }
        });
    </script>
    <script>
        var fichasJson = "";
        var taxasJson = '${taxasJson}';
        var fichasMenorJson = '${fichasMenorJson}';
        var contabilJson = '${contabilJson}';
    </script>
    <script src="/ProjetoCBE/resources/js/CalcularDiferenca.js"></script>
    <script src="/ProjetoCBE/resources/js/validacao.js"></script>
    <script src="/ProjetoCBE/resources/js/delecao.js"></script>
    <script src="/ProjetoCBE/resources/js/temas.js"></script>
    <script>
    $(document).ready(function() {
        function checarFiltroEmpresa() {
            var tipoMarcado = $('input[name="resposta-participacao"]:checked').val() || $('#inputTipoParticipacao').val();
            if (tipoMarcado === 'maior-que-10') {
                $('#blocoEmpresa').css('display', 'flex');
            } else {
                $('#blocoEmpresa').hide();
                $('#empresaFiltro').val('todos'); 
            }
        }
        checarFiltroEmpresa(); // Roda ao abrir a tela
        
        // Roda sempre que o usuário clicar na bolinha do seu filtro original de Maior/Menor
        $('input[name="resposta-participacao"]').on('change', function() {
            $('#inputTipoParticipacao').val($(this).val());
            checarFiltroEmpresa();
        });
    });
    </script>
  </body>
</html>