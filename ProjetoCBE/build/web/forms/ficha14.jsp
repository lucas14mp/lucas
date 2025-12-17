<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="ficha14MaiorController" class="br.com.bb.cbe.controllers.Ficha14MaiorController"/>
<jsp:useBean id="ficha14MenorController" class="br.com.bb.cbe.controllers.Ficha14MenorController"/>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="dataUtils" class="br.com.bb.cbe.Utils.DataUtils"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 14</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha14.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 14 - Fundos de Investimento</h2>
                <br>
                <p>
                    Devem ser declaradas nesta ficha participações no capital de fundos de investimentos. A participação no fundo é determinada a partir da participação no capital total do fundo.
                </p>
                <p>
                    Um fundo de investimento é um condomínio de investidores, que se juntam visando determinado objetivo ou retorno esperado, 
                    dividindo as receitas geradas e as despesas necessárias para o empreendimento.
                </p>
                <p>
                    Todos os fundos de investimento, independentemente do tipo, devem ser declarados na ficha “Fundos de Investimento”. As características da carteira de ativos detidas pelo fundo 
                    – renda fixa, renda variável, fundos mútuos, fundos de participação, fundos imobiliários, dentre outros – não alteram a ficha em que o ativo é declarado.
                </p>

                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <br>
                <div class="label-container">
                    <p>Porcentagem de participação no capital total do fundo de investimento: <span class="asterisco">*</span></p>
                </div>
                <br>
                <div class="label-container">
                    <label>
                        <input type="radio" name="resposta-participacao" id="success-outlined" value="menor-que-10">
                        Participação <b>menor</b> que 10%
                    </label>
                    <label>
                        <input type="radio" name="resposta-participacao" id="danger-outlined" value="maior-que-10">
                        Participação <b>maior</b> ou <b>igual</b> a 10%
                    </label>
                </div>

                <!--Tabela menor que 10%-->
                <form action="<%=request.getContextPath()%>/ficha14/menor" method="post" class="form" id="formMenor" style="display: none;">
                    <input type="hidden" name="tipo-requisicao" value="post">
                    <p>
                        <b>Participação menor que 10%:</b>
                    </p>
                    <br>
                    <p>
                        Podem ser agregadas informações de diversos fundos de investimentos caso a participação seja menor que 10%, desde que sejam coincidentes o país do fundo e a moeda de denominação.
                        Não é possível agregar fundos com participação maior ou igual a 10%, pois neste caso, é obrigatório a identificação individual de cada fundo.
                    </p>
                    <br>

                    <div class="label-container">
                        <label for="pais">País: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Escolher o país onde está constituído o fundo no exterior. Não é permitido que o país selecionado seja 'Brasil'.</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="moedaMenor">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda em que está denominado o patrimônio do fundo. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moedaMenor" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getNome()} | ${moeda.getSigla()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="valor">Valor de participação na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor de participação no fundo na data-base. Deve ser informado apenas o valor da participação do declarante no capital social do fundo. Este campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas-menor">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="valor" name="valor" required placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <p>
                        <b>ATENÇÃO:</b> O valor do campo "Rendimentos distribuídos ao declarante" é um fluxo auferido somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base
                    </p>
                    <div class="label-container">
                        <label for="rendimentos">Rendimentos distribuídos ao declarante: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor do rendimento que foi efetivamente distribuído ao declarante. Este campo deve ser maior ou igual a zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas-menor">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="rendimentos" name="rendimentos" required placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <input type="submit" value="Salvar" class="btn salvar">
                </form>

                <!--Tabela maior que 10%-->
                <form action="<%=request.getContextPath()%>/ficha14/maior" method="post" class="form" id="formMaior" style="display: none;">
                    <input type="hidden" name="tipo-requisicao" value="post">
                    <p>
                        <b>Participação maior ou igual a 10%:</b>
                    </p>
                    <br>
                    <p>
                        Não é possível agregar informações de fundos cuja participação do declarante seja igual ou superior a 10%, pois nestes casos é obrigatória a identificação individual de cada fundo.
                    </p>
                    <br>

                    <div class="label-container">
                        <label for="empresa">Informar fundo: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar um fundo que possui participação igual ou superior a 10%.</p>
                    <h5>Caso não encontre a empresa que deseja, <a href="../views/empresas.jsp" style="color: blue;">clique aqui</a> para adicioná-la.</h5>
                    <select name="empresa" id="empresa" required>
                        <option value="" selected>Selecione a empresa </option>
                        <c:forEach items="${empresaController.listarEmpresas()}" var="empresa">
                            <option value="${empresa.getId()}">${empresa.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="moedaMaior">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda em que está denominado o patrimônio do fundo. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moedaMaior" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getNome()} | ${moeda.getSigla()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="patrimonio">Patrimônio líquido na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor total do patrimônio líquido do fundo na data-base. Este campo pode assumir valores positivos, nulos ou negativos.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="patrimonio" name="patrimonio" required placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <div class="label-container">
                        <label for="percentual">Percentual de participação no patrimônio: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o percentual de participação detido pelo declarante no patrimônio do fundo. Informe um valor maior ou igual a 10 e menor ou igual a 100. Deve ser informado o percentual como múltiplo de 100, por exemplo, o valor 15 representa 15%.</p>
                    <input type='number' id='percentual' name='percentual' required min='10' max='100' step='0.01' placeholder='Digite um valor entre 10 e 100'>

                    <div class="label-container">
                        <label for="rendimento-distribuido">Rendimentos distribuídos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor total distribuído em rendimentos pelo fundo no período-base. Este campo deve ser maior ou igual a zero.</p>
                    <input type="text" id="rendimento-distribuido" name="rendimento-distribuido" required placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$">

                    <div class="label-container">
                        <label for="rendimento-fundo">Rendimentos (positivos ou negativos) do fundo no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor total auferido como rendimentos pelo fundo no período-base. Este campo pode assumir valores positivos, nulos ou negativos. ATENÇÃO: Os valores dos campos 'Rendimentos (positivos ou negativos) do fundo no período-base' e 'Rendimentos distribuídos no período-base' são fluxos auferidos somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="rendimento-fundo" name="rendimento-fundo" required placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>

                    <div class="label-container">
                        <label>O fundo no exterior controla outras empresas direta ou indiretamente, também no exterior, que estão ao final da cadeia de controle? <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe 'Sim' ou 'Não' para a pergunta. Caso seja respondido afirmativamente, será necessário o preenchimento de informações adicionais.</p>
                    <br>
                    <label>
                        <input type="radio" name="controla" value="true" required>
                        Sim
                    </label>
                    <br>
                    <br>
                    <label>
                        <input type="radio" name="controla" value="false" required>
                        Não
                    </label>
                    <br>        
                    <br>        
                    <input type="submit" value="Continuar" class="btn salvar" id="btnRedireciona" style="display: none;">
                    <input type="submit" value="Salvar" class="btn salvar" id="btnSalvar">
                </form>
                <script src="/ProjetoCBE/resources/js/ficha14.js"></script>
                <script src="/ProjetoCBE/resources/js/temas.js"></script>
                <script src="/ProjetoCBE/resources/js/moedas.js"></script>
            </article>
        </main>
    </body>
</html>