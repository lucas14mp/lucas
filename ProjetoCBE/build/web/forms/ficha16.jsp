<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 16</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha16.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 16 - Outros direitos</h2>
                <br>
                <p>
                    Devem ser declarados nesta ficha <b>apenas</b> os seguintes ativos:
                </p>
                <p>
                    - Bens (exceto bens imóveis): bens no exterior em posse do declarante;
                </p>
                <p>
                    - Crédito de imposto (imposto a receber): saldos de impostos a receber de não residentes, que configuram um direito do declarante;
                </p>
                <p>
                    - Direitos ou recebíveis que não se enquadram em créditos comerciais: ativos que não se enquadraram nos critérios de crédito comercial;
                </p>
                <p>
                    - Dividendos e outros reembolsos a receber: direitos de recebimento do declarante contra não residentes que não se enquadraram nas outras 
                    categorias de ativos passíveis de declaração;
                </p>
                <p>
                    - Moedas virtuais: moedas emitidas virtualmente, protegidas por criptografia, comumente distribuídas e controladas por seus desenvolvedores, 
                    independentemente de regulamentação ou de supervisão. Usadas e aceitas entre membros de comunidades virtuais específicas;  
                </p>            
                <p>
                    - Previdência: planos de previdências no exterior, declarados conforme o saldo passível de recebimento na data-base da declaração;
                </p>        
                <p>
                    - Salários: direitos remuneratórios por serviços prestados a não residentes, ainda não recebidos pelo declarante;
                </p>        
                <p>
                    - Seguros: planos de seguro estabelecidos com empresas não-residentes, cujo beneficiário é o declarante. 
                    São declarados conforme o saldo passível de recebimento na data-base;  
                </p>        
                <p>
                    - Sinistros ocorridos e indenizações a receber: outras expectativas de recebimento contra não-residentes. 
                    São declarados conforme o saldo passível de recebimento na data-base;
                </p>        
                <p>
                    - Trust ou Fundação: tipos de estruturas que permitem separar o direito aos recursos aplicados da propriedade legal do investimento e de sua administração. 
                    O investidor não 60 tem controle direto da gestão, mas é beneficiário dos ativos, numa relação que, no caso do trust, é chamada de fiduciária. Portanto, tais acordos só deverão ser declarados caso o beneficiário residente seja o próprio declarante. 
                    O valor na data-base será o valor relativo à participação do beneficiário nos ativos do trust ou da fundação; 
                </p>
                <br>
                <p>
                    Podem ser agregadas informações de diversos direitos, desde que sejam coincidentes o tipo de direito, o país e a moeda de denominação.    
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha16" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>

                    <div class="label-container">
                        <label for="direitos">Tipo de outros direitos: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione um item dentre as seguintes opções.</p>
                    <select name="direitos" id="direitos" required>
                        <option value="" selected>Selecione o tipo de outros direitos</option>
                        <option value="Bens">Bens (exceto bens imóveis)</option>
                        <option value="Crédito de imposto">Crédito de imposto</option>
                        <option value="Direitos ou recebíveis que não se enquadram em créditos comerciais">Direitos ou recebíveis que não se enquadram em créditos comerciais</option>
                        <option value="Dividendos e outros reembolsos a receber">Dividendos e outros reembolsos a receber</option>
                        <option value="Moedas virtuais">Moedas virtuais</option>
                        <option value="Previdência">Previdência</option>
                        <option value="Salários">Salários</option>
                        <option value="Seguros">Seguros</option>
                        <option value="Sinistros ocorridos e indenizações a receber">Sinistros ocorridos e indenizações a receber</option>
                        <option value="Trust ou Fundação">Trust ou Fundação</option>

                    </select>

                    <div class="label-container">
                        <label for="pais">País: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione o país do ativo</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione a moeda</option>
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
                            <option value="${moeda.getId()}">${moeda.getNome()} | ${moeda.getSigla()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="valor">Valor na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor de mercado do título de dívida na data-base. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                    <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>

                    <input type="submit" value="Salvar" class="btn salvar" id="salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>
