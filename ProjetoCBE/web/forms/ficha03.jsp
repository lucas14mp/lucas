<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" type="imagex/png" href="<%=request.getContextPath()%>/resources/imgs/logo_branca.png">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha 3</title>
    </head>
    <body>
        <%@include file="../topo.jsp"%>
        <main class="main">
            <article class="article">
                <a href="../views/ficha03.jsp"><input type="button" value="Voltar" class="btn voltar" id="voltar"></a>
                <h2>Ficha 3 - Câmbio Manual</h2>
                <br>
                <p>
                    Devem ser declarados nesta ficha os saldos em papel moeda estrangeira detidos na data-base.
                </p>
                <p>
                    Este ativo deve ser preenchido apenas por instituições financeiras autorizadas a operar no mercado de câmbio.
                </p>
                <p>
                    Não devem declarar as pessoas físicas que mantém em sua posse moeda estrangeira.
                </p>
                <br>
                <p><b>(<span class="asterisco">*</span>) Obrigatória</b></p>
                <form action="<%=request.getContextPath()%>/ficha03" method="post" class="form">
                    <input type="text" name="tipo-requisicao" value="post" hidden>
                    <div class="label-container">
                        <label for="moeda">Moeda: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Selecione a moeda, que não pode ser o Real (R$).</p>
                    <select name="moeda" id="moeda" required>
                        <option value="" selected>Selecione a moeda</option>
                        <c:forEach items="${moedaController.listarMoedasEstrangeiras()}" var="moeda">
                            <option value="${moeda.getId()}">${moeda.getNome()} | ${moeda.getSigla()}</option>
                        </c:forEach>
                    </select>

                    <div class="label-container">
                        <label for="valor">Valor na data-base: <span class="asterisco">*</span></label>
                    </div>
                    <p class="descricao">Informe o valor do ativo na data-base. O valor do campo deve ser maior que zero.</p>
                    <div class="box-moedas">
                        <div class="simbolo-moedas" id='simboloMoeda'>${moeda.getSimbolo()}</div>
                        <input class="input-moedas" type="text" name="valor" required id="valor" placeholder="Digite um valor maior que 0" pattern="^(?!.*[.,]$)(?!^[.,])(?!.*[.,]{2})(0*[1-9]\d*(\.\d+)*)(,\d+)?$">
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
                                    <th style="padding: 10px; border: 1px solid #ddd;">Moeda</th>
                                    <th style="padding: 10px; border: 1px solid #ddd;">Valor na Data-Base</th>
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
        <script src="/ProjetoCBE/resources/js/temas.js"></script>
        <script src="/ProjetoCBE/resources/js/moedas.js"></script>
        <script src="${pageContext.request.contextPath}/resources/js/ficha03.js"></script>
    </body>
</html>
