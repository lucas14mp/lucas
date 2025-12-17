<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@ page import="com.google.gson.Gson" %>
<!-- =========================== VIEW =========================== -->
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

<!DOCTYPE html>  
<html>
  <head>
    <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lista Ficha 11</title>
  </head>
  <body>
   
      <%
        
        String taxasJson = PtaxController.getAllTaxasJson();
        String fichasMenorJson = ficha11MenorController.getAllFichasJson();
        String contabilJson = ContabilController.getAllCosifsJson();
//        List<Moeda> moedas = new ArrayList<>();
//        Moeda moeda = MoedaController.getMoedaById(1);
        
//        for(Ficha11Menor ficha: fichasMenor){
//            Moeda moedinha = ficha.getMoeda();
//            System.out.println("MOEDA " + moedinha.getNome());
//            moedas.add(moedinha);
//          }
        
//        String nome = moeda.getNome();
        int trimestre = dataUtils.validaTrimestre();
        SimpleDateFormat anoFormat = new SimpleDateFormat("yyyy");
        int ano = Integer.parseInt(anoFormat.format(new Date()));
        pageContext.setAttribute("trimestre", trimestre);
        pageContext.setAttribute("ano", ano);
//        request.setAttribute("nome", nome);
        request.setAttribute("taxasJson", taxasJson);
        request.setAttribute("fichasMenorJson", fichasMenorJson);
        request.setAttribute("contabilJson", contabilJson);
      %>
      
      
      <input type="hidden" id="taxas" value="${taxas}">
      <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
    <%@include file="../topo.jsp"%>
    <div class="view-container">
      <div class="topo-view">
        <h2>Ficha 11 - Empresas - Participação no capital</h2> 
        <div>
          <c:choose>
              <c:when test = "${comissao.startsWith('GER SOLUCOES')}">
                  <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha11/menor" style="display: none;" id="valida-menor" title="É necessário ter o cargo de gerente para validar as informações">
                  <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha11/maior" style="display: none;" id="valida-maior" title="É necessário ter o cargo de gerente para validar as informações">
              </c:when>
              <c:otherwise>
                  <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha11/menor" style="display: none;" id="valida-menor" title="É necessário ter o cargo de gerente para validar as informações">
                  <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha11/maior" style="display: none;" id="valida-maior" title="É necessário ter o cargo de gerente para validar as informações">
              </c:otherwise>
          </c:choose>
          <a href="../index.jsp"><input type="button" value="Voltar" class="btn" id="voltar"></a>
          <a href="../forms/ficha11.jsp"><input type="button" value="Adicionar" class="btn"></a>
        </div>
      </div>
      <br><br>
      <div>
          <c:forEach var="taxa" items="${taxas}">
              <p>${taxa}</p>
          </c:forEach>
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
          <c:forEach items="${ficha11MenorController.getAllFichas()}" var="ficha">
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
          <c:forEach items="${ficha11MaiorController.getAllFichasByTrimestreAno(trimestre - 1, ano)}" var="ficha">
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
                          <a class="option-btn visualizar" title="Visualizar empresas controladas" href="empresas-controladas11.jsp?id=${ficha.getId()}">
                            <img class="option-btn-img" src="../resources/imgs/lupa.png" alt="alt"/>
                          </a>
                      </c:when>
                      <c:otherwise>
                          <p>Não</p>
                      </c:otherwise>
                  </c:choose>
                </td>
                <td>${ficha.getValorEmpresa() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getValorEmpresa() != -0.01 ? numeroUtils.doubleToString(ficha.getValorEmpresa()) : "Não informado"}</td>
                <td>${ficha.getPatrimonioTotal() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getPatrimonioTotal() != -0.01 ? numeroUtils.doubleToString(ficha.getPatrimonioTotal()) : "Não informado"}</td>
                <td>${ficha.getPorcentoParticipacaoCapital() != -0.01 ? numeroUtils.doubleToString(ficha.getPorcentoParticipacaoCapital()) : "Não informado"}${ficha.getPorcentoParticipacaoCapital() != -0.01 ? "%" : ""}</td>
                <td>${ficha.getPorcentoPoderVoto() != -0.01 ? numeroUtils.doubleToString(ficha.getPorcentoPoderVoto()) : "Não informado"}${ficha.getPorcentoPoderVoto() != -0.01 ? "%" : ""}</td>
                <td>${ficha.getAtivoDatabase() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getAtivoDatabase() != -0.01 ? numeroUtils.doubleToString(ficha.getAtivoDatabase()) : "Não informado"}</td>
                <td>${ficha.getPassivoExigivel() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getPassivoExigivel() != -0.01 ? numeroUtils.doubleToString(ficha.getPassivoExigivel()) : "Não informado"}</td>
                <td>${ficha.getValorTotalLucroPrejuizo() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getValorTotalLucroPrejuizo() != -0.01 ? numeroUtils.doubleToString(ficha.getValorTotalLucroPrejuizo()) : "Não informado"}</td>
                <td>${ficha.getResultadoLiquidoItensNaoRecorrentes() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getResultadoLiquidoItensNaoRecorrentes() != -0.01 ? numeroUtils.doubleToString(ficha.getResultadoLiquidoItensNaoRecorrentes()) : "Não informado"}</td>
                <td>${ficha.getResultadoLiquidoReavaliacoes() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getResultadoLiquidoReavaliacoes() != -0.01 ? numeroUtils.doubleToString(ficha.getResultadoLiquidoReavaliacoes()) : "Não informado"}</td>
                <td>${ficha.getResultadoLiquidoVariacaoCambial() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getResultadoLiquidoVariacaoCambial() != -0.01 ? numeroUtils.doubleToString(ficha.getResultadoLiquidoVariacaoCambial()) : "Não informado"}</td>
                <td>${ficha.getLucroDistribuido() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getLucroDistribuido() != -0.01 ? numeroUtils.doubleToString(ficha.getLucroDistribuido()) : "Não informado"}</td>
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
  </body>
</html>
