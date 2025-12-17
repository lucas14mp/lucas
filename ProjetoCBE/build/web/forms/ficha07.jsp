<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 7</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha07.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 7 - <em>Depositary Receipt</em> - Empresa não-brasileira</h2>
                <br>
                <p>
                    <em>Depositary receipts</em> de empresa não-brasileira são certificados representativos 
                    de valores mobiliários de emissão de companhias abertas, ou assemelhadas, com sede no exterior 
                    e emitidos por instituição depositária também no exterior.
                </p>
                <p>
                    Podem ser agregadas informações de diversos depositary receipts de empresas não-brasileiras, 
                    desde que sejam coincidentes o país de negociação dos certificados, o país da empresa emissora 
                    dos certificados e a moeda de denominação.
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha07" class="form" method="post">
                    <input type="text" name="tipo-requisicao" value="post" hidden>
                    <div class="label-container">
                        <label for="pais-negociacao">País de negociação: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione o país de negociação do certificado.</p>
                    <select name="pais-negociacao" id="pais-negociacao" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="pais-emissor">País da empresa não-brasileira: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione o país da empresa não-brasileira emissor do certificado.</p>
                    <select name="pais-emissor" id="pais-emissor" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda original em que está referenciado o valor do ativo. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getSigla()} | ${moeda.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="valor">Valor de mercado na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o valor de mercado do ativo na data-base. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>


                    <div class="label-container">
                        <label for="dividendos">Dividendos e outros rendimentos recebidos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe a soma dos dividendos e outros rendimentos recebidos no período-base para o ativo informado. O valor do campo deve ser maior ou igual a zero.</p>
                    <input type="text" name="dividendos" required id="dividendos" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$">

                    <input type="submit" value="Salvar" class="btn salvar" id="salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>