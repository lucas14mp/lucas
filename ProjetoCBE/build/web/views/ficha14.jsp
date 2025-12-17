<!-- =========================== VIEW =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="ficha14EmpresaController" class="br.com.bb.cbe.controllers.Ficha14EmpresaController"/>
<jsp:useBean id="ficha14MaiorController" class="br.com.bb.cbe.controllers.Ficha14MaiorController"/>
<jsp:useBean id="ficha14MenorController" class="br.com.bb.cbe.controllers.Ficha14MenorController"/>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista Ficha 14</title>
    </head>
    <body>
        <!--CONTEX PATH PRA USAR NA VALIDAÇÃO-->
        <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
        <%@include file="../topo.jsp"%>
        <div class="view-container">
            <div class="topo-view">
                <h2>Ficha 14 - Fundos de Investimento</h2>
                <div>
                    <c:choose>
                        <c:when test = "${comissao.startsWith('GER SOLUCOES')}">
                            <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha14/menor" style="display: none;" id="valida-menor" title="É necessário ter o cargo de gerente para validar as informações">
                            <input type="button" class="btn btn-validar" value="Validar" data-ficha="ficha14/maior" style="display: none;" id="valida-maior" title="É necessário ter o cargo de gerente para validar as informações">
                        </c:when>
                        <c:otherwise>
                            <input type="button" class="btn btn-validar btn-disabled" value="Validar" style="display: none;" id="valida-menor" title="É necessário ter o cargo de gerente para validar as informações" disabled>
                            <input type="button" class="btn btn-validar btn-disabled" value="Validar" style="display: none;" id="valida-maior" title="É necessário ter o cargo de gerente para validar as informações" disabled>
                        </c:otherwise>
                    </c:choose>
                    <a href="../index.jsp"><input type="button" value="Voltar" class="btn" id="voltar"></a>
                    <a href="../forms/ficha14.jsp"><input type="button" value="Adicionar" class="btn"></a>
                </div>
            </div>
            <div class="opcoes-ficha-container">
                <p>Porcentagem de participação no capital total do fundo de investimento: *</p>
                <br>
                <div class="label-container">
                    <label class="label-radio-menor">
                        <input type="radio" name="resposta-participacao" value="menor">
                        Participação <b>menor</b> que 10%
                    </label>
                    <label class="label-radio-maior">
                        <input type="radio" name="resposta-participacao" value="maior">
                        Participação <b>maior</b> ou <b>igual</b> a 10%
                    </label>
                </div>
            </div>

            <!--Tabela menor que 10%-->
            <div id="tabelaMenor" style="display: none;">
                <table class="table-lista-fichas" id="menor">
                    <tr>
                        <th>Status</th>
                        <th>Opções</th>
                        <th>País</th>
                        <th>Moeda</th>
                        <th>Valor de participação na data-base</th>
                        <th>Rendimentos distribuídos ao declarante</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha14MenorController.getAllFichas()}" var="ficha">
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
                                        <a href="../edits/ficha14menor.jsp?idMenor=${ficha.getId()}" class="option-btn" title="Editar">
                                            <img class="option-btn-img" src="../resources/imgs/editar.png" alt="editar"/>
                                        </a>
                                        <button class="option-btn delete" title="Excluir" data-id="${ficha.getId()}" data-ficha="ficha14/menor">
                                            <img class="option-btn-img" src="../resources/imgs/lixo.png" alt="alt"/>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${ficha.getPais().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getValorParticipacao())}</td>
                            <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getRendimentoDistribuido())}</td>
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
            <div id="tabelaMaior" style="display: none;">
                <table class="table-lista-fichas" id="maior">
                    <tr>
                        <th>Status</th>
                        <th>Opções</th>
                        <th>Fundo de investimento</th>
                        <th>Moeda</th>
                        <th>Patrimônio líquido na data-base</th>
                        <th>Percentual de participação no patrimônio</th>
                        <th>Rendimentos (positivos ou negativos) do fundo no período-base</th>
                        <th>Rendimentos distribuídos no período-base</th>
                        <th>Controla outras empresas que estão no final da cadeia de controle?</th>
                        <th>Última atualização</th>
                        <th>Funcionário</th>
                    </tr>
                    <c:forEach items="${ficha14MaiorController.getAllFichas()}" var="ficha">
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
                                        <a href="../edits/ficha14Maior.jsp?idMaior=${ficha.getId()}" class="option-btn" title="Editar">
                                            <img class="option-btn-img" src="../resources/imgs/editar.png" alt="editar"/>
                                        </a>
                                        <button class="option-btn delete" title="Excluir" data-id="${ficha.getId()}" data-ficha="ficha14/maior">
                                            <img class="option-btn-img" src="../resources/imgs/lixo.png" alt="alt"/>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${ficha.getEmpresa().getNome()}</td>
                            <td>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</td>
                            <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getPatrimonioLiquido())}</td>
                            <td>${numeroUtils.doubleToString(ficha.getParticipacaoPatrimonio())}%</td>
                            <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getRendimentosFundo())}</td>
                            <td>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getRendimentosDistribuidos())}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${ficha.isControlaEmpresas()}">
                                        <p>Sim</p>
                                        <a class="option-btn visualizar" title="Visualizar empresas controladas" href="empresas-controladas14.jsp?id=${ficha.getId()}">
                                            <img class="option-btn-img" src="../resources/imgs/lupa.png" alt="alt"/>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Não</p>
                                    </c:otherwise>
                                </c:choose>
                            </td>
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
                            window.localStorage.setItem("opcaoFicha14", "menor");
                            perguntasFormMenor.style.display = 'block';
                            perguntasFormMaior.style.display = 'none';
                            validarMenor.style.display = 'inline-block';
                            validarMaior.style.display = 'none';
                        } else if (input.value === 'maior') {
                            window.localStorage.setItem("opcaoFicha14", "maior");
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
                if (window.localStorage.getItem("opcaoFicha14") === "maior") {
                    perguntasFormMenor.style.display = 'none';
                    perguntasFormMaior.style.display = 'block';
                    validarMenor.style.display = 'none';
                    validarMaior.style.display = 'inline-block';
                    respostaInputs[1].checked = true;
                } else if (window.localStorage.getItem("opcaoFicha14") === "menor") {
                    perguntasFormMenor.style.display = 'block';
                    perguntasFormMaior.style.display = 'none';
                    validarMenor.style.display = 'inline-block';
                    validarMaior.style.display = 'none';
                    respostaInputs[0].checked = true;
                }

            });
        </script>
        <script src="/ProjetoCBE/resources/js/CalcularDiferenca.js"></script>
        <script src="/ProjetoCBE/resources/js/validacao.js"></script>
        <script src="/ProjetoCBE/resources/js/delecao.js"></script>
        <script src="/ProjetoCBE/resources/js/temas.js"></script> 
    </body>
</html>