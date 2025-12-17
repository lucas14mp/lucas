<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 9</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha09.jsp">
                    <input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 9 - Derivativo - Futuro e <em>swap</em></h2>

                <p>
                    Devem ser declarados nesta ficha ativos nas modalidades derivativo futuro ou swap, incluindo os contratos a termo. Apenas os investidores detentores de derivativos devem declará-los.
                </p>

                <p>
                    Derivativos são instrumentos financeiros cujo valor deriva de um ativo predeterminado. Futuro é um instrumento financeiro em que é assumido o compromisso de comprar ou vender determinado ativo em determinada data a um determinado preço. Swap é um instrumento financeiro em que há um acordo entre duas partes para troca de riscos, segundo critérios preestabelecidos.
                </p>

                <p>
                    Podem ser agregadas informações de diversos derivativos, desde que sejam coincidentes o país de aquisição, o método de valoração e a moeda de denominação.
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha09" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>
                    <div class="label-container">
                        <label for="pais">País: <span class="asterisco">*</span></label>
                    </div> 
                    <p class="descricao">Informar o país da instituição responsável pela liquidação do contrato.</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda original em que está referenciado o contrato. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getNome()} | ${moeda.getSigla()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label>Método de valoração: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar um método de valoração para os montantes a receber ou pagar na data-base, escolhendo entre 'Marcação a mercado' ou 'Valor a receber ou pagar excluindo-se a margem de garantia.'</p>
                    <br>
                    <label>
                        <input type="radio" name="resposta-metodo" value="Marcação a mercado" required>
                        Marcação a mercado
                    </label>
                    <br>
                    <br>
                    <label>
                        <input type="radio" name="resposta-metodo" value="Valor a receber ou pagar excluindo-se a margem de garantia" required>
                        Valor a receber ou pagar excluindo-se a margem de garantia
                    </label>
                    <br>
                    <br>
                    <br>
                    <div class="label-container">
                        <label for="valor">Valor receber(+) / pagar(-) na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o valor a receber ou a pagar na data-base, referentes às posições em aberto na data-base da declaração.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
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
