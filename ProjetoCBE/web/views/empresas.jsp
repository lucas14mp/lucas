<!-- =========================== VIEW =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>
<!DOCTYPE html>
<html>
  <head>
    <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Lista de Empresas</title>
  </head>
  <body>
    <%@include file="../topo.jsp"%>
    <div class="view-container">
      <div class="topo-view">
        <h2>Lista de Empresas</h2>
        <div>
          <a href="../index.jsp"><input type="button" value="Voltar" class="btn" id="voltar"></a>
          <a href="../forms/ficha0.jsp"><input type="button" value="Adicionar" class="btn"></a>
        </div>
      </div>
      <table class="table-lista-fichas">
        <tr>
          <th>Opções</th>
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
            <td class="opcoes-col">
                  <a href="../edits/ficha0.jsp?id=${empresa.getId()}" class="option-btn" title="Editar">
                    <img class="option-btn-img" src="../resources/imgs/editar.png" alt="editar"/>
                  </a>
                  <button class="option-btn delete" title="Excluir" data-id="${empresa.getId()}" data-ficha="ficha0">
                    <img class="option-btn-img" src="../resources/imgs/lixo.png" alt="alt"/>
                  </button>
                </td>
            <td>${empresa.getNome()}</td>
            <td>${empresa.getPais().getNome()}</td>
            <td>
              <c:choose>
                <c:when test="${empresa.isTransacionaPaisesDiferentes()}">
                  <img src="../resources/imgs/yes.png" alt="Sim" width="20">
                </c:when>
                <c:otherwise>
                  <img src="../resources/imgs/no.png" alt="Não" width="20">
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
    <script src="/ProjetoCBE/resources/js/validacao.js"></script>
    <script src="/ProjetoCBE/resources/js/delecao.js"></script>
    <script src="/ProjetoCBE/resources/js/temas.js"></script>
  </body>
</html>
