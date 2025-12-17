<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 0</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/empresas.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 0 - Empresas</h2>
                <p>
                    A identificação da empresa no exterior é condição prévia apenas para o preenchimento dos ativos nas quais o declarante detém participação no capital igual ou superior a 10%, ou aquelas empresas devedoras do mesmo grupo econômico (intercompanhia).
                </p>
                <ul>
                    <li>
                        <p>Empresas – participação no capital, em que o poder de voto do declarante é maior que 10%;</p>
                    </li>
                    <li>
                        <p>Fundos de investimento, em que a participação do declarante no capital é maior que 10%;</p>
                    </li>
                    <li>
                        <p>Crédito comercial intercompanhia;</p>
                    </li>
                    <li>
                        <p>Empréstimo intercompanhia;</p>
                    </li>
                    <li>
                        <p>Título de dívida intercompanhia.</p>
                    </li>
                </ul>

                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha0" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>

                    <div class="label-container">
                        <label for="nome-empresa">Nome da empresa: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o nome da pessoa jurídica no exterior.</p>
                    <input type="text" id="nome-empresa" name="nome-empresa" required placeholder="Insira o nome da empresa">

                    <div class="label-container">
                        <label for="pais">País:</label>
                    </div>
                    <p class="descricao">Informar o país de residência da empresa.</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>
                    <div class="label-container">
                        <label>A empresa transaciona (detém ativos e/ou passivos) quase exclusivamente com pessoas ou empresas de países diferentes do país selecionado? <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">A pergunta deve ser respondida afirmativamente por aquelas empresas que desempenham, quase exclusivamente, a função de canalizar recursos entre diferentes jurisdições em relação à que está instalada, em geral um país tido como paraíso fiscal. O conceito 'quase exclusivamente' busca excluir as empresas que exerçam atividades operacionais de fato (produtos e serviços, financeiros ou não) com contrapartes residentes da jurisdição onde estão instaladas.</p>
                    <br>
                    <label for="empresa-transaciona-sim">
                        <input type="radio" name="empresa-transaciona" id="empresa-transaciona-sim" value="true">
                        Sim
                    </label><br><br>
                    <label for="empresa-transaciona-nao">
                        <input type="radio" name="empresa-transaciona" id="empresa-transaciona-nao" value="false">
                        Não
                    </label><br><br>

                    <div class="label-container">
                        <label for="cdnr">Cadastro Declaratório de Não Residentes – CDNR (antigo Cademp):</label>
                    </div>
                    <p class="descricao">Código requisito às pessoas físicas ou jurídicas não residentes para registro de operações no sistema RDE. É necessário para empresas no exterior que investem (emprestam) em (a) residentes no Brasil. Campo de preenchimento opcional.</p>
                    <input type="text" id="cdnr" name="cdnr" placeholder="Insira o CDNR">

                    <div class="label-container">
                        <label>Relação com o declarante: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar o relacionamento da empresa no exterior com o declarante, dentre as seguintes opções:</p>
                        <br>
                    <label>
                        <input type="radio" name="relacao-declarante" value="Empresa declarante é empresa irmã da empresa no exterior">
                        Empresa declarante é empresa irmã da empresa no exterior: a empresa declarante e a empresa no exterior pertencem ao mesmo grupo econômico.
                    </label><br><br>
                    <label>
                        <input type="radio" name="relacao-declarante" value="Empresa declarante é investidora direta na empresa no exterior">
                        Empresa declarante é investidora direta na empresa no exterior: a empresa declarante possui poder de voto igual ou superior a 10% na empresa no exterior.
                    </label><br><br>
                    <label>
                        <input type="radio" name="relacao-declarante" value="Empresa declarante é investidora indireta na empresa no exterior">
                        Empresa declarante é investidora indireta na empresa no exterior: a empresa declarante possui, indiretamente, poder de voto igual ou superior a 10% na empresa no exterior.
                    </label><br><br>
                    <label>
                        <input type="radio" name="relacao-declarante" value="Empresa declarante é investida direta ou indireta da empresa no exterior">
                        Empresa declarante é investida direta ou indireta da empresa no exterior: a empresa no exterior possui, direta ou indiretamente, poder de voto igual ou superior a 10% na empresa declarante.
                    </label><br><br>
                    <label>
                        <input type="radio" name="relacao-declarante" value="Declarante é investidor direto na empresa no exterior">
                        Declarante é investidor direto na empresa no exterior: apenas disponível para declarantes pessoas físicas. O declarante pessoa física possui poder de voto igual ou superior a 10% na empresa no exterior.
                    </label><br><br>

                    <div class="label-container">
                        <label for="numero-empregados">Número de empregados: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Preencher com o número efetivo de funcionários da empresa (inclusive diretores, mesmos os estatutários) no exterior, exceto terceirizados, estagiários e equivalentes.</p>
                    <input type="number" id="numero-empregados" name="numero-empregados" required placeholder="Insira o número de empregados">

                    <div class="label-container">
                        <label>Atividade econômica: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informar a atividade econômica exercida pela empresa no exterior, de acordo com a geração de suas receitas. Não necessariamente é a mesma do declarante. Caso a empresa desempenhe mais de uma das atividades econômicas, considerar aquela que resulta em maior receita para a empresa no exterior.</p>
                    <br>
                    <label>
                        <input type="radio" name="atividade-economica" id="atvd-64" value="64 - Atividades de serviços financeiros">
                        64 - Serviços financeiros e atividades auxiliares
                    </label><br><br>

                    <label>
                        <input type="radio" name="atividade-economica" id="atvd-100" value="100 - Empresa constituída para aquisição de ativos financeiros">
                        100 - Empresa constituída para aquisição de ativos financeiros
                    </label>


                    <br>
                    <br>


                    <!--Mostrar somente se atividade economica == 64 ou 65-->
                    <div id="detalhamento-atividade-container" style="display: none;">
                        <div class="label-container">
                            <picture class="informacao-container" title="Se, no campo número 'Atividade Econômica', forem selecionadas as atividades econômicas “64 - Atividades de serviços financeiros” ou “65 - Seguros, resseguros, previdência complementar e planos de saúde”, este campo estará disponível para o detalhamento da respectiva atividade. Caso e empresa desempenhe mais de uma das atividades listadas, selecionar a principal.">
                                <img src="../resources/imgs/informacao.png" alt="Ícone informação"/>
                            </picture>
                            <label for="detalhamento-atividade-economica">Detalhamento da atividade econômica:</label>
                        </div><br>
                        <textarea name="detalhamento-atividade-economica" id="detalhamento-atividade-economica" placeholder="Detalhe a atividade econômica"></textarea>
                    </div>

                    <input type="submit" value="Salvar" class="btn salvar">
                </form>
            </article>
        </main>
        <script src="/ProjetoCBE/resources/js/ficha0.js"></script>    
        <script src="/ProjetoCBE/resources/js/temas.js"></script> 
    </body>
</html>
