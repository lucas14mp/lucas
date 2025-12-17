<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 12</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha12.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 12 - Empréstimo intercompanhia</h2>
                <br>

                <p>
                    Devem ser declarados nesta ficha os créditos concedidos pela empresa declarante às empresas do mesmo grupo econômico no exterior.
                </p>

                <p>
                    Empréstimo é um instrumento financeiro originado quando da cessão de recursos pelo credor diretamente ao devedor, mediante instrumento ou contrato, em geral não negociado em mercado secundário.
                </p>

                <p>
                    Caso a empresa devedora dos empréstimos no exterior não seja do mesmo grupo econômico da empresa declarante, esses ativos devem ser declarados na ficha "Empréstimo não-intercompanhia".
                </p>

                <p>
                    Quando o empréstimo ocorre diretamente entre exportador e importador, deve ser declarado em uma das fichas de crédito comercial.
                </p>

                <p>
                    Podem ser agregadas informações de diversos empréstimos, desde que sejam coincidentes a empresa devedora do empréstimo, a moeda de denominação e a categoria do prazo original do empréstimo intercompanhia.
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>


                <form action="<%=request.getContextPath()%>/ficha12" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>
                    <div class="label-container">
                        <label for="empresa">Empresa do grupo econômico devedora do empréstimo: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o nome da empresa devedora do empréstimo no exterior previamente cadastrada.</p>
                    <select name="empresa" id="empresa" required>
                        <option value="" selected>Selecione a empresa</option>
                        <c:forEach items="${empresaController.listarEmpresas()}" var="empresa">
                            <option value="${empresa.getId()}">${empresa.getNome()}</option>
                        </c:forEach>                            
                    </select>

                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda em que está referenciado o empréstimo. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getSigla()} | ${moeda.getNome()}</option>
                        </c:forEach>
                    </select>


                    <div class="label-container">
                        <label>Prazo original do empréstimo: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar uma opção para o prazo original do empréstimo, dividido em duas categorias: 'Até 12 meses' ou 'Mais de 12 meses'. Na hipótese de prazo flexível ou indefinido, utilize sua melhor expectativa.</p>
                    <br>
                    <label>
                        <input type="radio" name="prazo" value="Até 12 meses" required>
                        Até 12 meses
                    </label>
                    <br>
                    <br>
                    <label>
                        <input type="radio" name="prazo" value="Mais de 12 meses" required>
                        Mais de 12 meses
                    </label>
                    <br>
                    <br>
                    <br>

                    <div class="label-container">
                        <label for="saldo">Saldo na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o saldo nominal do empréstimo na data-base. O saldo nominal do empréstimo consiste na soma do saldo a receber de principal, incluindo os juros incorridos e não recebidos (devidos e não recebidos). Corresponde, portanto, ao principal do empréstimo concedido inicialmente, somado a quaisquer valores concedidos posteriormente e aos juros incorridos e não recebidos, subtraídos os recebimentos (amortizações) de principal. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="saldo" required id="saldo" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <div class="label-container">
                        <label for="juros">Juros recebidos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o somatório dos juros recebidos no período-base relativos ao empréstimo declarado. O valor do campo deve ser maior ou igual a zero. O fluxo deve ser auferido somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="juros" required id="juros" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>

                    <input type="submit" value="Salvar" class="btn salvar" id="salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>