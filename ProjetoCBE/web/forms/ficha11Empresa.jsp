<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="ficha11EmpresaController" class="br.com.bb.cbe.controllers.Ficha11EmpresaController"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha11MaiorController" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha11Maior" %>
<%
    Ficha11MaiorController fichaController = new Ficha11MaiorController();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha11Maior ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
    pageContext.setAttribute("id", idFicha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 11 - Empresa</title>   
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha11.jsp"><input type="button" value="Voltar" class="btn voltar"></a>
                <h2> Ficha 11 - Empresas - Participação no capital</h2>
                <br>
                <p>
                    <b>Controladas pela empresa no exterior:</b>
                </p>
                <p>
                    Os declarantes deverão cadastrar as empresas que cumpram <b>todos</b> os requisitos a seguir:
                </p>
                <p>
                    a) Empresas ou fundos que exerçam atividades econômicas operacionais de fato. Isto significa que esta empresa produz bens ou presta serviços (inclusive financeiros). Empresas que exercem funções de jure (por exemplo, as constituídas sob a forma de holdings), devem ser desconsideradas;
                </p>
                <p>
                    A empresa pertence à cadeia de controle no qual o declarante detém participação direta. O controle é transmitido ao longo dos elos da cadeia desde que haja mais de 50% do poder de voto.
                </p>
                <p>
                    c) A empresa ou fundo é a primeira de seu ramo organizacional a exercer atividade econômica de fato (nas edições anteriores do CBE, solicitava-se a declaração apenas das empresas que estavam ao fim da cadeia de controle). 
                </p>
                <br>
                <p><b>Por gentileza, verifique as informações preenchidas anteriormente:</b></p>
                <br>
                <div class='dados-empresa-preenchida'>
                    <p><b>Empresa: </b>${ficha.getEmpresa().getNome()}</p>
                    <p><b>Esta empresa possui cotação em bolsa de valores no exterior?</b> ${ficha.getPossuiCotacaoEmBolsa() != null && ficha.getPossuiCotacaoEmBolsa() ? "Sim" : "Não"}</p>
                    <p><b>Moeda do país da empresa no exterior:</b> ${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</p>
                    <p><b>Método de valoração:</b> ${ficha.getMetodoValoracao() != null ? ficha.getMetodoValoracao() : "Não informado"}</p>
                    <p><b>Valor da empresa na data-base:</b> ${ficha.getValorEmpresa() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getValorEmpresa() != null ? numeroUtils.doubleToString(ficha.getValorEmpresa()) : "-"}</p>
                    <p><b>Patrimônio líquido total na data-base:</b> ${ficha.getPatrimonioTotal() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getPatrimonioTotal() != null ? numeroUtils.doubleToString(ficha.getPatrimonioTotal()) : "-"}</p>
                    <p><b>Percentual de participação no capital social:</b> ${ficha.getPorcentoParticipacaoCapital() != null ? numeroUtils.doubleToString(ficha.getPorcentoParticipacaoCapital()) : "-"}${ficha.getPorcentoParticipacaoCapital() != null ? "%" : ""}</p>
                    <p><b>Percentual de poder de voto:</b> ${ficha.getPorcentoPoderVoto() != null ? numeroUtils.doubleToString(ficha.getPorcentoPoderVoto()) : "-"}${ficha.getPorcentoPoderVoto() != null ? "%" : ""}</p>
                    <p><b>Ativo na data-base:</b> ${ficha.getAtivoDatabase() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getAtivoDatabase() != null ? numeroUtils.doubleToString(ficha.getAtivoDatabase()) : "-"}</p>
                    <p><b>Passivo exigível na data-base:</b> ${ficha.getPassivoExigivel() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getPassivoExigivel() != null ? numeroUtils.doubleToString(ficha.getPassivoExigivel()) : "-"}</p>
                    <p><b>Valor total do lucro ou prejuízo líquidos da empresa no exterior:</b> ${ficha.getValorTotalLucroPrejuizo() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getValorTotalLucroPrejuizo() != null ? numeroUtils.doubleToString(ficha.getValorTotalLucroPrejuizo()) : "-"}</p>
                    <p><b>Resultado líquido de itens não recorrentes:</b> ${ficha.getResultadoLiquidoItensNaoRecorrentes() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getResultadoLiquidoItensNaoRecorrentes() != null ? numeroUtils.doubleToString(ficha.getResultadoLiquidoItensNaoRecorrentes()) : "-"}</p>
                    <p><b>Resultado líquido de reavaliações (ex. impairment):</b> ${ficha.getResultadoLiquidoReavaliacoes() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getResultadoLiquidoReavaliacoes() != null ? numeroUtils.doubleToString(ficha.getResultadoLiquidoReavaliacoes()) : "-"}</p>
                    <p><b>Resultado líquido de variação cambial:</b> ${ficha.getResultadoLiquidoVariacaoCambial() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getResultadoLiquidoVariacaoCambial() != null ? numeroUtils.doubleToString(ficha.getResultadoLiquidoVariacaoCambial()) : "-"}</p>
                    <p><b>Lucro distribuído no período-base:</b> ${ficha.getLucroDistribuido() != null ? ficha.getMoeda().getSimbolo() : ""} ${ficha.getLucroDistribuido() != null ? numeroUtils.doubleToString(ficha.getLucroDistribuido()) : "-"}</p>
                    <p><b>A empresa no exterior controla outras empresas?:</b> Sim</p>
                </div>
                <br>
                <p>Preencha as informações abaixo para adicionar a empresa controlada:</p>
                <br>
<!--                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>-->
<!--                <form action='<%=request.getContextPath()%>/ficha11Empresa' method='post' id='formulario' class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>
                    <input type="number" name="id" hidden value="${ficha.getId()}">

                    <div class="label-container">
                        <label for="nome">Nome da empresa controlada: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o nome da empresa controlada.</p>
                    <input type='text' id='nome' name='nome' required placeholder='Digite o nome da empresa'>

                    <div class="label-container">
                        <label for="pais">País da empresa controlada: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o país da empresa controlada.</p>
                    <select id='pais' name='pais' required>
                        <option value='' selected>Selecione o país</option>
                        <c:forEach items='${paisController.listarPaises()}' var='pais'>
                            <option value='${pais.getId()}'>${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="atividade">Atividade econômica principal: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe a atividade econômica principal da empresa controlada.</p>
                    <select id='atividade' name='atividade'>
                        <option value='' selected>Selecione a atividade econômica</option>
                        <option value='Negociação de títulos'>Negociação de títulos</option>
                        <option value='Viagens e Turismo'>Viagens e Turismo</option>
                        <option value='Recuperação de créditos'>Recuperação de créditos</option>
                        <option value='Financeira'>Financeira</option>
                        <option value='Gestão de Fundos'>Gestão de Fundos</option>
                    </select>

                    <div class="label-container">
                        <label for="participacao">Percentual de participação no capital social: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o percentual de participação no capital social que a empresa no exterior possui da controlada. Informe um valor maior que zero e menor ou igual a 100. Note que deve ser informado o percentual como múltiplo de 100, por exemplo, o valor 15 representa 15%. O percentual que a empresa ou fundo no exterior (no qual o declarante possui participação direta) detém da controlada. Ex.: a participação em “B2” é de 55% multiplicada por 65%: 36%</p>
                    <input type='number' id='participacao' name='participacao' min='1' max='100' step='0.01' placeholder='Digite um valor entre 1 e 100'>

                    <div class="label-container">
                        <label for="nome">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda original em que está referenciada a participação na empresa. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name='moeda' id='moeda' required>
                        <option value='' selected>Selecione a moeda</option>
                        <c:forEach items='${moedaController.listarMoedas()}' var='moeda'>
                            <option value='${moeda.getId()}'>${moeda.getSigla()} | ${moeda.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="patrimonio">Patrimônio líquido total na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor total do patrimônio líquido da empresa controlada na data-base. Este campo pode assumir valores positivos, nulos ou negativos.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type='text' id='patrimonio' name='patrimonio' placeholder='Digite um valor' pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <div class="label-container">
                        <label for="valor">Valor de mercado na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Estimar o valor de mercado da empresa a partir de métodos de valoração preferencialmente distintos do patrimônio líquido.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type='text' id='valor' name='valor' placeholder='Digite um valor' pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <div class="label-container">
                        <label>A empresa está ao final da cadeia de controle? <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Responder afirmativamente caso a empresa esteja no último nível de seu ramo da cadeia de controle (ex: empresa 'B2').</p>
                    <label>
                        <input type="radio" name="final" value="true">
                        Sim
                    </label>
                    <br>
                    <br>
                    <label>
                        <input type="radio" name="final" value="false">
                        Não
                    </label>
                    <br>
                    <br>

                    <div class="btn-group">
                        <button type="submit" class="btn salvar" id="salvar">Salvar</button>
                    </div>-->
                <!--</form>-->
                        <div class="formulario11">
                            <h3>Como enviar as informações?</h3>
                            <br>
                            <p>
                                As informações devem ser copiadas diretamente do arquivo Excel e coladas na caixa de texto presente ao final da página.
                            </p>
                            <br>
                            <p>
                                <b>Atenção:</b> O cabeçalho será gerado automaticamente, portanto copie apenas as informações conforme a figura abaixo.
                            </p>
                            <img src="../resources/imgs/ficha11controle.png" alt="Tabela base para colar" style="width:1000px;height:225px">
                            <div style="margin-bottom: 15px;">
                                <button id="copiarConteudo11" class="btn-copiar11" onclick="copiarTexto()">Copiar conteúdo</button>
                            </div>
                            <br>
                            <br>
                            <h3>Devem constar as seguintes informações:</h3>
                            <br>
                            <div class="label-container11">
                                <label for="nome">Nome da empresa controlada: <span class="asterisco">*</span></label>
                            </div>
                            <p class="descricao11">Informe o nome da empresa controlada.</p>
                             <div class="label-container11">
                                <label for="pais">País da empresa controlada: <span class="asterisco">*</span></label>
                            </div>
                            <p class="descricao11">Informe o país da empresa controlada.<a onClick="mostrarTabela('tabela2Pais'), ordenarTabela2()" class="link-expande11">
                                    Clique aqui para visualizar a tabela.</a></p>
                            <div class="label-container11">
                                <div class="table-relatorio11" id="tabela2Pais" style="display: none;">
                                    <table class="view11" style="margin-bottom: 45px;">
                                        <tr>
                                            <td class="cabecalho11" style="width: 15%; display: none;">Código</td>
                                            <td class="cabecalho11">Nome</td>
                                        </tr>
                                        <tbody id="tbody2">
                                            <c:forEach  items='${paisController.listarPaises()}' var='pais'>
                                                <tr id="ids">
                                                    <td style="display: none;">${pais.getId()}</td>
                                                    <td>${pais.getNome()}</td>                                                                    
                                                </tr>
                                            </c:forEach>    
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                             <div class="label-container">
                                <label for="atividade">Atividade econômica principal: <span class="asterisco">*</span></label>
                            </div>
                            <p class="descricao11">Informe a atividade econômica principal da empresa controlada.<a onClick="mostrarTabela('tabela3')" class="link-expande11">
                                            Clique aqui para visualizar a tabela.</a></p>

                            <div class="label-container11">
                                <div class="table-relatorio11" id="tabela3" style="display: none;">
                                    <table class="view11" style="margin-bottom: 45px;">
                                        <tr>
                                            <td class="cabecalho11" style="width: 15%;">Nome</td>
                                        </tr>
                                        <tr>
                                            <td>Negociação de títulos</td>
                                        </tr>
                                        <tr>
                                            <td>Viagens e Turismo</td>
                                        </tr>
                                        <tr>
                                            <td>Financeira</td>
                                        </tr>
                                        <tr>
                                            <td>Gestão de Fundos</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                            <div class="label-container">
                                <label for="participacao">Percentual de participação no capital social:</label>
                            </div>
                            <p class="descricao11">Informe o percentual de participação no capital social que a empresa no exterior possui da controlada. Informe um valor maior que zero e menor ou igual a 100. Note que deve ser informado o percentual como múltiplo de 100, por exemplo, o valor 15 representa 15%. O percentual que a empresa ou fundo no exterior (no qual o declarante possui participação direta) detém da controlada. Ex.: a participação em “B2” é de 55% multiplicada por 65%: 36%</p>
                            <div class="label-container11">
                                <label>Moeda:</label>
                            </div>
                            <p class="descricao11">Escrever o nome da moeda em que está referenciada a participação na empresa. Será com base nessa mesma
                                moeda que deverão ser informados os demais valores nesta ficha. <a onClick="mostrarTabela('tabela2Moeda'), ordenarTabela2()" class="link-expande11">
                                    Clique aqui para visualizar a tabela.</a></p>

                            <div class="label-container11">
                                <div class="table-relatorio11" id="tabela2Moeda" style="display: none;">
                                    <table class="view11" style="margin-bottom: 45px;">
                                        <tr>
                                            <td class="cabecalho11" style="width: 15%; display: none;">Código</td>
                                            <td class="cabecalho11">Nome</td>
                                        </tr>
                                        <tbody id="tbody2">
                                            <c:forEach items='${moedaController.listarMoedas()}' var='moeda'>
                                                <tr id="ids">
                                                    <td style="display: none;">${moeda.getId()}</td>
                                                    <td>${moeda.getNome()}</td>                                                                    
                                                </tr>
                                            </c:forEach>    
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="label-container">
                                <label for="patrimonio">Patrimônio líquido total na data-base:</label>
                            </div>
                            <p class="descricao11">Informe o valor total do patrimônio líquido da empresa controlada na data-base. Este campo pode assumir valores positivos, nulos ou negativos.</p>
                            <div class="label-container">
                                <label for="valor">Valor de mercado na data-base:</label>
                            </div>
                            <p class="descricao">Estimar o valor de mercado da empresa a partir de métodos de valoração preferencialmente distintos do patrimônio líquido.</p>                               
                            <br>                          
                            <button id="colarNovamente11" class="btn-relatorio-colar11" onclick="colarNovamente()">Colar tabela novamente</button>
                            <br><br>
                            <textarea id="areaInput11" class="area-input11" onpaste="clip(event)" placeholder="Por gentileza, cole as informações aqui."></textarea>
                            <input type="text" id="idControlada" name="idControlada" value="<%= pageContext.getAttribute("id") %>" hidden>                 
                            <input type="text" id="tipozada" name="tipo-requisicao" value="createBatch" hidden>
                            <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
                            <input type="text" id="id-header" name="id-header" value="4" hidden>
                            <div id="controla">
<!--                                <div class="label-container">
                                    <label>A empresa está ao final da cadeia de controle? <span class="asterisco">*</span></label>
                                </div>
                                <p class="descricao">Responder afirmativamente caso a empresa esteja no último nível de seu ramo da cadeia de controle (ex: empresa 'B2').</p>
                                <label>
                                    <input type="radio" name="final" value="true" required onclick="mudarCadeia('SIM')">
                                    Sim
                                </label>
                                <br>
                                <br>
                                <label>
                                    <input type="radio" name="final" value="false" required onclick="mudarCadeia('NAO')">
                                    Não
                                </label>
                                <br>
                                <br>
                            </div>-->
                            <button id="submitButton" class="btn-relatorio-enviar11" onclick="sendAllData()" disabled >Enviar</button>
                        </div>
                        </div>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/ficha11Empresa.js"></script>               
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <!--<script src="/ProjetoCBE/resources/js/moedas.js"></script>-->
        <script>
            function ordenarTabela1() { 
                var table = document.getElementById("tabela1");
                var tbody = table.querySelector("tbody"); 
                var rows = Array.prototype.slice.call(tbody.querySelectorAll('tr[id="nomes"]')); // Captura todas as linhas no tbody 

                rows.sort(function(a, b) { 
                    var nomeA = a.cells[0].textContent.trim();
                    var nomeB = b.cells[0].textContent.trim(); 
                    return nomeA.localeCompare(nomeB); 
                }); // Limpa o tbody e adiciona as linhas ordenadas de volta 

                rows.forEach(function(row) { 
                    tbody.appendChild(row); 
                }); 
            }

            function ordenarTabela2() {
                
                // Obtém o nó pai
                const pai = document.querySelector(".formulario11");
                // Obtém o nó de referência
                const referencia = document.getElementById("submitButton");

                // Verifica se o nó de referência é um filho do nó pai
//                        if (pai.contains(referencia)) {
//                            console.log("O nó de referência é um filho do nó pai.");
//                        } else {
//                            console.log("O nó de referência não é um filho do nó pai.");
//                        }

                var tbody = document.getElementById("tbody2");
                var rows = Array.prototype.slice.call(tbody.querySelectorAll('tr[id=ids]')); // Captura todas as linhas no tbody

                rows.sort(function(a, b) {
                    var idA = parseInt(a.cells[0].textContent.trim(), 10); // Captura e converte o ID para um número inteiro
                    var idB = parseInt(b.cells[0].textContent.trim(), 10); // Captura e converte o ID para um número inteiro

                    return idA - idB; // Ordena os IDs do menor para o maior
                });

                // Limpa o tbody e adiciona as linhas ordenadas de volta
                rows.forEach(function(row) {
                    tbody.appendChild(row);
                });
            }

            document.addEventListener('DOMContentLoaded', function() {
//                ordenarTabela1();
//                ordenarTabela2();
            });

             function mudarLink() {
                const radios = document.getElementsByName('controla');
                let controla = false;
                for (const radio of radios) {
                    if (radio.checked) {
                        controla = radio.value === 'true';
                        break;
                    }
                }

                if (controla === false) {
                    document.getElementById('submitButton').disabled = false;
                    document.getElementById('submitButton').style.display = 'block';
                    document.getElementById('submitButtonRedireciona').style.display = 'none';
                } else {
                    document.getElementById('submitButton').style.display = 'none';
                    document.getElementById('submitButtonRedireciona').style.display = 'block';
                }
            }
    </script>                
    </body>
</html>