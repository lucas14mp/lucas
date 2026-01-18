<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="ficha11MaiorController" class="br.com.bb.cbe.controllers.Ficha11MaiorController"/>
<jsp:useBean id="ficha11MenorController" class="br.com.bb.cbe.controllers.Ficha11MenorController"/>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>
<c:set var="uor_equipe" value="${sessionScope.uorEquipe}" />
<c:set var="chave" value="${sessionScope.chave}" />
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 11</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>

    <body>
        <%@include file="../topo.jsp" %>
        <main class="main">
            <article class="article">
                <a href="../views/ficha11.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2> Ficha 11 - Empresas - Participação no capital</h2>
                <br>
                <p>
                    Deve ser declarado nesta ficha um conjunto de informações sobre empresas no exterior,
                    em que o declarante participa no capital. A participação no capital social da empresa
                    é constituída pela propriedade dos instrumentos patrimoniais, ações ou cotas, com ou
                    sem direito a voto, que conferem ao seu proprietário direito de participação nos resultados da
                    empresa.
                </p>
                <p>
                    <b>ATENÇÃO</b>: Em poder de voto, entende-se aqueles direitos de voto (capital votante) que
                    asseguram, de modo
                    permanente, participação nas deliberações sociais e na eleição dos administradores de uma
                    empresa. Geralmente,
                    a compra de ações ordinárias confere poder de voto. Porém, é possível obter poder de voto em
                    proporção superior
                    à das ações ordinárias, como, por exemplo, por meio de aquisição de golden shares, por meio de
                    estatuto ou de
                    acordo com outros investidores.
                </p>
                <p>
                    No caso de participação minoritária direta (poder de voto inferior a 10%) em ações de empresas
                    cotadas em bolsa
                    de valores, utilize a ficha “Ações negociadas em bolsa”.
                </p>

                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <br>
                <p>Porcentagem de poder de voto na empresa: <span class="asterisco">*</span></p>
                <br>
                <div class="label-container">
                    <label>
                        <input type="radio" name="resposta-participacao" id="danger-outlined" value="menor-que-10">
                        Poder de voto <b>menor</b> que 10%
                    </label>
                    <label>
                        <input type="radio" name="resposta-participacao" id="success-outlined" value="maior-que-10">
                        Poder de voto <b>maior</b> ou <b>igual</b> a 10%
                    </label>
                </div>
                
                <!--SESSÃO COPIA E COLA-->
                
                <div class="container11" id="FormCopiaColaMenor" style="display: none;">
                        <!--<br><br><br><br><br><br><br>-->                       
                       
                        <c:choose>
                            <c:when test="${uor_equipe == '19953' || uor_equipe == '284073' || chave == 'F6003248' || chave == 't1092011'}">
                               <h1>SESSÃO COGER</h1>
                                <br><br><br>
                            </c:when>
                            <c:when test="${uor_equipe == '454300' || uor_equipe == '284073' || chave == 'T1092407'}">
                               <h1>SESSÃO UNI</h1>
                                <br><br><br>
                            </c:when>     
                        </c:choose>
                                <div class="formulario11Menor">
                                    <h3>Como enviar as informações?</h3>
                                    <br>
                                    <p>
                                        As informações devem ser copiadas diretamente do arquivo Excel e coladas na caixa de texto presente ao final da página.
                                    </p>
                                    <br>
                                    <p>
                                        <b>Atenção:</b> O cabeçalho será gerado automaticamente, portanto copie apenas as informações conforme a figura abaixo.
                                    </p>
                                    <img src="../resources/imgs/ficha11menor.png" alt="Tabela base para colar" style="width:750px;height:200px;">
                                    <div style="margin-bottom: 15px;">
                                        <button id="copiarConteudo11" class="btn-copiar11" onclick="copiarTextoMenor()">Copiar conteúdo</button>
                                    </div>
                                    <br>
                                    <br>
                                    <h3>Devem constar as seguintes informações:</h3>
                                    <br>
                                    <div class="label-container11">
                                        <label>País da empresa no exterior:</label>
                                    </div>
                                    <p class="descricao11">Escolher o país da empresa no exterior. Não é permitido que o país selecionado seja 'Brasil'.
                                        <a onClick="mostrarTabela('tabelaMenor1')" class="link-expande11">
                                            Clique aqui para visualizar a tabela.</a></p><!--
-->                                    <div class="label-container11">
                                        <div class="table-relatorio11" id="tabelaMenor1" style="display: none;">
                                            <table class="view11" style="margin-bottom: 45px;">
                                                <tr>
                                                    <td class="cabecalho11" style="width: 15%;">Nome</td>
                                                </tr>
                                                <c:forEach items='${paisController.listarPaises()}' var="pais">
                                                    <tr id="paises">
                                                        <td>${pais.nome}</td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="label-container11">
                                        <label>Moeda do país da empresa no exterior:</label>
                                    </div>
                                    <p class="descricao11">Escrever o código da moeda em que está referenciada a participação na empresa. Será com base nessa mesma
                                        moeda que deverão ser informados os demais valores nesta ficha. <a onClick="mostrarTabela('tabelaMoeda2')" class="link-expande11">
                                            Clique aqui para visualizar a tabela.</a></p>
                                    <div class="label-container11">
                                        <div class="table-relatorio11" id="tabelaMoeda2" style="display: none;">
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
                                    <div class="label-container11">
                                        <label>Método de Valoração:</label>
                                    </div>                                    
                                    <p class="descricao11">Escrever um método de valoração para a participação na empresa na data-base, 
                                        escolhendo entre 'Avaliação por especialista', 'Cotação em bolsa', 'Fluxo de caixa descontado', 
                                        'Negociação recente de parcela do capital' e 'Valor total do patrimônio líquido'. <strong>Caso a empresa 
                                        possua cotação em bolsa de valores no exterior, o método de valoração deverá ser obrigatoriamente 
                                        'Cotação em bolsa'.</strong> <a onClick="mostrarTabela('tabela3Menor')" class="link-expande11">
                                            Clique aqui para visualizar a tabela.</a></p>

                                    <div class="label-container11">
                                        <div class="table-relatorio11" id="tabela3Menor" style="display: none;">
                                            <table class="view11" style="margin-bottom: 45px;">
                                                <tr>
                                                    <td class="cabecalho11" style="width: 15%;">Nome</td>
                                                </tr>
                                                <tr>
                                                    <td>Avaliação por especialista</td>
                                                </tr>
                                                <tr>
                                                    <td>Fluxo de caixa descontado</td>
                                                </tr>
                                                <tr>
                                                    <td>Negociação recente de parcela do capital</td>
                                                </tr>
                                                <tr>
                                                    <td>Valor patrimonial</td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="label-container">
                                        <label for="valorParticipacao">Valor de participação na empresa na data-base:</label>
                                    </div>
                                    <p class="descricao11">Informe o valor de participação na empresa na data-base, conforme o método de valoração escolhido anteriormente. Deve ser informado apenas o valor da participação do declarante no capital social da empresa. Este campo deve ser maior que zero.</p>
                                    <div class="label-container">
                                        <label for="valorParticipacao">Lucro distribuído ao declarante:</label>
                                    </div>
                                    <p class="descricao11">Informe o valor de lucro distribuído ao declarante. Deve ser informado apenas o valor do lucro distribuído ao declarante. Este campo deve ser maior que zero.</p>
                                    <br>                          
                                    <button id="colarNovamente11" class="btn-relatorio-colar11" onclick="colarNovamente()">Colar tabela novamente</button>
                                    <br><br>
                                    <textarea id="areaInput11Menor" class="area-input11" onpaste="clipMenor(event)" placeholder="Por gentileza, cole as informações aqui."></textarea>                                   
                                    <input type="text" id="tipozada" name="tipo-requisicao" value="createbatch" hidden>
                                    <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
                                    <input type="text" id="id-header" name="id-header" value="4" hidden>
                                    <div id="controlaMenor">
                                    </div>
                                    <button id="submitButtonMenor" class="btn-relatorio-enviar11" onclick="sendAllDataMenor()" disabled >Enviar</button>                       
                                </div>
                        </div>
                                    
                                    <div class="area-upload">
                    <h3 style="color: #333; margin-bottom: 15px;">Anexar Planilha (.xlsx)</h3>

                    <form action="${pageContext.request.contextPath}/UploadFicha11MenorController" method="post" enctype="multipart/form-data">

                        <label for="arquivoExcel" style="font-weight: bold; display: block; margin-bottom: 5px;">
                            Selecione o arquivo no seu computador:
                        </label>
                        
                        <div class="input-wrapper">
                            <input type="file" name="arquivoExcel" id="arquivoExcel" class="input-arquivo" accept=".xlsx, .xls" required onchange="verificarArquivo()"/>
                            
                            <i class="bi bi-x-circle-fill btn-remover-arquivo" id="btnRemover" onclick="limparArquivo()" title="Remover arquivo selecionado"></i>
                        </div>

                        <br>

                        <button type="submit" class="btn" style="padding: 10px 30px; font-size: 16px;">
                            <i class="bi bi-cloud-upload"></i> Processar Arquivo
                        </button>
                    </form>
                </div>
                                    
                <!--FIN-->

                    <div class="container11" id="FormCopiaColaMaior" style="display: none;">
<!--                        <br><br><br><br><br><br><br>-->
                        
                       
                        <c:choose>
                            
                            <%--<c:when test="${uor_equipe == '19953' || chave == 'T1092407' || chave == 'F6003248' || chave == 'F2412416' || uor_equipe == '18905'}">--%>
                            <c:when test="${uor_equipe == '19953' || chave == 'F6003248' || chave == 'F2412416' || uor_equipe == '284073' || chave == 'F8595462'}">    
                               <h1>SESSÃO COGER</h1>
                                <br><br><br>
                            </c:when>
                                
                            <c:when test="${uor_equipe == '315118' || uor_equipe == '284073' || chave == 't1092011' || uor_equipe == '9510' || uor_equipe == '283575'}">
                               <h1>SESSÃO UPE</h1>
                                <br><br>
                            </c:when>  
                                
                        </c:choose>
                                <div class="formulario11Maior" id="formulario11Maior">
                                    <h3>Como enviar as informações?</h3>
                                    <br>
                                    <p>
                                        As informações devem ser copiadas diretamente do arquivo Excel e coladas na caixa de texto presente ao final da página.
                                    </p>
                                    <br>
                                    <p>
                                        <b>Atenção:</b> O cabeçalho será gerado automaticamente, portanto copie apenas as informações conforme a figura abaixo.
                                    </p>
                                    <c:choose>
                                        
                                        <c:when test="${uor_equipe == '19953' || uor_equipe == '284073' || chave == 'F6003248' || chave == 'T1092407' || chave == 'F2412416' || chave == 'T1091905' || chave == 'F8595462'}">
                                            <img src="../resources/imgs/ficha11maiorCoger.png" alt="Tabela base para colar" style="width:1000px;height:250 px">
                                            <div style="margin-bottom: 15px;">
                                                <button id="copiarConteudo11" class="btn-copiar11" onclick="copiarTextoMaiorCoger()">Copiar conteúdo</button>
                                            </div>
                                        </c:when>
                                            
                                        <c:when test="${uor_equipe == '315118' || uor_equipe == '284073' || chave == 't1092011' || uor_equipe == '9510' || uor_equipe == '283575'}">
                                            <img src="../resources/imgs/ficha11maiorCoger.png" alt="Tabela base para colar" style="width:750px;height:250px">
                                            <div style="margin-bottom: 15px;">
                                                <button id="copiarConteudo11" class="btn-copiar11" onclick="copiarTextoMaiorUpe()">Copiar conteúdo</button>
                                            </div>
                                        </c:when>
                                            
                                    </c:choose>
                                    <br>
                                    <br>
                                    <h3>Devem constar as seguintes informações:</h3>
                                    <br>
                                    <div class="label-container11">
                                        <label>Empresa:</label>
                                    </div>
                                    <p class="descricao11">Escolher, entre as empresas no exterior previamente cadastradas, aquela na qual o declarante 
                                        possui participação em poder de voto igual ou superior a 10%. Somente podem ser vinculadas ao ativo 'Empresas – 
                                        Participação no capital' aquelas empresas com as quais o declarante possui a relação 'Empresa declarante é 
                                        investidora direta na empresa no exterior'.<a onClick="mostrarTabela('tabela1')" class="link-expande11">
                                            Clique aqui para visualizar a tabela.</a></p>
                                    <div class="label-container11">
                                        <div class="table-relatorio11" id="tabela1" style="display: none;">
                                            <table class="view11" style="margin-bottom: 45px;">
                                                <tr>
                                                    <td class="cabecalho11" style="width: 15%;">Nome</td>
                                                </tr>
                                                <c:forEach items='${empresaController.listarEmpresas()}' var="empresa">
                                                    <tr id="nomes">
                                                        <td>${empresa.nome}</td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </div>
                                    </div>    
                                    <c:choose>
                                        <c:when test="${uor_equipe == '315118' || uor_equipe == '284073' || chave == 't1092011' || uor_equipe == '9510' || uor_equipe == '283575'}">
                                            
                                            <div class="label-container11">
                                                <label>Esta empresa possui cotação em bolsa de valores no exterior?</label>
                                            </div>
                                            <p class="descricao11">Escreva "Sim" ou "Não" na tabela, para informar se a empresa possui cotação em bolsa de valores 
                                                no exterior.<strong> Caso seja escrito a opção 'Sim', o método de valoração deverá ser obrigatoriamente</strong> 'Cotação em 
                                                bolsa'.</p>
                                            <div class="label-container11">
                                                <label>Método de Valoração:</label>
                                            </div>                                    
                                            <p class="descricao11">Escrever um método de valoração para a participação na empresa na data-base, 
                                                escolhendo entre 'Avaliação por especialista', 'Cotação em bolsa', 'Fluxo de caixa descontado', 
                                                'Negociação recente de parcela do capital' e 'Valor total do patrimônio líquido'. <strong>Caso a empresa 
                                                possua cotação em bolsa de valores no exterior, o método de valoração deverá ser obrigatoriamente 
                                                'Cotação em bolsa'.</strong> <a onClick="mostrarTabela('tabela3')" class="link-expande11">
                                                    Clique aqui para visualizar a tabela.</a></p>

                                            <div class="label-container11">
                                                <div class="table-relatorio11" id="tabela3" style="display: none;">
                                                    <table class="view11" style="margin-bottom: 45px;">
                                                        <tr>
                                                            <td class="cabecalho11" style="width: 15%;">Nome</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Avaliação por especialista</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Fluxo de caixa descontado</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Negociação recente de parcela do capital</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Valor patrimonial</td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                            <div class="label-container11">
                                                <label>Valor da empresa na data-base</label>
                                            </div>
                                            <p class="descricao11">Informe o valor integral da empresa na data-base, conforme o método de valoração escolhido. 
                                                Este campo pode assumir valores positivos, nulos ou negativos.</p>
                                            <div class="label-container11">
                                                <label>Resultado líquido de itens não recorrentes:</label>
                                            </div>
                                            <p class="descricao11">Informe, em termos líquidos, os ganhos (positivo) ou perdas (negativo) decorrentes de eventos não usuais
                                                às atividades da empresa no exterior e que tenham transitado pelo resultado do exercício, tal como resultado de operações 
                                                descontinuadas (venda de ativos, incluindo participações em empresas), perdas judiciais, multas, dentre outros.</p>
                                            <div class="label-container11">
                                                <label>Lucro distribuído no período-base:</label>
                                            </div>
                                            <p class="descricao11">Informe o lucro total aprovado para distribuição aos sócios (dividendos) pela empresa no período-base, 
                                                inclusive dividendos provenientes de reserva de lucros (períodos anteriores). Este campo deve ser maior ou igual a zero.</p>  
                                        </c:when>         
                                        <c:when test="${uor_equipe == '19953' || uor_equipe == '284073' || chave == 'F6003248' || chave == 'T1092407' || chave == 'F2412416' || chave == 'T1091905' || chave == 'F8595462'}">                           
                                            <div class="label-container11">
                                                <label>Moeda do país da empresa no exterior:</label>
                                            </div>
                                            <p class="descricao11">Escrever o código da moeda em que está referenciada a participação na empresa. Será com base nessa mesma
                                                moeda que deverão ser informados os demais valores nesta ficha. <a onClick="mostrarTabela('tabela2'), ordenarTabela2()" class="link-expande11">
                                                    Clique aqui para visualizar a tabela.</a></p>

                                            <div class="label-container11">
                                                <div class="table-relatorio11" id="tabela2" style="display: none;">
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
                                            <div class="label-container11">
                                                <label>Patrimônio líquido total na data-base:</label>
                                            </div>
                                            <p class="descricao11">Informe o valor total do patrimônio líquido da empresa na data-base. Este campo pode assumir 
                                                valores positivos, nulos ou negativos.</p>
                                            <div class="label-container11">
                                                <label>Percentual de participação no capital social:</label>
                                            </div>
                                            <p class="descricao11">Informe o percentual de participação no capital social detido pelo declarante. Informe um valor 
                                                maior que zero e menor ou igual a 100. Note que deve ser informado o percentual como múltiplo de 100, por exemplo, 
                                                o valor 15 representa 15%.</p>
                                            <div class="label-container11">
                                                <label>Percentual de poder de voto:</label>
                                            </div>
                                            <p class="descricao11">Informe o percentual de participação no poder de voto na empresa detido pelo declarante. Informe um valor entre 
                                                1 e 100. Note que deve ser informado o percentual como múltiplo de 100, por exemplo, o valor 15 representa 15%.</p>
                                            <div class="label-container11">
                                                <label>Ativo na data-base:</label>
                                            </div>
                                            <p class="descricao11">Informe o ativo total da empresa na data-base. Este campo deve ser maior ou igual a zero.</p>
                                             <div class="label-container11">
                                                <label>Passivo exigível na data-base:</label>
                                            </div>
                                            <p class="descricao11">Informe o passivo exigível da empresa na data-base. Este campo deve ser maior ou igual a zero.</p>
                                            <div class="label-container11">
                                                <label>Valor total do lucro ou prejuízo líquidos da empresa no exterior:</label>
                                            </div>
                                            <p class="descricao11">Informe o total do lucro ou prejuízo líquido auferido pela empresa na data-base. Este campo pode assumir 
                                                valores positivos, nulos ou negativos. ATENÇÃO: Os valores dos campos número 'Valor total do lucro ou prejuízo líquidos da 
                                                empresa no exterior' a 'Lucro distribuído no período-base' são fluxos auferidos somente no período de referência (trimestral
                                                ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base.</p>        
                                            <div class="label-container11">
                                                <label>Resultado Líquido de reavaliações (ex. impairment):</label>
                                            </div>
                                            <p class="descricao11">Informe, em termos líquidos, os ganhos (positivo) ou perdas (negativo), que tenham transitado no 
                                                resultado do exercício: i) não realizados decorrentes de reavaliação de ativos (clientes, estoques, investimentos, 
                                                imobilizado e intangível) e de passivos (constituição/reversão de despesas com provisões), e ii) realizados na negociação 
                                                de ativos (exceto estoque) e passivos.</p>
                                            <div class="label-container11">
                                                <label>Resultado líquido de variação cambial:</label>
                                            </div>
                                            <p class="descricao11">Informe, em termos líquidos, os ganhos (positivo) ou perdas (negativo) decorrentes de variação cambial 
                                                (monetária) de passivos (obrigações) e ativos (incluindo créditos) que tenham transitado no resultado do exercício.</p>
                                        </c:when>
                                            
                                    </c:choose>
                                        <br>                          
                                        <button id="colarNovamente11" class="btn-relatorio-colar11" onclick="colarNovamente()">Colar tabela novamente</button>
                                        <br><br>
                                    <c:choose>
                                        <c:when test="${uor_equipe == '19953' || uor_equipe == '284073' || chave == 'F6003248'  || chave == 'T1091905' || chave == 'F8595462'}">
                                            <textarea id="areaInput11Maior" class="area-input11" onpaste="clipMaior(event, 'COGER')" placeholder="Por gentileza, cole as informações aqui."></textarea>                                         
                                        </c:when>
                                        <c:when test="${uor_equipe == '315118' || uor_equipe == '284073' || chave == 't1092011' || chave == 'T1092407' || chave == 'F2412416' || uor_equipe == '9510' || uor_equipe == '283575'}">
                                           <textarea id="areaInput11Maior" class="area-input11" onpaste="clipMaior(event, 'UPE')" placeholder="Por gentileza, cole as informações aqui."></textarea>
                                        </c:when>     
                                    </c:choose>                                   
                                    <input type="text" id="tipozada" name="tipo-requisicao" value="createbatch" hidden>
                                    <input type="hidden" id="contextPath" value="<%=request.getContextPath()%>">
                                    <input type="text" id="id-header" name="id-header" value="4" hidden>
                                    <div id="controlaMaior">
                                            <!--TESTE-->
                                    </div>     
                                        <c:choose>
                                            <c:when test="${uor_equipe == '19953' || uor_equipe == '284073' || chave == 'F6003248' || chave == 'F2412416'  || chave == 'F8595462' || chave == 'T1092407'}">
                                               <button id="submitButtonMaior" class="btn-relatorio-enviar11" onclick="sendAllDataMaior('COGER')" disabled >Enviar</button>
                                            </c:when>
                                            <c:when test="${uor_equipe == '315118' || uor_equipe == '284073' || chave == 't1092011' || chave == 'T1091905' || uor_equipe == '9510' || uor_equipe == '283575'}">
                                               <button id="submitButtonMaior" class="btn-relatorio-enviar11" onclick="sendAllDataMaior('UPE')" disabled >Enviar</button>
                                            </c:when>     
                                       </c:choose>
                                          
                                </div>
                                <div class="teste" id="teste"></div>
                                    
                <script src="/ProjetoCBE/resources/js/ficha11.js"></script>
                <script src="/ProjetoCBE/resources/js/temas.js"></script>
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
                        ordenarTabela1();
                        ordenarTabela2();
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
                <script>
            // Função chamada quando o usuário seleciona um arquivo
            function verificarArquivo() {
                var input = document.getElementById('arquivoExcel');
                var btnRemover = document.getElementById('btnRemover');
                
                // Se tem arquivo selecionado, mostra o X. Se não, esconde.
                if (input.value) {
                    btnRemover.style.display = 'block';
                } else {
                    btnRemover.style.display = 'none';
                }
            }

            // Função chamada quando clica no X
            function limparArquivo() {
                var input = document.getElementById('arquivoExcel');
                var btnRemover = document.getElementById('btnRemover');
                
                input.value = ''; // Limpa o input
                btnRemover.style.display = 'none'; // Esconde o botão novamente
            }

            // Validação extra no envio
            document.querySelector('form').addEventListener('submit', function(e) {
                var input = document.getElementById('arquivoExcel');
                if (!input.value) {
                    alert('Nenhum arquivo selecionado.');
                    e.preventDefault();
                    return;
                }
                if (!input.value.endsWith('.xlsx') && !input.value.endsWith('.xls')) {
                    alert('Por favor, selecione um arquivo Excel válido (.xlsx ou .xls).');
                    e.preventDefault();
                }
            });
        </script>
            </article>
        </main>                       
    </body>
</html>