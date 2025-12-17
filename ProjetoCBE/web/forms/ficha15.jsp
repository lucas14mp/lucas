<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 15</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha15.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 15 - Imóvel</h2>
                <br>
                <p>
                    Devem ser declarados nesta ficha imóveis no exterior, como casa, apartamento, fazenda, terreno.
                </p>
                <p>
                    Os imóveis devem ser declarados por seus titulares, considerando o valor integral, caso seja atestável a propriedade pelo residente 
                    na data-base da declaração, independentemente do valor efetivamente pago até a data-base ou do saldo financiado na data-base, 
                    bem como da existência de usufrutuários.
                </p>
                <p>
                    <b>Atenção</b>: caso dois ou mais titulares detenham de <b>forma conjunta</b> o mesmo imóvel, cada parte deverá considerar o 
                    valor integral deste ativo para análise do enquadramento dos critérios de obrigatoriedade da declaração. 
                    Porém, devem realizar a declaração apenas da sua respectiva parcela, mesmo que o total individual declarado 
                    individualmente seja inferior ao piso de obrigatoriedade.
                </p>
                <p>
                    Podem ser agregadas informações de diversos imóveis, desde que sejam coincidentes o país, a moeda de denominação, 
                    o método de valoração e a informação se o imóvel está quitado.
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>

                <form action="<%=request.getContextPath()%>/ficha15" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>

                    <div class="label-container">
                        <label for="pais">País do imóvel: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Escolher o país do imóvel no exterior</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda original em que está referenciado o valor do imóvel e saldo devedor. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getNome()} | ${moeda.getSigla()}</option>
                        </c:forEach>
                    </select>


                    <div class="label-container">
                        <label for="metodo">Método de valoração: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar um método de valoração do valor na data-base, escolhendo entre 'Valor de aquisição', 'Valor de aquisição com benfeitorias' e 'Valor de mercado.'</p>
                    <div class="label-container">
                        <select name="metodo" id="metodo" required>
                            <option value="" selected>Selecione o método de valoração</option>                
                            <option value="Valor de aquisição">Valor de aquisição</option>
                            <option value="Valor de aquisição com benfeitorias">Valor de aquisição com benfeitorias</option>
                            <option value="Valor de mercado">Valor de mercado</option>
                        </select>
                    </div>

                    <div class="label-container">
                        <label for="valor">Valor na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o valor do imóvel na data-base, conforme o método de valoração escolhido. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>

                    <div class="label-container">
                        <label>O imóvel está quitado? <span class="asterisco">*</span></label>
                    </div>
                    <br>
                    <p class="descricao">Selecione uma opção. Caso escolha 'Sim', o campo “Saldo devedor na data-base” será desabilitado. Caso escolha não, o saldo devedor deverá ser preenchido.</p>
                    <label>
                        <input type="radio" name="quitado" value="true" required>
                        Sim
                    </label>
                    <br>
                    <br>
                    <label>
                        <input type="radio" name="quitado" value="false" required>
                        Não
                    </label>
                    <br>
                    <br>

                    <div class="label-container">
                        <label for="devedor">Saldo devedor na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o saldo devedor de financiamento remanescente na data-base. Caso tenha respondido que o imóvel está quitado, este campo ficará desabilitado. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                    <input class="input-moedas" type="text" name="devedor" required id="devedor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>

                    <div class="label-container">
                        <label for="alugueis">Aluguéis recebidos no período: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o total dos aluguéis recebidos pelo imóvel no período base. O valor do campo deve ser maior ou igual a zero. O fluxo deve ser apurado somente no período de referência (trimestral ou anual, conforme a declaração). Não deve ser preenchido com dados acumulados ou relativos a outros períodos-base.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                    <input class="input-moedas" type="text" name="alugueis" required id="alugueis" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <input type="submit" value="Salvar" class="btn salvar" id="salvar">
                </form>
            </article>
            <script src="/ProjetoCBE/resources/js/ficha15.js"></script>
            <script src="/ProjetoCBE/resources/js/temas.js"></script>
            <script src="/ProjetoCBE/resources/js/moedas.js"></script>
        </main>
    </body>
</html>