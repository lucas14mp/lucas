<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha11EmpresaController" %>
<%@ page import="br.com.bb.cbe.controllers.Ficha11MaiorController" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha11Controle" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha11Maior" %>
<%@ page import="java.util.List" %>
<%
    Ficha11EmpresaController fichaController = new Ficha11EmpresaController();
    Ficha11MaiorController fichaMaiorController = new Ficha11MaiorController();
    int idFichaControladora = Integer.parseInt(request.getParameter("id"));
    Ficha11Maior fichaControladora = fichaMaiorController.getFichaById(idFichaControladora);
    List<Ficha11Controle> fichas = fichaController.getAllFichasByControladoraId(idFichaControladora);
    pageContext.setAttribute("fichas", fichas);
    pageContext.setAttribute("fichaControladora", fichaControladora);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Empresas - Ficha 11</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <div class="view-container">
            <div class="topo-view">
                <p>Empresas controladas pela empresa <strong>${fichaControladora.getEmpresa().getNome()}</strong></p>
                <div>
                    <a href="/ProjetoCBE/views/ficha11.jsp"><input type="button" value="Voltar" class="btn" id="voltar"></a>
                    <a href="/ProjetoCBE/forms/ficha11Empresa.jsp?id=<%= fichaControladora.getId()%>"><input type="button" value="Adicionar" class="btn"></a>
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
            </div>
            <table class="table-lista-fichas" id="controle">
                <tr>
                    <th>Nome da empresa controladora</th>
                    <th>Opções</th>
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
                <c:forEach items="${fichas}" var="ficha">
                    <tr>
                        <td>${ficha.getFicha11Controladora().getEmpresa().getNome()}</td>
                        <td class="opcoes-col">
                            <c:choose>
                                <c:when test="${fichaControladora.getStatus().getId() == 2}">
                                    <a class="option-btn desabilitado">
                                        <img class="option-btn-img" src="../resources/imgs/editar.png" alt="editar"/>
                                    </a>
                                    <button class="option-btn delete desabilitado" disabled>
                                        <img class="option-btn-img" src="../resources/imgs/lixo.png" alt="alt"/>
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <a href="../edits/ficha11Empresa.jsp?idFichaMaior=${fichaControladora.getId()}&id=${ficha.getId()}" class="option-btn" title="Editar">
                                        <img class="option-btn-img" src="../resources/imgs/editar.png" alt="editar"/>
                                    </a>
                                    <button class="option-btn delete" title="Excluir" data-id="${ficha.getId()}" data-ficha="ficha11Empresa" data-maior="${fichaControladora.getId()}">
                                        <img class="option-btn-img" src="../resources/imgs/lixo.png" alt="alt"/>
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${ficha.getNome()}</td>
                        <td>${ficha.getPais().getNome()}</td>
                        <td>${ficha.getAtividadeEcn()}</td>
                        <td>${numeroUtils.doubleToString(ficha.getParticipacaoCapital())}${ficha.getParticipacaoCapital() != -0.01 ? "%" : ""}</td>
                        <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                        <td>${ficha.getPatrimonioLiquido() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getPatrimonioLiquido() != -0.01 ? numeroUtils.doubleToString(ficha.getPatrimonioLiquido()) : "Não informado"}</td>
                        <td>${ficha.getValorMercado() != -0.01 ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getValorMercado() != -0.01 ? numeroUtils.doubleToString(ficha.getValorMercado()) : "Não informado"}</td>
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
        </div>
        <script src="/ProjetoCBE/resources/js/delecao.js"></script>
        <script src="/ProjetoCBE/resources/js/ficha0.js"></script>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
    </body>
</html>