var listaItens = []; 

$(document).ready(function() {
    console.log(">>> Ficha 16 JS Iniciado");

    // 1. Botão Adicionar
    $('#btnAdicionar').click(function(e) {
        e.preventDefault();

        var paisId = $('#pais').val();
        var paisNome = $('#pais option:selected').text();
        var moedaId = $('#moeda').val();
        var moedaNome = $('#moeda option:selected').text();
        
        // CORREÇÃO: Usando o ID 'direitos' que está no seu JSP
        var tipo = $('#direitos').val(); 
        var valor = $('#valor').val();

        if (!paisId || !moedaId || !valor || !tipo) {
            alert("Por favor, preencha todos os campos.");
            return;
        }

        var item = {
            id_pais: paisId,
            nome_pais: paisNome,
            id_moeda: moedaId,
            nome_moeda: moedaNome,
            tipo: tipo,
            valor: valor
        };

        listaItens.push(item);
        atualizarTabela();
        limparCampos();
    });

    // 2. Salvar Lote
    $('#btnFinalizarLote').click(function(e) {
        e.preventDefault();
        if (listaItens.length === 0) return;
        
        $('body').css('cursor', 'wait');

        $.ajax({
            type: "POST",
            url: "../ficha16", 
            data: JSON.stringify({
                "tipo-requisicao": "validar-lote",
                "itens": listaItens
            }),
            contentType: "application/json",
            dataType: "json",
            success: function(response) {
                $('body').css('cursor', 'default');
                if (response.precisaJustificar) {
                    $('#modalJustificativa').show();
                } else {
                    salvarLoteDefinitivo("");
                }
            },
            error: function(xhr) {
                $('body').css('cursor', 'default');
                console.error("Erro validação:", xhr);
                alert("Erro ao validar dados.");
            }
        });
    });

    // 3. Modal
    $('#btnConfirmarJustificativa').click(function(e) {
        var texto = $('#textoJustificativa').val().trim();
        if (texto === "") { alert("Justificativa obrigatória."); return; }
        $('#modalJustificativa').hide();
        salvarLoteDefinitivo(texto);
    });

    $('#btnCancelarJustificativa').click(function() {
        $('#modalJustificativa').hide();
    });
});

function atualizarTabela() {
    var tbody = $('#tabelaItens tbody');
    tbody.empty();
    
    listaItens.forEach(function(item, index) {
        var iconeLixo = "<img src='../resources/imgs/lixovermelho.png' alt='Excluir' style='width:20px; height:20px; cursor:pointer;' title='Remover'>";
        var tr = `<tr>
            <td>${item.nome_pais}</td>
            <td>${item.nome_moeda}</td>
            <td>${item.tipo}</td>
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
    $('#direitos').val(''); // Limpa o select #direitos
    $('#valor').val('');
}

function salvarLoteDefinitivo(justificativa) {
    $('body').css('cursor', 'wait');
    $.ajax({
        type: "POST",
        url: "../ficha16",
        data: JSON.stringify({
            "tipo-requisicao": "salvar-lote",
            "justificativa": justificativa,
            "itens": listaItens
        }),
        contentType: "application/json",
        success: function(response) {
            $('body').css('cursor', 'default');
            alert("Ficha 16 enviada com sucesso!");
            if(response.redirectUrl) window.location.href = "../" + response.redirectUrl;
            else window.location.href = "../views/ficha16.jsp";
        },
        error: function(xhr) {
            $('body').css('cursor', 'default');
            alert("Erro ao salvar.");
        }
    });
}