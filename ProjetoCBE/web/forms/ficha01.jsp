<!-- =========================== FORM =========================== -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 1</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha01.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 1 - Ações negociadas em bolsa</h2>
                <p>
                    Ações negociadas em bolsa, são participações no capital de empresas não residentes, negociadas nas
                    bolsas de valores de cada país. Devem ser declaradas aqui ações (diretamente detidas) de empresas no
                    exterior negociadas em bolsa também no exterior, quando a participação não confira ao acionista exercer
                    o equivalente de até 10% do poder de voto da empresa listada.
                </p>
                <p>
                    Depositary Receipts (DRs) de empresas com sede no Brasil devem ser informadas na ficha "Depositary
                    Receipt - Empresa Brasileira".
                </p>
                <p>
                    Podem ser agregadas informações de diversas ações, desde que sejam coincidentes o país de mercado de
                    negociação das ações e a moeda de denominação.
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha01" method="post" class="form" id="formFicha01">
                    <input type="text" name="tipo-requisicao" value="post" hidden>
                    <div class="label-container">
                        <label for="pais">Mercado de negociação: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione o país do mercado de negociação da ação.</p>
                    <select name="pais" id="pais" required>
                        <option value="" selected>Selecione o país</option>
                        <c:forEach items="${paisController.listarPaises()}" var="pais">
                            <option value="${pais.getId()}">${pais.getNome()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione a moeda original em que está referenciado o valor do ativo. <br>Será com base nessa mesma moeda que deverão ser informados os demais valores nesta ficha.</p>
                    <select name="moeda" id="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedas()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getNome()} | ${moeda.getSigla()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="valor">Valor na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor do ativo na data-base. O valor do campo deve ser maior que zero.</p> 
                    <div class="box-moedas">
                        <div class="simbolo-moedas"> ${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <div class="label-container">
                        <label for="dividendos">Dividendos recebidos no período-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe a soma dos rendimentos recebidos no período-base para o ativo informado.<br> Se o período-base for trimestral, corresponde apenas aos três meses que compõe o trimestre. <br>Em caso da declaração anual (31/12), corresponde aos 12 meses do ano. <br>O valor do campo deve ser maior ou igual a zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas"> ${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="dividendos" required id="dividendos" placeholder="Digite um valor maior ou igual a 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*(\.\d+)*|0*[1-9]\d*(\.\d+)*)(,\d+)?$">
                    </div>
                    <div class="botoes" style="justify-content: flex-start; margin-top: 15px;">
                        <button type="button" class="btn" id="btnAdicionar"> + Adicionar à Lista </button>
                    </div>

                    <hr style="margin: 30px 0; border-top: 1px solid #eee;">

                    <h3 style="color: #003366; font-family: 'BancoDoBrasilTitulos-Regular', sans-serif;">Itens Adicionados</h3>

                    <div class="table-responsive">
                        <table class="table table-bordered table-striped" id="tabelaItens" style="width: 100%; margin-bottom: 20px; border-collapse: collapse;">
                            <thead>
                                <tr style="background-color: #f8f9fa; text-align: left;">
                                    <th style="padding: 10px; border: 1px solid #ddd;">País</th>
                                    <th style="padding: 10px; border: 1px solid #ddd;">Moeda</th>
                                    <th style="padding: 10px; border: 1px solid #ddd;">Valor</th>
                                    <th style="padding: 10px; border: 1px solid #ddd;">Dividendos</th>
                                    <th style="padding: 10px; border: 1px solid #ddd; text-align: center; width: 80px;">Ação</th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>

                    <div class="botoes" id="areaBotaoFinal" style="display:none; margin-top: 30px; justify-content: center;">
                        <button type="button" class="btn salvar" id="btnFinalizarLote">Salvar e Enviar Ficha</button>
                    </div>

                    <input type="hidden" name="justificativa_gestor" id="hiddenJustificativa" value="">

                    <div id="modalJustificativa" style="display:none; position:fixed; z-index:9999; left:0; top:0; width:100%; height:100%; background-color:rgba(0,0,0,0.6);">
                        <div style="background-color:#fff; margin:10% auto; padding:25px; border:1px solid #888; width:50%; border-radius:8px; box-shadow: 0 4px 8px rgba(0,0,0,0.2); text-align:center; font-family: Arial, sans-serif;">

                            <h2 style="color: #003366; margin-bottom: 15px;">Aviso de Variação</h2>

                            <p style="font-size: 16px; color: #333; margin-bottom: 20px;">
                                A diferença entre o valor informado e o valor de referência na base de dados é muito divergente.
                                <br><br>
                                Para prosseguir com o envio desta ficha, é <strong>obrigatório</strong> justificar o motivo desta divergência.
                            </p>

                            <textarea id="textoJustificativa" rows="5" style="width:100%; padding:10px; border:1px solid #ccc; border-radius:4px; resize:vertical;" placeholder="Digite aqui sua justificativa detalhada..."></textarea>

                            <div class="modal-actions">
                                <button type="button" id="btnConfirmarJustificativa" class="btn">Confirmar e Salvar</button>
                                <button type="button" id="btnCancelarJustificativa" class="btn-secondary">Cancelar</button>
                            </div>
                        </div>
                    </div>
                </form>
            </article>
        </main>
        <script src="${pageContext.request.contextPath}/resources/js/CalcularDiferenca.js"></script>                       
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
    </body>
</html>