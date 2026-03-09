<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="uor_equipe" value="${sessionScope.uorEquipe}" />
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formulário Declaração CBE</title>
        <link rel="stylesheet" href="resources/css/style.css"/>
    </head>
    <body>
        <%@include file="topo.jsp"%>
        <main class="main">
            <article class="article">
                <h2>Fichas</h2>
                <div class="btns-index-container">
                    <div>
                        <button class="btn-exibicao-fichas todas" style="margin-bottom: 5px">Todas as fichas</button>
                        
                        <button class="btn-exibicao-fichas equipe">Fichas da equipe</button>
                    </div>
                    <c:choose>
                        <c:when test = "${uor_equipe == '284307' || uor_equipe == '284308'}">
                            <div>
                                <a href="filtroPtax.jsp" class="btn" style="margin-left: 270px">PTAX</a>
                            </div>
                            <div >
                                <a href="filtro4010Conciliacao.jsp" class="btn" style="margin-left: 10px">Conciliação</a>
                            </div>
                            <div >
                                <a href="filtroRelatorio.jsp" class="btn" style="margin-left: 10px">Relatório</a>
                            </div>
                            
                        </c:when>
                    </c:choose>
                </div>
                <br>
                <div class="links-container">
                    <div class="table-todas">
                        <p>Abaixo estão todas as fichas do CBE:</p>
                        <table class="table-lista-forms">
                            <tr>
                                <td>Empresas</td>
                                <td><a class="btn voltar" href="views/empresas.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 1 - Ações negociadas em bolsa</td>
                                <td><a class="btn voltar" href="views/ficha01.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 2 - Brazilian depositary receipt</td>
                                <td><a class="btn voltar" href="views/ficha02.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 3 - Câmbio Manual</td>
                                <td><a class="btn voltar" href="views/ficha03.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 6 - Depositary receipt - Empresa brasileira</td>
                                <td><a class="btn voltar" href="views/ficha06.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 7 - Depositary receipt - Empresa não-brasileira </td>
                                <td><a class="btn voltar" href="views/ficha07.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 8 - Depósitos à vista e a prazo</td>
                                <td><a class="btn voltar" href="views/ficha08.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 9 - Derivativo - Futuro e swap</td>
                                <td><a class="btn voltar" href="views/ficha09.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 10 - Derivativo – Opção</td>
                                <td><a class="btn voltar" href="views/ficha10.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 11 - Empresas – Participação no capital</td>
                                <td><a class="btn voltar" href="views/ficha11.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 12 - Empréstimo intercompanhia</td>
                                <td><a class="btn voltar" href="views/ficha12.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 13 - Empréstimo não-intercompanhia</td>
                                <td><a class="btn voltar" href="views/ficha13.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 14 - Fundos de Investimento</td>
                                <td><a class="btn voltar" href="views/ficha14.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 15 - Imóvel</td>
                                <td><a class="btn voltar" href="views/ficha15.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 16 - Outros direitos</td>
                                <td><a class="btn voltar" href="views/ficha16.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 17 - Título de dívida intercompanhia</td>
                                <td><a class="btn voltar" href="views/ficha17.jsp">Visualizar</a></td>
                            </tr>
                            <tr>
                                <td>Ficha 18 - Título de dívida não-intercompanhia</td>
                                <td><a class="btn voltar" href="views/ficha18.jsp">Visualizar</a></td>
                            </tr>
                        </table>
                    </div>
                    <div class="table-equipe">
                        <p>Abaixo estão as fichas que você deve preencher:</p>
                        <table class="table-lista-forms table-equipe">
                            <%-- DIRCO --%>
                            <c:choose>
                                <c:when test = "${uor_equipe == '284307' || uor_equipe == '284308'}">
                                    <tr>
                                        <td>Empresas</td>
                                        <td><a class="btn voltar" href="views/empresas.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 1 - Ações negociadas em bolsa</td>
                                        <td><a class="btn voltar" href="views/ficha01.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 2 - Brazilian depositary receipt</td>
                                        <td><a class="btn voltar" href="views/ficha02.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 3 - Câmbio Manual</td>
                                        <td><a class="btn voltar" href="views/ficha03.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 6 - Depositary receipt - Empresa brasileira</td>
                                        <td><a class="btn voltar" href="views/ficha06.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 7 - Depositary receipt - Empresa não-brasileira </td>
                                        <td><a class="btn voltar" href="views/ficha07.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 8 - Depósitos à vista e a prazo</td>
                                        <td><a class="btn voltar" href="views/ficha08.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 9 - Derivativo - Futuro e swap</td>
                                        <td><a class="btn voltar" href="views/ficha09.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 10 - Derivativo – Opção</td>
                                        <td><a class="btn voltar" href="views/ficha10.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 11 - Empresas – Participação no capital</td>
                                        <td><a class="btn voltar" href="views/ficha11.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 12 - Empréstimo intercompanhia</td>
                                        <td><a class="btn voltar" href="views/ficha12.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 13 - Empréstimo não-intercompanhia</td>
                                        <td><a class="btn voltar" href="views/ficha13.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 14 - Fundos de Investimento</td>
                                        <td><a class="btn voltar" href="views/ficha14.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 15 - Imóvel</td>
                                        <td><a class="btn voltar" href="views/ficha15.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 16 - Outros direitos</td>
                                        <td><a class="btn voltar" href="views/ficha16.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 17 - Título de dívida intercompanhia</td>
                                        <td><a class="btn voltar" href="views/ficha17.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 18 - Título de dívida não-intercompanhia</td>
                                        <td><a class="btn voltar" href="views/ficha18.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when >
                                <%--UNI/CONTROLE COMPLIANCE --%>
                                <%-- !--0 - 8 - 12 - 13--%>
                                <c:when test = "${uor_equipe == 519272}">
                                    <tr>
                                        <td>Empresas</td>
                                        <td><a class="btn voltar" href="views/empresas.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 8 - Depósitos à vista e a prazo</td>
                                        <td><a class="btn voltar" href="views/ficha08.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 12 - Empréstimo intercompanhia</td>
                                        <td><a class="btn voltar" href="views/ficha12.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 13 - Empréstimo não-intercompanhia</td>
                                        <td><a class="btn voltar" href="views/ficha13.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <%--UGE/GER. SOLUÇÕES DE APOIO A GOV--%>
                                <%--0 - 1 - 11--%>
                                <c:when test = "${uor_equipe == 327821}">
                                    <tr>
                                        <td>Empresas</td>
                                        <td><a class="btn voltar" href="views/empresas.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 1 - Ações negociadas em bolsa</td>
                                        <td><a class="btn voltar" href="views/ficha01.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 11 - Empresas – Participação no capital</td>
                                        <td><a class="btn voltar" href="views/ficha11.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <%--UGE/GER. ENTIDADES LIGADAS II--%>
                                <%--0 - 1 - 11--%>
                                <c:when test = "${uor_equipe == 283575}">
                                    <tr>
                                        <td>Empresas</td>
                                        <td><a class="btn voltar" href="views/empresas.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 1 - Ações negociadas em bolsa</td>
                                        <td><a class="btn voltar" href="views/ficha01.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 11 - Empresas – Participação no capital</td>
                                        <td><a class="btn voltar" href="views/ficha11.jsp">Visualizar</a></td>
                                    </tr>                            
                                </c:when>
                                <%--UGE/GER. ENTIDADES LIGADAS IV--%>
                                <%--14--%>
                                <c:when test = "${uor_equipe == 327819}">
                                    <tr>
                                        <td>Empresas</td>
                                        <td><a class="btn voltar" href="views/empresas.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 1 - Ações negociadas em bolsa</td>
                                        <td><a class="btn voltar" href="views/ficha01.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 11 - Empresas – Participação no capital</td>
                                        <td><a class="btn voltar" href="views/ficha11.jsp">Visualizar</a></td>
                                    </tr>                            
                                    <tr>
                                        <td>Ficha 14 - Fundos de Investimento</td>
                                        <td><a class="btn voltar" href="views/ficha14.jsp">Visualizar</a></td>
                                    </tr>                                
                                </c:when>
                                <%--DIOPE/GER.RENDA VARIAVEL--%>
                                <%--2 - 6 - 7 - 9 - 10--%>
                                <c:when test = "${uor_equipe == 283390}">
                                    <tr>
                                        <td>Ficha 2 - Brazilian depositary receipt</td>
                                        <td><a class="btn voltar" href="views/ficha02.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 6 - Depositary receipt - Empresa brasileira</td>
                                        <td><a class="btn voltar" href="views/ficha06.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 7 - Depositary receipt - Empresa não-brasileira </td>
                                        <td><a class="btn voltar" href="views/ficha07.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 9 - Derivativo - Futuro e swap</td>
                                        <td><a class="btn voltar" href="views/ficha09.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 10 - Derivativo – Opção</td>
                                        <td><a class="btn voltar" href="views/ficha10.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <%--DIOPE/GER. NEGOCIOS DE VALORES--%>
                                <%--3--%>
                                <c:when test = "${uor_equipe == 507531}">
                                    <tr>
                                        <td>Ficha 3 - Câmbio Manual</td>
                                        <td><a class="btn voltar" href="views/ficha03.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <%--TESOU--%>
                                <%--8--%>
                                <c:when test = "${uor_equipe == 457192 || uor_equipe == 457172}">
                                    <tr>
                                        <td>Ficha 8 - Depósitos à vista e a prazo</td>
                                        <td><a class="btn voltar" href="views/ficha08.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <%--UNI/CONSULTORIA E SERVIÇOS--%>
                                <%--11--%>
                                <c:when test="${uor_equipe == 519271}">
                                    <tr>
                                        <td>Ficha 11 - Empresas – Participação no capital</td>
                                        <td><a class="btn voltar" href="views/ficha11.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <%--COGER/GEVID CODEX--%>
                                <%--11--%>
                                <c:when test = "${uor_equipe == 284073}">
                                    <tr>
                                        <td>Ficha 11 - Empresas – Participação no capital</td>
                                        <td><a class="btn voltar" href="views/ficha11.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <%--COGER/GECOE COBAM 2--%>
                                <%--14--%>
                                <c:when test = "${uor_equipe == 283901}">
                                    <tr>
                                        <td>Ficha 14 - Fundos de Investimento</td>
                                        <td><a class="btn voltar" href="views/ficha14.jsp">Visualizar</a></td>
                                    </tr>  
                                </c:when>
                                <%--DISEC--%>
                                <%--15--%>
                                <c:when test = "${uor_equipe == 510211}">
                                    <tr>
                                        <td>Ficha 15 - Imóvel</td>
                                        <td><a class="btn voltar" href="views/ficha15.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <%--DIMEP--%>
                                <%--16--%>
                                <c:when test = "${uor_equipe == 283870}">
                                    <tr>
                                        <td>Ficha 16 - Outros direitos</td>
                                        <td><a class="btn voltar" href="views/ficha16.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <%--DIOPE/SERVIÇOS ESPECIALIZADOS--%>
                                <%--16--%>
                                <c:when test = "${uor_equipe == 283273 || uor_equipe == 285143}">
                                    <tr>
                                        <td>Ficha 16 - Outros direitos</td>
                                        <td><a class="btn voltar" href="views/ficha16.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <%--DIFIN--%>
                                <%--17 - 18--%>
                                <c:when test = "${uor_equipe == 490374}">
                                    <tr>
                                        <td>Ficha 17 - Título de dívida intercompanhia</td>
                                        <td><a class="btn voltar" href="views/ficha17.jsp">Visualizar</a></td>
                                    </tr>
                                    <tr>
                                        <td>Ficha 18 - Título de dívida não-intercompanhia</td>
                                        <td><a class="btn voltar" href="views/ficha18.jsp">Visualizar</a></td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <p>Caso esteja vendo essa mensagem ao invés da lista, entre em contato com
                                        <a href="https://teams.microsoft.com/l/chat/0/0?users=deusi@bb.com.br" target="_blank" class="link-teams">Deusiane Caldas</a>
                                    </p>
                                </c:otherwise>
                            </c:choose>
                        </table>
                    </div>
                </div>
            </article>
        </main>

        <footer>
            <img src="resources/imgs/logo_azul.png" alt=" Banco do Brasil "</span>
            <a target="_blank" href="https://www.bcb.gov.br/estabilidadefinanceira/manualcbe"> #ManualCBE </a>            
            <a target="_blank" href="https://teams.microsoft.com/l/chat/0/0?users=deusi@bb.com.br"> #Contato </a> <%-- Atribuição a pessoa responsável ao CBE --%>
        </footer>
                   
        <script src="resources/js/index.js"></script>
    </body>
</html>