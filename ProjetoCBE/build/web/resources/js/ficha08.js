var rowCount = 1;

function adicionarLinha() {
    var table = document.querySelector("table");
    // verifica se a tabela existe antes de tentar acessar rows
    if (!table) return;
    
    var lastRow = table.rows[table.rows.length - 1];
    if (lastRow) {
        var inputs = lastRow.getElementsByTagName("input");
        var inputValid = true;

        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].value === "") {
                inputValid = false;
                break;
            }
        }

        if (inputValid) {
            var newRow = table.insertRow(table.rows.length);
            var cell1 = newRow.insertCell(0);
            var cell2 = newRow.insertCell(1);
            var cell3 = newRow.insertCell(2);
            var cell4 = newRow.insertCell(3);
            var cell5 = newRow.insertCell(4);
            cell1.innerHTML = "<input type='text' placeholder='Informe aqui!' >";
            cell2.innerHTML = "<input type='text' placeholder='Informe aqui!' >";
            cell3.innerHTML = "<input type='number' step='0.01' placeholder='Informe aqui!' >";
            cell4.innerHTML = "<input type='number' step='0.01' placeholder='Informe aqui!' >";
            cell5.innerHTML = "<button type= 'button' class='btn-excluir' onclick='removerLinha(this)'>X</button>";
            rowCount++;
            if (rowCount % 2 === 0) {
                cell1.className = "row-dark";
                cell2.className = "row-dark";
                cell3.className = "row-dark";
                cell4.className = "row-dark";
                cell5.className = "transparent-col";
            } else {
                cell1.className = "row-light";
                cell2.className = "row-light";
                cell3.className = "row-light";
                cell4.className = "row-light";
                cell5.className = "transparent-col";
            }
        } else {
            alert("Preencha a linha atual antes de adicionar uma nova.");
        }
    }
}

function removerLinha(button) {
    var row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
}

var formularioAntigo = document.getElementById("myForm");
if (formularioAntigo) {
    formularioAntigo.addEventListener("submit", function (event) {
        var table = document.querySelector("table");
        var rows = table.rows;
        var isValid = true;

        for (var i = 1; i < rows.length; i++) {
            var inputs = rows[i].getElementsByTagName("input");
            for (var j = 0; j < inputs.length; j++) {
                if (inputs[j].value === "") {
                    isValid = false;
                    break;
                }
            }
        }

        if (!isValid) {
            alert("Preencha todas as linhas da tabela antes de enviar o formulário.");
            event.preventDefault();
        }
    });
}

var listaItens = []; 

$(document).ready(function() {
    console.log(">>> Ficha 08 JS Iniciado com sucesso");

    // --- 1. BOTÃO ADICIONAR ---
    $('#btnAdicionar').click(function(e) {
        e.preventDefault(); // Evita recarregar a página se estiver dentro de um form

        var paisId = $('#pais').val();
        var paisNome = $('#pais option:selected').text();
        var moedaId = $('#moeda').val();
        var moedaNome = $('#moeda option:selected').text();
        var valor = $('#valor').val();
        
        // ID correto da Ficha 08
        var rendimentos = $('#rendimentos').val(); 

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
            rendimentos: rendimentos 
        };

        listaItens.push(item);
        atualizarTabela();
        limparCampos();
    });

    // --- 2. BOTÃO SALVAR E ENVIAR ---
    $('#btnFinalizarLote').click(function(e) {
        e.preventDefault();
        
        if (listaItens.length === 0) {
            alert("Adicione itens à lista antes de salvar.");
            return;
        }

        $('body').css('cursor', 'wait');

        $.ajax({
            type: "POST",
            url: "../ficha08", 
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
                console.error("Erro validacao:", xhr);
                alert("Erro de comunicação com o servidor.");
            }
        });
    });

    // --- 3. MODAL ---
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
    // Garante que pegue apenas o corpo da tabela de itens (caso haja outras tabelas)
    var tbody = $('#tabelaItens tbody');
    tbody.empty();
    
    listaItens.forEach(function(item, index) {
        // Caminho da imagem (ajuste se necessário)
        var iconeLixo = "<img src='../resources/imgs/lixovermelho.png' alt='Excluir' style='width:20px; height:20px; cursor:pointer;' title='Remover item'>";

        var tr = `<tr>
            <td>${item.nome_pais}</td>
            <td>${item.nome_moeda}</td>
            <td>${item.valor}</td>
            <td>${item.rendimentos}</td>
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
    $('#valor').val('');
    $('#rendimentos').val('');
}

function salvarLoteDefinitivo(justificativa) {
    $('body').css('cursor', 'wait');
    $.ajax({
        type: "POST",
        url: "../ficha08",
        data: JSON.stringify({
            "tipo-requisicao": "salvar-lote",
            "justificativa": justificativa,
            "itens": listaItens
        }),
        contentType: "application/json",
        success: function(response) {
            $('body').css('cursor', 'default');
            alert("Ficha 08 enviada com sucesso!");
            if(response.redirectUrl) window.location.href = "../" + response.redirectUrl;
            else window.location.href = "../views/ficha08.jsp";
        },
        error: function(xhr) {
            $('body').css('cursor', 'default');
            console.error(xhr);
            alert("Erro ao salvar.");
        }
    });
}