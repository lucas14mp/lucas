var listaItens = []; 

$(document).ready(function() {
    console.log(">>> Ficha 18 JS Iniciado");

    $('#btnAdicionar').click(function(e) {
        e.preventDefault();

        var paisId = $('#pais').val();
        var paisNome = $('#pais option:selected').text();
        var moedaId = $('#moeda').val();
        var moedaNome = $('#moeda option:selected').text();
        var prazo = $('input[name="resposta-prazo"]:checked').val();
        var valor = $('#valor').val(); 
        
        // Captura juros pelo id="juros"
        var juros = $('#juros').val();

        // Validação
        if (!paisId || !moedaId || !prazo || !valor || !juros) {
            alert("Por favor, preencha todos os campos:\n- País\n- Moeda\n- Prazo\n- Valor de Mercado\n- Juros");
            return;
        }

        // Cria o objeto Item (Mantendo as chaves que o Controller espera: valorMercado)
        var item = {
            id_pais: paisId,
            nome_pais: paisNome,
            id_moeda: moedaId,
            nome_moeda: moedaNome,
            prazo: prazo,           // "Até 12 meses" ou "Mais de 12 meses"
            valorMercado: valor,    // Enviamos o valor do input 'valor' como 'valorMercado' pro Java
            juros: juros
        };

        listaItens.push(item);
        atualizarTabela();
        limparCampos();
    });

    $('#btnFinalizarLote').click(function(e) {
        e.preventDefault();
        
        if (listaItens.length === 0) {
            alert("Adicione itens à lista antes de salvar.");
            return;
        }

        $('body').css('cursor', 'wait');

        // Envia para o Controller validar
        $.ajax({
            type: "POST",
            url: "../ficha18", 
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
                console.error(xhr);
                alert("Erro ao validar dados com o servidor.");
            }
        });
    });
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
        var iconeLixo = "<img src='../resources/imgs/lixovermelho.png' alt='Excluir' style='width:20px; height:20px; cursor:pointer;' title='Remover'>";
        
        var tr = `<tr>
            <td>${item.nome_pais}</td>
            <td>${item.nome_moeda}</td>
            <td>${item.prazo}</td>
            <td>${item.valorMercado}</td>
            <td>${item.juros}</td>
            <td style="text-align:center;"><span onclick="removerItem(${index})">${iconeLixo}</span></td>
        </tr>`;
        tbody.append(tr);
    });

    if (listaItens.length > 0) {
        $('#areaBotaoFinal').show();
    } else {
        $('#areaBotaoFinal').hide();
    }
}

function removerItem(index) {
    listaItens.splice(index, 1);
    atualizarTabela();
}

function limparCampos() {
    // Limpa os radio buttons pelo name correto
    $('input[name="resposta-prazo"]').prop('checked', false);
    
    // Limpa inputs de texto
    $('#valor').val('');
    $('#juros').val('');
    
    // Opcional: resetar selects (comentei para facilitar inserções repetidas)
    // $('#pais').val('');
    // $('#moeda').val('');
}

function salvarLoteDefinitivo(justificativa) {
    $('body').css('cursor', 'wait');
    
    $.ajax({
        type: "POST",
        url: "../ficha18",
        data: JSON.stringify({
            "tipo-requisicao": "salvar-lote",
            "justificativa": justificativa,
            "itens": listaItens
        }),
        contentType: "application/json",
        success: function(response) {
            $('body').css('cursor', 'default');
            alert("Ficha 18 enviada com sucesso!");
            
            if(response.redirectUrl) {
                window.location.href = "../" + response.redirectUrl;
            } else {
                window.location.href = "../views/ficha18.jsp";
            }
        },
        error: function(xhr) {
            $('body').css('cursor', 'default');
            console.error("Erro no salvamento:", xhr);
            alert("Erro ao salvar a ficha.");
        }
    });
}