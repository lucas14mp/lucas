<!-- =========================== VIEW =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="ficha03Controller" class="br.com.bb.cbe.controllers.Ficha19Controller"/>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Ficha 19</title>
    </head>
    <body>
        <!--CONTEX PATH PRA USAR NA VALIDAÇÃO-->
        <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
        <%@include file="../topo.jsp"%>
        <div class="view-container">
            <div class="topo-view">
                <h2>Ficha 19 - Exportação de mercadoria</h2>
                <div>
                    <c:choose>
                        <c:when test = "${comissao.startsWith('GER SOLUCOES')}">
                            <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha03" id="valida" title="É necessário ter o cargo de gerente para validar as informações">
                        </c:when>
                        <c:otherwise>
                            <input type="button" class="btn btn-validar btn-disabled" value="Validar" title="É necessário ter o cargo de gerente para validar as informações" disabled>
                        </c:otherwise>
                    </c:choose>
                    <a href="../index.jsp"><input type="button" value="Voltar" class="btn" id="voltar"></a>
                    <a href="../forms/ficha19.jsp"><input type="button" value="Adicionar" class="btn"></a>
                </div>
            </div>
            <table class="table-lista-fichas">
                <tr>
                    <th>Status</th>
                    <th>Opções</th>
                    <th>A empresa declarante exportou mercadorias durante o período-base da declaração?</th>
                    <th>Última atualização</th>
                    <th>Funcionário</th>
                </tr>
                <c:forEach items="${ficha19Controller.getAllFichas()}" var="ficha">
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
                                    <a href="../edits/ficha03.jsp?id=${ficha.getId()}" class="option-btn" title="Editar">
                                        <img class="option-btn-img" src="../resources/imgs/editar.png" alt="editar"/>
                                    </a>
                                    <button class="option-btn delete" title="Excluir" data-id="${ficha.getId()}" data-ficha="ficha03">
                                        <img class="option-btn-img" src="../resources/imgs/lixo.png" alt="alt"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
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
