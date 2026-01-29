var listaItens = []; 

$(document).ready(function() {
    console.log(">>> Ficha 11 Menor JS (Externo) Iniciado");

    // --- 1. CONTROLE VISUAL DO INPUT DE ARQUIVO ---
    $(document).on('change', '#arquivoExcel', function() {
        if ($(this).val()) {
            $('#btnRemover').show();
        } else {
            $('#btnRemover').hide();
        }
    });

    $(document).on('click', '#btnRemover', function() {
        $('#arquivoExcel').val(''); 
        $(this).hide(); 
    });

    // --- 2. INTERCEPTAR O ENVIO DO EXCEL (UPLOAD) ---
    // Usamos $(document).on('submit'...) para garantir que funcione mesmo em elementos dinâmicos
    $(document).on('submit', '#formUploadExcel', function(e) {
        e.preventDefault(); // <--- ISTO QUE IMPEDE A TELA BRANCA COM JSON
        
        console.log("Submit interceptado via AJAX");
        
        var inputArquivo = $('#arquivoExcel');
        var nomeArquivo = inputArquivo.val();
        var form = $(this); // O próprio formulário
        var actionUrl = form.attr('action'); // Pega a URL do action do JSP

        // Validações
        if (!nomeArquivo) {
            alert('Por favor, selecione um arquivo.');
            return;
        }
        if (!nomeArquivo.toLowerCase().endsWith('.xlsx') && !nomeArquivo.toLowerCase().endsWith('.xls')) {
            alert('Por favor, selecione um arquivo Excel válido (.xlsx ou .xls).');
            return;
        }

        var formData = new FormData(this);
        var btnUpload = form.find('button[type="submit"]');
        
        btnUpload.prop('disabled', true).text('Processando...');
        $('body').css('cursor', 'wait');

        $.ajax({
            url: actionUrl, // Usa a URL definida no JSP
            type: 'POST',
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            dataType: 'json',
            success: function(response) {
                $('body').css('cursor', 'default');
                btnUpload.prop('disabled', false).html('<i class="bi bi-cloud-upload"></i> Processar Arquivo');
                
                if (response.erro) {
                    alert("Erro no upload: " + response.erro);
                } else {
                    if (Array.isArray(response)) {
                        // Adiciona novos itens à lista existente
                        response.forEach(function(item) {
                            listaItens.push(item);
                        });
                        atualizarTabela();
                        alert("Dados importados com sucesso! Verifique a tabela abaixo.");
                        
                        // Limpa o campo
                        inputArquivo.val('');
                        $('#btnRemover').hide();
                    } else {
                        console.error("Formato inesperado:", response);
                        alert("Erro: O servidor não retornou uma lista válida.");
                    }
                }
            },
            error: function(xhr, status, error) {
                $('body').css('cursor', 'default');
                btnUpload.prop('disabled', false).html('<i class="bi bi-cloud-upload"></i> Processar Arquivo');
                console.error("Erro AJAX:", error);
                console.log("Resposta:", xhr.responseText);
                alert("Erro ao processar arquivo. Verifique o console.");
            }
        });
    });

    // --- 3. SALVAR LOTE ---
    $('#btnFinalizarLote').click(function(e) {
        e.preventDefault();
        if (listaItens.length === 0) {
            alert("A lista está vazia.");
            return;
        }
        
        $('body').css('cursor', 'wait');

        // Pega o contextPath de algum input hidden se necessário, ou usa relativo
        // Assumindo que o controller principal responde em ../ficha11/menor
        $.ajax({
            type: "POST",
            url: "../ficha11/menor", 
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

    // --- 4. MODAL ---
    $('#btnConfirmarJustificativa').click(function() {
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
        
        // Garante formatação numérica simples
        var valPart = item.valor_participacao !== undefined ? parseFloat(item.valor_participacao) : 0;
        var lucDist = item.lucro_distribuido !== undefined ? parseFloat(item.lucro_distribuido) : 0;

        var tr = `<tr>
            <td>${item.nome_pais}</td>
            <td>${item.nome_moeda}</td>
            <td>${item.metodo}</td>
            <td>${valPart.toLocaleString('pt-BR', {minimumFractionDigits: 2})}</td>
            <td>${lucDist.toLocaleString('pt-BR', {minimumFractionDigits: 2})}</td>
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

function salvarLoteDefinitivo(justificativa) {
    $('body').css('cursor', 'wait');
    $.ajax({
        type: "POST",
        url: "../ficha11/menor",
        data: JSON.stringify({
            "tipo-requisicao": "salvar-lote",
            "justificativa": justificativa,
            "itens": listaItens
        }),
        contentType: "application/json",
        success: function(response) {
            $('body').css('cursor', 'default');
            alert("Ficha 11 Menor enviada com sucesso!");
            window.location.href = "../views/ficha11.jsp?msg=Sucesso";
        },
        error: function(xhr, status, error) {
            $('body').css('cursor', 'default');
            console.error("Detalhes do erro:", xhr);
            
            // AQUI ESTÁ O SEGREDO: xhr.responseText contém a mensagem que você escreveu no Java
            var mensagemErro = xhr.responseText;
            
            if (!mensagemErro) {
                mensagemErro = "Erro desconhecido ao salvar (Status: " + xhr.status + ")";
            }
            
            alert(mensagemErro); 
        }
    });
}