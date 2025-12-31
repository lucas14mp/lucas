var listaItens = []; 

$(document).ready(function() {

    // Adicionar Item
    $('#btnAdicionar').click(function() {
        // Ficha 03: Apenas Moeda e Valor
        var moedaId = $('#moeda').val();
        var moedaNome = $('#moeda option:selected').text();
        var valor = $('#valor').val();

        if (!moedaId || !valor) {
            alert("Por favor, preencha Moeda e Valor.");
            return;
        }

        var item = {
            id_moeda: moedaId,
            nome_moeda: moedaNome,
            valor: valor
        };

        listaItens.push(item);
        atualizarTabela();
        limparCampos();
    });

    // Salvar e Enviar
    $('#btnFinalizarLote').click(function(e) {
        e.preventDefault();
        if (listaItens.length === 0) return;

        $.ajax({
            type: "POST",
            url: "../ficha03", 
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
                    salvarLoteDefinitivo("");
                }
            },
            error: function(xhr) {
                console.error(xhr);
                alert("Erro ao validar Ficha 03.");
            }
        });
    });

    // Modal Actions
    $('#btnConfirmarJustificativa').click(function() {
        var texto = $('#textoJustificativa').val().trim();
        if (texto === "") {
            alert("Justificativa obrigatória.");
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

function atualizarTabela() {
    var tbody = $('#tabelaItens tbody');
    tbody.empty();
    
    listaItens.forEach(function(item, index) {
        var iconeLixo = "<img src='../resources/imgs/lixovermelho.png' alt='Excluir' style='width:20px; height:20px; cursor:pointer;'>";
        var tr = `<tr>
            <td>${item.nome_moeda}</td>
            <td>${item.valor}</td>
            <td style="text-align:center;"><span onclick="removerItem(${index})">${iconeLixo}</span></td>
        </tr>`;
        tbody.append(tr);
    });

    if (listaItens.length > 0) $('#areaBotaoFinal').show();
    else $('#areaBotaoFinal').hide();
}

function removerItem(index) {
    listaItens.splice(index, 1);
    atualizarTabela();
}

function limparCampos() {
    $('#valor').val('');
    // Reseta select se quiser: $('#moeda').val('');
}

function salvarLoteDefinitivo(justificativa) {
    $('body').css('cursor', 'wait');
    $.ajax({
        type: "POST",
        url: "../ficha03",
        data: JSON.stringify({
            "tipo-requisicao": "salvar-lote",
            "justificativa": justificativa,
            "itens": listaItens
        }),
        contentType: "application/json",
        success: function(response) {
            $('body').css('cursor', 'default');
            alert("Ficha 03 enviada com sucesso!");
            if(response.redirectUrl) window.location.href = "../" + response.redirectUrl;
            else window.location.href = "../views/ficha03.jsp";
        },
        error: function(xhr) {
            $('body').css('cursor', 'default');
            alert("Erro ao salvar Ficha 03.");
        }
    });
}