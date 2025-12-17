<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 5</title>
    </head>

    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="http://localhost:8080/ProjetoCBE/index.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 5 - Crédito comercial não-intercompanhia</h2>
                <p>
                    Devem ser declarados nesta ficha os ativos de créditos comerciais do (i) declarante ou contraparte pessoa física, em todos os casos e (ii) declarante pessoa jurídica, junto a empresas no exterior que não sejam do mesmo grupo econômico. Caso a empresa devedora de créditos comerciais no exterior seja do mesmo grupo econômico da empresa declarante, esses ativos devem ser declarados na ficha “Crédito comercial intercompanhia”
                </p>
                <p>
                    Créditos comerciais compreendem financiamentos concedidos diretamente entre exportador e importador para aquisição de bens ou serviços em transações de comércio exterior.
                </p>
                <p>
                    Ainda que o financiamento esteja associado ao comércio de bens e serviços, se houver intermediação de instituição financeira, não se trata de crédito comercial. Nesta hipótese, avaliar a inclusão do ativo na modalidade de empréstimo, quando a contraparte devedora (instituição financeira que intermediou a transação) seja não residente. Os ativos, na modalidade crédito comercial, podem constituir-se de duas formas:
                    • Importador residente no Brasil efetua o pagamento ao exportador não residente, que assume
                    o compromisso de, no futuro, entregar o bem ou serviço (adiantamento de compras). Implica
                    saída de recursos financeiros do País e é um ativo externo recebível em bens ou serviços;
                    e
                    • Exportador residente no Brasil envia o bem ou presta o serviço ao importador não residente,
                    que assume o compromisso de, no futuro, efetuar o pagamento devido (exportações a
                    receber). Não implica saída de recursos financeiros do País e é um ativo externo exigível
                    em moeda.

                </p>
                <p>
                    Devem ser declarados ativos de crédito comercial quando o descasamento entre recursos financeiros e entrega do bem ou serviço for igual ou superior a 30 dias. Operações de prazo entre 0 e 29 dias são consideradas à vista, e estão dispensadas de declaração.
                </p>
                <p>
                    Podem ser agregadas informações de diversos créditos comerciais, desde que sejam coincidentes o país do devedor do crédito comercial, a moeda de denominação e a categoria do prazo original do crédito comercial.
                </p>
                <br>
                <p><b>(*) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha05"" method="post" class="form">

                    <div class="label-container">
                        <picture class="informacao-container" title="Escolher o país do devedor do crédito comercial no exterior. Não é permitido que o país
                                 selecionado seja 'Brasil'.">
                            <img src="../resources/imgs/informacao.png" alt="Ícone informação"/>
                        </picture>
                        <label for="pais">País: *</label>
                    </div>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <picture class="informacao-container" title="Selecionar a moeda original em que está denominada a operação do crédito comercial.">
                            <img src="../resources/imgs/informacao.png" alt="Ícone informação"/>
                        </picture>
                        <label for="moeda">Moeda: *</label>
                    </div>
                    <select name="moeda" id="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getSigla()} | ${moeda.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <picture class="informacao-container" title="Informar o valor do saldo nominal do crédito comercial na data-base da declaração, na moeda original da operação, selecionada no campo anterior. O saldo nominal consiste no valor do crédito comercial concedido inicialmente, somado a quaisquer valores concedidos posteriormente e aos juros incorridos e não recebidos (quando houver), subtraídos dos recebimentos (amortizações) de principal realizados. As amortizações de créditos comerciais podem ser realizadas em moeda, em bens ou serviços, conforme negociado entre o devedor e o credor. O valor do campo deve ser maior que zero.">
                            <img src="../resources/imgs/informacao.png" alt="Ícone informação"/>
                        </picture>
                        <label for="saldoDataBase">Saldo na data-base: *</label>
                    </div>
                    <input type="number" name="saldoDataBase" required id="saldoDataBase" placeholder="Insira o saldo na data-base">

                    <div class="label-container">
                        <picture class="informacao-container" title=" O prazo original em meses refere-se ao prazo contratual previsto para liquidação da operação. Não se refere ao prazo residual entre a data-base da declaração e a data prevista para liquidação. Para avaliação do prazo devem ser usados
                                 parâmetros contratuais, independentemente da data do recebimento efetivo da mercadoria, que pode ser diferente do contratual. Para determinar a data de recebimento da mercadoria, o declarante deve considerar, para cada contrato, o momento em que ocorre a mudança de propriedade. Selecionar uma opção para o prazo total da operação: “Até 12 meses” ou “Mais de 12 meses”. Na hipótese de prazo flexível ou indefinido, utilize a melhor expectativa.">
                            <img src="../resources/imgs/informacao.png" alt="Ícone informação"/>
                        </picture>
                        <label for="prazoOriginal">Prazo original do crédito comercial:</label>
                    </div>
                    <select name="prazoOriginal" id="prazoOriginal" required>
                        <option value="" selected>Selecione o prazo</option>
                        <option value="ate12Meses">Até 12 meses</option>
                        <option value="maisDe12Meses">Mais de 12 meses</option>
                    </select>

                    <div class="label-container">
                        <picture class="informacao-container" title=" informar o somatório dos juros recebidos no período-base relativos ao ativo declarado. O valor do campo deve ser maior ou igual a zero. Deve corresponder a um fluxo auferido somente no período de referência (trimestral ou anual).">
                            <img src="../resources/imgs/informacao.png" alt="Ícone informação"/>
                        </picture>
                        <label for="jurosRecebidos">Juros recebidos no período-base:</label>
                    </div>
                    <input type="number" name="jurosRecebidos" required id="jurosRecebidos" placeholder="Informe os juros recebidos">

                    <input type="submit" value="Salvar" class="btn salvar" id="salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
    </body>
</html>
