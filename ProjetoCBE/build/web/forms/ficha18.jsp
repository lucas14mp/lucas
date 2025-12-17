<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 18</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha18.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 18 - Título de dívida não-intercompanhia</h2>

                <p>
                    Devem ser declarados nesta ficha os títulos de dívida (i) detidos por declarantes pessoas físicas, 
                    em todos os casos e (ii) emitidos por empresas no exterior não pertencentes ao mesmo grupo econômico do declarante pessoa jurídica.
                </p>

                <p>
                    Títulos de dívida são instrumentos negociáveis no mercado financeiro, representativos de dívida entre o emissor (não residente) e 
                    seu detentor (residente, declarante do CBE). Incluem todos os títulos de dívida negociáveis no mercado tais como títulos de renda fixa, bônus, bonds,
                    notes, commercial papers, certificados de depósito bancário, entre outros instrumentos similares.
                </p>

                <p>
                    Caso a empresa emissora dos títulos no exterior seja do mesmo grupo econômico do declarante, esses ativos devem ser declarados na ficha 
                    "Título de dívida intercompanhia".
                </p>

                <p>
                    Podem ser agregadas informações de diversos títulos, desde que sejam coincidentes o país do emissor do título, a moeda de denominação e a 
                    categoria do prazo original do título de dívida.
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha18" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>

                    <div class="label-container">
                        <label for="pais">País emissor: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Escolher o país do emissor do título de dívida. No caso de grupos econômicos de origem de capital brasileiro, que emitem títulos a partir de suas subsidiárias no exterior, deve-se considerar o país da subsidiária, e não da matriz brasileira..</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione a moeda em que está referenciado o título de dívida. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getSigla()} | ${moeda.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label>Prazo original do título de dívida: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar uma opção para o prazo original do título de dívida, dividido em duas categorias: “Até 12 meses” ou “Mais de 12 meses”. Na hipótese de prazo flexível ou indefinido, utilize a melhor expectativa.</p>
                    <br>
                    <label>
                        <input type="radio" name="resposta-prazo" value="Até 12 meses" required >
                        Até 12 meses
                    </label>
                    <br>
                    <br>
                    <label>
                        <input type="radio" name="resposta-prazo" value="Mais de 12 meses" required>
                        Mais de 12 meses
                    </label>
                    <br>
                    <br>
                    <br>

                    <div class="label-container">
                        <label for="valor">Valor de mercado: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor de mercado do título de dívida na data-base. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>

                    <div class="label-container">
                        <label for="valor">Juros recebidos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o somatório dos juros recebidos no período-base relativos ao título declarado. O valor do campo deve ser maior ou igual a zero. O valor do campo 'Juros recebidos no período-base' é um fluxo auferido somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos base.</p>
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
