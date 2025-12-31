function PegarTaxas(){
    var taxas = JSON.parse(taxasJson);
    return taxas;
}

function PegarFichasMenores(){
    console.log("PASSOU CERTO");
    var fichasMenor = JSON.parse(fichasMenorJson);
    return fichasMenor;
}

function PegarFichasMaiores(){
    var fichasMenor = JSON.parse(fichasMenorJson);
    return fichasMenor;
}

function PegarFichas(){
    console.log("PASSOU ERRADO");
    var fichas = JSON.parse(fichasJson || "[]");
    return fichas;
}

function PegarFichasByNum(num){
     switch(num){
        case "8":
            console.log("entrou ficha 8");
    var fichas = JSON.parse(fichasJson8 || "[]");
    return fichas; 
            break;
        case "3":
            console.log("entrou ficha 3");
    var fichas = JSON.parse(fichasJson3 || "[]");
    return fichas; 
            break;
         case "16":
            console.log("entrou ficha 16");
    var fichas = JSON.parse(fichasJson16 || "[]");
    return fichas; 
            break;
            case "18":
            console.log("entrou ficha 18");
    var fichas = JSON.parse(fichasJson18 || "[]");
    return fichas; 
            break;
        default :
            break;
    
}
}


function PegarContabeis(){
    var contabeis = JSON.parse(contabilJson);
    return contabeis;
}

function GetCosifByCosif(cosif, contabilArray){
    return contabilArray.find(item =>item.cosif === cosif);

}

function GetTaxaBySigla(sigla){
    var ptax = PegarTaxas();
    console.log(ptax);
    console.log(sigla);
    return ptax.find(item =>item.moeda.sigla === sigla);
}

function ConverterValores(valor, sigla){
    var valorFinal = 0;
    var taxaConversao = GetTaxaBySigla(sigla);
    taxaConversao = substituirPontoPorVirgula(taxaConversao.compra);
    console.log("VALOR: ", valor);
    console.log("TAXA CONVERSÃO: ", taxaConversao);
    valorFinal = taxaConversao * valor;
    console.log(valorFinal);
    return valorFinal;
}


function substituirPontoPorVirgula(numero) {
    // Converte o número para string
    var numeroStr = numero.toString();
    
    // Verifica se a string contém um ponto
    if (numeroStr.includes('.')) {
        // Substitui o ponto por vírgula
        var numeroComVirgula = numeroStr.replace('.', ',');
        // Converte a string modificada de volta para número com casas decimais
        var numeroDouble = parseFloat(numeroComVirgula.replace(',', '.'));
        return numeroDouble;
    } else {
        // Converte a string original de volta para número com casas decimais
        return parseFloat(numeroStr);
    }
}
 
function ficha1Ca1(){
    diferenca = 1;
    return diferenca;
}

function ficha3Cal(){
     var contabil = PegarContabeis();
    console.log(contabil);
    var somatorioFichas = getSomatorioFichas("ficha03", "ficha03");
    contabil = getContabilByFicha("ficha03", "ficha03");
    console.log(contabil.saldo);      
    diferenca = contabil.saldo - somatorioFichas;
    console.log(diferenca);
    diferenca = 1;
    return diferenca;
}


//
//var contabil = PegarContabeis();
//    console.log(contabil);
//
//    var somatorioFichas = getSomatorioFichas("ficha08");
//    contabil = getContabilByFicha("ficha08");
//
//    console.log(contabil.saldo);
//
//    var diferenca = contabil.saldo - somatorioFichas;
//    console.log(diferenca);
//    diferenca = 1;
//    return diferenca; // Removido o sobrescrevimento com 1
//};


function ficha8Cal01(){
    var contabil = PegarContabeis();
    console.log(contabil);
    var somatorioFichas = getSomatorioFichas("ficha08", "ficha08");
    contabil = getContabilByFicha("ficha08", "ficha08");
    console.log(contabil.saldoDatabase);   
   diferenca = contabil.saldoDatabase - somatorioFichas;
        console.log(diferenca);
        diferenca = 1;
        return diferenca;
}

function ficha8Cal2(){
    diferenca = 1;
    return diferenca;
}

function ficha11Cal(porcentagem){
    console.log(porcentagem);
    if (porcentagem === "maior"){
        var num = 0;
        return num;
    }
    else if(porcentagem === "menor"){
        var contabil = PegarContabeis();
        console.log(contabil);
        var somatorioFichas = 0;
        contabil = GetCosifByCosif("1.3.1.85.60.00-", contabil);
        console.log(contabil.saldo);      
        var fichas = PegarFichasMenores();
        console.log(fichas);
        for (var i = 0; i < fichas.length; i++){
            var ficha = fichas[i];
            console.log("Pais: ", ficha.pais.nome);
            console.log("Valor: ", ficha.valorParticipacao);
            var valorReal = ConverterValores(ficha.valorParticipacao, ficha.moeda.sigla);
            somatorioFichas += valorReal;
            console.log("SOMATÓRIO: ", somatorioFichas);
            console.log("--------------------------");
        }
        diferenca = contabil.saldo - somatorioFichas;
        console.log(diferenca);
        return diferenca;
    }
    else{
        console.log("CASO TENHA ALTERADO O DATA-FICHA PARA ALGO DIFERENTE DE 'menor' ou 'maior', ELE NÃO VAI EFETUAR NENHUM CÁLCULO  E \n\
        A VALIDAÇÃO NÃO VAI FUNCIONAR!!!");
    }
    diferenca = 1;
    return diferenca;
}

function ficha14Cal(porcentagem){
    diferenca = 1;
    return diferenca;
}

function ficha16Ca1(){
     var contabil = PegarContabeis();
    console.log(contabil);
    var somatorioFichas = getSomatorioFichas("ficha16", "ficha16");
    contabil = getContabilByFicha("ficha16", "ficha16");
    console.log(contabil.saldo);   
   diferenca = contabil.saldo - somatorioFichas;
    console.log(diferenca);
    diferenca = 1;
    return diferenca;
}

function ficha18Ca1(){
    diferenca = 1;
    return diferenca;
}

//ALTERA AONDE TA FALANDO PEGARCICHAS, USA O PEGARFICHABYNUM
function getSomatorioFichas(numFicha, info){
    console.log("ENTROU NO GET");
    console.log(numFicha);
    console.log(info);
    var somatorioFichas = 0;
    switch(numFicha){
        case "ficha1":
            
            break;
        case "ficha03":
            var fichas = PegarFichasByNum("3");
            console.log(fichas);
            for (var i = 0; i < fichas.length; i++){
                var ficha = fichas[i];
                console.log("Valor: ", ficha.valorDatabase);
                var valorReal = ConverterValores(ficha.valorDatabase, ficha.moeda.sigla);
                somatorioFichas += valorReal;
                console.log("SOMATÓRIO: ", somatorioFichas);
                console.log("--------------------------");
            }
            return somatorioFichas;
            
            break;
        case "ficha08":
            var fichas = PegarFichasByNum("8");
            console.log(fichas);
            for (var i = 0; i < fichas.length; i++){
                var ficha = fichas[i];
                console.log("Pais: ", ficha.pais);
                console.log("Valor: ", ficha.saldoDatabase);
                var valorReal = ConverterValores(ficha.saldoDatabase, ficha.moeda.sigla);
                somatorioFichas += valorReal;
                console.log("SOMATÓRIO: ", somatorioFichas);
                console.log("--------------------------");
            }
            return somatorioFichas;
            break;
        case "ficha11":
            if(info === "maior"){
               return somatorioFichas;
            }
            else if (info === "menor"){
                var fichas = PegarFichasMenores();
                for (var i = 0; i < fichas.length; i++){
                    var ficha = fichas[i];
                    console.log("Pais: ", ficha.pais.nome);
                    console.log("Valor: ", ficha.valorParticipacao);
                    var valorReal = ConverterValores(ficha.valorParticipacao, ficha.moeda.sigla);
                    somatorioFichas += valorReal;
                    console.log("SOMATÓRIO: ", somatorioFichas);
                    console.log("--------------------------");
                    return somatorioFichas;
                }
            }
            else{
                return somatorioFichas;
            }
            
            break;
            
        case "ficha16":
   
            var fichas = PegarFichasByNum("16");
            console.log(fichas);
            for (var i = 0; i < fichas.length; i++){
                var ficha = fichas[i];
                console.log("Pais: ", ficha.pais);
                console.log("Valor: ", ficha.valorDatabase);
                var valorReal = ConverterValores(ficha.valorDatabase, ficha.moeda.sigla);
                somatorioFichas += valorReal;
                console.log("SOMATÓRIO: ", somatorioFichas);
                console.log("--------------------------");
             }
            return somatorioFichas;
            break;
            
        case "ficha18":
            var fichas =  PegarFichasByNum("18");
            console.log(fichas);
            for (var i = 0; i < fichas.length; i++){
                var ficha = fichas[i];
                console.log("Pais: ", ficha.pais);
                console.log("Valor: ", ficha.valorMercado);
                var valorReal = ConverterValores(ficha.valorMercado, ficha.moeda.sigla);
                somatorioFichas += valorReal;
                console.log("SOMATÓRIO: ", somatorioFichas);
                console.log("--------------------------");
            }
            return somatorioFichas;
            break;
            
            default :
                console.log("Opção inválida.");
    }
    
}

function getContabilByFicha(numFicha, info){
    console.log("ENTROU NO GET");
    console.log(numFicha);
    console.log(info);
    var contabil = PegarContabeis();
//    console.log(contabil);
    switch(numFicha){
        case "ficha1":
            
            break;
        case "ficha03":
            contabil = GetCosifByCosif("115400000" , contabil);
            console.log("getcontabil");
            console.log(contabil);
            return contabil;
            break;
        case "ficha08":
                //ADICIONAR COSIF AQUI
                contabil = GetCosifByCosif("126102000" , contabil);
                console.log("getcontabil");
                console.log(contabil);
                return contabil;
            break;
        case "ficha11":
            if(info === "maior"){
               contabil = 0;
               return contabil;
            }
            else if (info === "menor"){
                /////arrumar depois
                contabil = GetCosifByCosif("1.3.1.85.60.00-", contabil);
                return contabil;
            }
            else{
                    contabil = 0;
                    return contabil;
            }
            
            break;
        case "ficha16":
            contabil = GetCosifByCosif("1.8.8.90.00.00-" , contabil);
            console.log("getcontabil");
            console.log(contabil);
            return contabil;
            
            break;
            
        case "ficha18":
            contabil = GetCosifByCosif("131852000" , contabil);
            console.log("getcontabil");
            console.log(contabil);
            return contabil;
            
            break;
            default :
                console.log("Opção inválida.");
    }
    contabil = 0;
    return contabil;
}

var listaItens = []; 

$(document).ready(function() {

    // 1. Botão ADICIONAR ITEM À LISTA
    $('#btnAdicionar').click(function() {
        var paisId = $('#pais').val();
        var paisNome = $('#pais option:selected').text();
        var moedaId = $('#moeda').val();
        var moedaNome = $('#moeda option:selected').text();
        var valor = $('#valor').val();
        var dividendos = $('#dividendos').val();

        // Validação simples (Adicione mais se precisar)
        if (!paisId || !moedaId || !valor) {
            alert("Por favor, preencha País, Moeda e Valor.");
            return;
        }

        var item = {
            id_pais: paisId,
            nome_pais: paisNome,
            id_moeda: moedaId,
            nome_moeda: moedaNome,
            valor: valor,
            dividendos: dividendos
        };

        listaItens.push(item);
        atualizarTabela();
        limparCampos(); // Limpa os inputs para o próximo item
    });

    // 2. Botão SALVAR E ENVIAR FICHA (Processa o Lote)
    $('#btnFinalizarLote').click(function(e) {
        e.preventDefault();
        
        if (listaItens.length === 0) {
            alert("Adicione pelo menos um item à lista.");
            return;
        }

        // Envia para validação de soma no servidor
        $.ajax({
            type: "POST",
            url: "../ficha01", 
            data: JSON.stringify({
                "tipo-requisicao": "validar-lote",
                "itens": listaItens
            }),
            contentType: "application/json",
            dataType: "json",
            success: function(response) {
                if (response.precisaJustificar) {
                    $('#modalJustificativa').show();
                } else {
                    salvarLoteDefinitivo(""); // Salva direto sem justificativa
                }
            },
            error: function(xhr, status, error) {
                console.error("Erro validação:", error);
                alert("Erro ao validar os dados no servidor.");
            }
        });
    });

    // 3. Controles do Modal
    $('#btnConfirmarJustificativa').click(function() {
        var texto = $('#textoJustificativa').val().trim();
        if (texto === "") {
            alert("A justificativa é obrigatória.");
            return;
        }
        $('#modalJustificativa').hide();
        salvarLoteDefinitivo(texto);
    });

    $('#btnCancelarJustificativa').click(function() {
        $('#modalJustificativa').hide();
        $('#textoJustificativa').val('');
    });
});

// Atualiza o HTML da tabela
function atualizarTabela() {
    var tbody = $('#tabelaItens tbody');
    tbody.empty();
    
    listaItens.forEach(function(item, index) {
        // Caminho da imagem de lixo (ajuste se sua pasta resources for diferente)
        var iconeLixo = "<img src='../resources/imgs/lixovermelho.png' alt='Excluir' style='width:20px; height:20px; cursor:pointer;' title='Remover item'>";

        var tr = `<tr>
            <td>${item.nome_pais}</td>
            <td>${item.nome_moeda}</td>
            <td>${item.valor}</td>
            <td>${item.dividendos}</td>
            <td style="text-align:center;">
                <span onclick="removerItem(${index})">${iconeLixo}</span>
            </td>
        </tr>`;
        tbody.append(tr);
    });

    // Controla visibilidade do botão final
    if (listaItens.length > 0) {
        $('#areaBotaoFinal').show(); // Mostra a div que contém o botão
    } else {
        $('#areaBotaoFinal').hide();
    }
}

// Remove item do array e redesenha
function removerItem(index) {
    listaItens.splice(index, 1);
    atualizarTabela();
}

// Limpa apenas os inputs do formulário
function limparCampos() {
    $('#valor').val('');
    $('#dividendos').val('');
    // Opcional: Resetar selects para o padrão
    // $('#pais').val('');
    // $('#moeda').val('');
}

// Envio final para persistência
function salvarLoteDefinitivo(justificativa) {
    // Feedback visual simples
    $('body').css('cursor', 'wait');
    
    $.ajax({
        type: "POST",
        url: "../ficha01",
        data: JSON.stringify({
            "tipo-requisicao": "salvar-lote",
            "justificativa": justificativa,
            "itens": listaItens
        }),
        contentType: "application/json",
        success: function(response) {
            $('body').css('cursor', 'default');
            alert("Ficha enviada com sucesso!");
            
            // Redireciona para a tela de visualização ou limpa tudo
            if(response.redirectUrl) {
                window.location.href = "../" + response.redirectUrl; // Ajuste o caminho conforme o retorno
            } else {
                window.location.href = "../views/ficha01.jsp";
            }
        },
        error: function(xhr) {
            $('body').css('cursor', 'default');
            console.error("Erro salvamento:", xhr);
            alert("Erro ao salvar os dados. Tente novamente.");
        }
    });
}

function enviarFormulario(form) {
    // Remove o listener de click para evitar loop e submete
    form.off('submit').submit();
}