<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="ficha08Controller" class="br.com.bb.cbe.controllers.Ficha08Controller"/>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Ficha 8</title>
    </head>
    <body>     
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha08.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 8 - Depósitos à vista e a prazo</h2>
                <br/>
                <p>
                    Devem ser declarados nesta ficha depósitos no exterior em instituições
                    depositárias não residentes no Brasil. Conta corrente, poupança e
                    outros instrumentos similares devem ser declarados nessa ficha.
                </p>
                <p>
                    Depósitos no exterior em instituições depositárias não residentes no
                    Brasil, compreendem todos os tipos de depósitos prontamente
                    transferíveis, livremente movimentáveis, à vista ou a prazo, com ou
                    sem remuneração, expressos pelo seu valor nominal na moeda original em
                    que estão denominados.
                </p>
                <p>
                    <b>Atenção:</b> caso dois ou mais titulares detenham de forma conjunta
                    o mesmo depósito à vista ou a prazo (contas-conjuntas), cada parte
                    residente ou domiciliada no País - conforme a legislação tributária -
                    deverá considerar o valor integral deste ativo para análise do
                    enquadramento dos critérios de obrigatoriedade da declaração, porém,
                    realizar a declaração apenas da sua respectiva parcela, mesmo que o
                    total individual declarado individualmente seja inferior ao piso de
                    obrigatoriedade.
                </p>
                <p>
                    Podem ser agregadas informações de diversos depósitos, desde que sejam coincidentes o
                    país da instituição depositária e a moeda de denominação.
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha08" class="form" method="post">
                    <input type='hidden' name='tipo-requisicao' value='post'>

                    <div class="label-container">
                        <label for="pais">País: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o país da instituição depositária. Não é permitido que o país selecionado seja 'Brasil'.</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaisesEstrangeiros()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>
                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda em que está denominado o depósito. Os demais valores desta ficha deverão ser informados nessa mesma moeda.</p>
                    <select name="moeda" id="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getNome()} | ${moeda.getSigla()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="saldo">Saldo na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o saldo na data-base da declaração, na moeda do depósito, selecionada no campo “Moeda”.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="saldo" required id="valor" placeholder="Digite um valor maior ou igual 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>

                    <div class="label-container">
                        <label for="rendimentos">Rendimentos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Para depósitos com rendimentos, informar o somatório de todos os rendimentos líquidos (descontados eventuais impostos pagos) recebidos durante o período-base da declaração. <br>Não se confundem com saques ou aplicações na conta de depósito. <br>Em caso de depósitos sem remuneração, deve ser informado o valor zero. <br>O valor do campo deve ser maior ou igual a zero. <br>O fluxo deve ser auferido somente no período de referência (trimestral ou anual, conforme a declaração).</p>

                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="rendimentos" required id="rendimentos" placeholder="Digite um valor maior ou igual 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <input type="submit" value="Salvar" class="btn salvar" id="salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>
