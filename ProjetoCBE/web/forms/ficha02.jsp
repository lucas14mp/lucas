<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 2</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha02.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 2 - <em>Brazilian Depositary Receipt</em></h2>
                <br>
                <p>
                    <em>Brazilian Depositary Receipts</em> (BDRs) são certificados representativos de valores mobiliários
                    de emissão de companhias abertas, ou assemelhadas, com sede no exterior e emitidos por instituição depositária no Brasil.
                    Apenas as instituições depositárias devem informar nesta ficha os valores de propriedade de investidores residentes,
                    domiciliados ou com sede no Brasil.
                </p>
                <p>
                    Os BDRs podem ser negociados em bolsas de valores ou no mercado de balcão organizado. A emissão dos certificados é lastreada
                    em valores mobiliários depositados em instituições custodiantes no país em que os ativos lastro são negociados.
                    Para atuar como instituição custodiante, tais entidades devem ser autorizadas, por órgão similar à CVM, a manter em custódia
                    os valores mobiliários. A informação prestada deve ser individualizada por programa autorizado pela CVM.
                </p>
                <p>
                    Podem ser agregadas informações de diversos BDRs, desde que seja coincidente o país do emissor.
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha02" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>
                    <div class="label-container">
                        <label for="pais">País da empresa: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione o país sede da empresa emissora do BDR.</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="dividendos">Dividendos e outros rendimentos recebidos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe a soma dos rendimentos recebidos no período-base para o ativo informado. <br>Se o período-base for trimestral, corresponde apenas aos três meses que compõe o trimestre. <br>Em caso da declaração anual (31/12), corresponde aos 12 meses do ano.</p>
                    <input type="text" name="dividendos" required id="dividendos" placeholder="Digite o valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$">

                    <div class="label-container">
                        <label for="valor">Valor de mercado na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor do ativo na data-base. O valor do campo deve ser maior que zero.</p>
                    <input type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$">

                    <input type="submit" value="Salvar" class="btn salvar" id="salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
    </body>
</html>
