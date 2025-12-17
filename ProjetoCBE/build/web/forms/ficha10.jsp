<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 10</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha10.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 10 - Derivativo - Opção</h2>
                <br>
                <p>
                    Devem ser declarados nesta ficha ativos na modalidade derivativo - opção.
                </p>
                <p>
                    Opção é um instrumento financeiro que confere ao adquirente o direito de comprar ou vender determinado ativo, a determinado preço, em data futura.
                </p>
                <p>
                    A CBE capta informações de ativos externos detidos por residentes no país. Portanto, emissores de opções em mercados internacionais não devem declará-las na CBE. Apenas os investidores detentores de opções devem fazê-lo.
                </p>
                <p>
                    Podem ser agregadas informações de diversas opções, desde que sejam coincidentes o país de aquisição, a moeda de denominação e o método de valoração.
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>


                <form action="<%=request.getContextPath()%>/ficha10" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>
                    <div class="label-container">
                        <label for="pais">País: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione o país em que reside o emissor da opção. Caso não seja possível identificá-lo, informar o país no qual a opção foi adquirida.</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione a moeda original em que está referenciada a opção. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getNome()} | ${moeda.getSigla()}</option>
                        </c:forEach>
                    </select>

                    <br>

                    <div class="label-container">
                        <label>Método de valoração: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar um método de valoração para o valor na data-base, escolhendo entre “Valor intrínseco”, o valor imediato de uma opção em relação ao preço do ativo-objeto, e “Valor extrínseco”, parcela do prêmio de uma opção atribuída ao risco ou ao custo de oportunidade e expectativas sobre a evolução do ativo-objeto.</p>
                    <br>
                    <label>
                        <input type="radio" name="metodo" value="Valor intrínseco" required>
                        Valor intrínseco
                    </label>
                    <br>
                    <br>
                    <label>
                        <input type="radio" name="metodo" value="Valor extrínseco" required>
                        Valor extrínseco
                    </label>
                    <br>
                    <br>
                    <br>

                    <div class="label-container">
                        <label for="valor">Valor na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">O valor da opção com base no método de valoração, na data-base da declaração. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <br>
                    <br>

                    <input type="submit" value="Salvar" class="btn salvar" id="salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>
