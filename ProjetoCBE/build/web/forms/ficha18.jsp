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
                                    <th style="padding: 10px; border: 1px solid #ddd;">Prazo</th>
                                    <th style="padding: 10px; border: 1px solid #ddd;">Vlr. Mercado</th>
                                    <th style="padding: 10px; border: 1px solid #ddd;">Juros</th>
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
        <script src="${pageContext.request.contextPath}/resources/js/ficha18.js"></script>
    </body>
</html>
