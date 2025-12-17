<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="numeroUtils" class="br.com.bb.cbe.Utils.NumeroUtils"/>
<jsp:useBean id="ficha14EmpresaController" class="br.com.bb.cbe.controllers.Ficha14EmpresaController"/>
<%@ page import="br.com.bb.cbe.controllers.Ficha14MaiorController" %>
<%@ page import="br.com.bb.cbe.Bean.Ficha14Maior" %>
<%
    Ficha14MaiorController fichaController = new Ficha14MaiorController();
    int idFicha = Integer.parseInt(request.getParameter("id"));
    Ficha14Maior ficha = fichaController.getFichaById(idFicha);
    pageContext.setAttribute("ficha", ficha);
%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 14 - Empresa</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">                
                <a href="../views/ficha14.jsp"><input type="button" value="Voltar" class="btn voltar"></a>
                <h2>Ficha 14 - Fundos de Investimento</h2>
                <br>
                <p>
                    <b>Controladas pela empresa no exterior:</b>
                </p>
                <p>
                    Os declarantes deverão cadastrar as empresas que cumpram <b>todos</b> os requisitos a seguir:
                </p>
                <p>
                    a) Empresas ou fundos que exerçam atividades econômicas operacionais de fato. Isto significa que esta empresa produz bens ou presta serviços (inclusive financeiros). Empresas que exercem funções de jure (por exemplo, as constituídas sob a forma de holdings), devem ser desconsideradas;
                </p>
                <p>
                    b) A empresa pertence à cadeia de controle no qual o declarante detém participação direta. O controle é transmitido ao longo dos elos da cadeia desde que haja mais de 50% do poder de voto.

                </p>
                <p>
                    c) A empresa ou fundo é a primeira de seu ramo organizacional a exercer atividade econômica de fato (nas edições anteriores do CBE, solicitava-se a declaração apenas das empresas que estavam ao fim da cadeia de controle).
                </p>
                <br>
                <p><b>Por gentileza, verifique as informações preenchidas anteriormente:</b></p>
                <br>
                <div class='dados-empresa-preenchida'>
                    <p><b>Fundo de investimento: </b>${ficha.getEmpresa().getNome()}</p>                        
                    <p><b>Moeda: </b>${ficha.getMoeda().getSigla()} - ${ficha.getMoeda().getNome()}</p>
                    <p><b>Patrimônio líquido na data-base: </b>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getPatrimonioLiquido())}</p>
                    <p><b>Percentual de participação no patrimônio: </b>${numeroUtils.doubleToString(ficha.getParticipacaoPatrimonio())}%</p>
                    <p><b>Rendimentos (positivos ou negativos) do fundo no período-base: </b>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getRendimentosFundo())}</p>
                    <p><b>Rendimentos distribuídos no período-base: </b>${ficha.getMoeda().getSimbolo()} ${numeroUtils.doubleToString(ficha.getRendimentosDistribuidos())}</p>
                    <p><b>O fundo no exterior controla outras empresas direta ou indiretamente, também no exterior, que estão ao final da cadeia de controle? </b>Sim</p>
                </div>
                <br>
                <br>
                <p>Preencha as informações abaixo para adicionar a empresa controlada:</p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action='<%=request.getContextPath()%>/ficha14Empresa' method='post' id='formulario' class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>
                    <input type="number" name="id" hidden value="${ficha.getId()}">

                    <div class="label-container">
                        <label for="nome">Nome da empresa controlada: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o nome da empresa controlada.</p>
                    <input type="text" id="nome" name="nome" required placeholder="Digite o nome da empresa">

                    <div class="label-container">
                        <label for="pais">País da empresa controlada: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o país da empresa controlada.</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="atividade">Atividade econômica principal: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione a atividade econômica principal da empresa controlada, independentemente da atividade do fundo ou da declarante.</p>
                    <select id="atividade" name="atividade" required>
                        <option value="" selected>Selecione a atividade econômica</option>
                        <option value="Negociação de títulos">Negociação de títulos</option>
                        <option value="Viagens e Turismo">Viagens e Turismo</option>
                        <option value="Recuperação de créditos">Recuperação de créditos</option>
                        <option value="Financeira">Bens</option>
                        <option value="Gestão de Fundos">Gestão de Fundos</option>
                    </select>

                    <div class="label-container">
                        <label for="percentual">Percentual de participação no capital social: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o percentual de participação no capital social que a empresa no exterior possui da controlada. Informe um valor maior que zero e menor ou igual a 100. Note que deve ser informado o percentual como múltiplo de 100, por exemplo, o valor 15 representa 15%. O percentual que a empresa ou fundo no exterior (no qual o declarante possui participação direta) detém da controlada. Ex.: a participação em 'B2' é de 55% multiplicada por 65%: 36%.</p>
                    <input type='number' id='percentual' name='percentual' min='1' max='100' step='0.01' required placeholder='Digite um valor entre 1 e 100'>

                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecionar a moeda em que está denominado o patrimônio do fundo. Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select id="moeda" name="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getSigla()} | ${moeda.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="patrimonio">Patrimônio líquido total na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor total do patrimônio líquido da empresa controlada na data-base. Este campo pode assumir valores positivos, nulos ou negativos.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="patrimonio" name="patrimonio" required placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <div class="label-container">
                        <label for="valor">Valor de mercado na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Estimar o valor de mercado da empresa a partir de métodos de valoração preferencialmente distintos do patrimônio líquido.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas">${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" id="valor" name="valor" required placeholder="Digite um valor" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(-?0*(\.\d+)*|-?0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <div class="label-container">
                        <label>A empresa está ao final da cadeia de controle? <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Responder afirmativamente caso a empresa esteja no último nível de seu ramo da cadeia de controle (ex.: empresa 'B2').</p>
                    <label>
                        <input type="radio" name="final" value="true" required>
                        Sim
                    </label>
                    <br>
                    <br>
                    <label>
                        <input type="radio" name="final" value="false" required>
                        Não
                    </label>
                    <br>
                    <br>

                    <div class="btn-group">
                        <button type="submit" class="btn salvar" id="salvar">Salvar</button>
                    </div>
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
    </body>
</html>