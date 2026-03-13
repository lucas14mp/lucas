var listaItensMaior = [];

$(document).ready(function() {
    console.log(">>> Ficha 11 Maior JS Iniciado");

    // --- NOVO: ATUALIZA A TABELA EM TEMPO REAL AO MUDAR O SELECT DA UPE ---
    $(document).on('change', '#selectUpe', function() {
        if (listaItensMaior.length > 0) {
            atualizarTabelaMaior();
        }
    });
    // ----------------------------------------------------------------------

    // --- 1. CONTROLE VISUAL DO INPUT DE ARQUIVO ---
    $(document).on('change', '#arquivoExcelMaior', function() {
        if ($(this).val()) {
            $('#btnRemoverMaior').show();
        } else {
            $('#btnRemoverMaior').hide();
        }
    });

    $(document).on('click', '#btnRemoverMaior', function() {
        $('#arquivoExcelMaior').val('');
        $(this).hide();
    });

    // --- 2. UPLOAD DO EXCEL MAIOR (Processamento de Leitura) ---
    $(document).on('submit', '#formUploadExcelMaior', function(e) {
        e.preventDefault(); 
        
        var inputArquivo = $('#arquivoExcelMaior');
        var nomeArquivo = inputArquivo.val();

        if (!nomeArquivo) {
            alert('Por favor, selecione um arquivo.');
            return;
        }
        if (!nomeArquivo.toLowerCase().endsWith('.xlsx') && !nomeArquivo.toLowerCase().endsWith('.xls')) {
            alert('Por favor, selecione um arquivo Excel válido (.xlsx ou .xls).');
            return;
        }

        var formData = new FormData(this);
        var btnUpload = $(this).find('button[type="submit"]');
        var actionUrl = $(this).attr('action'); 

        btnUpload.prop('disabled', true).text('Processando...');
        $('body').css('cursor', 'wait');

        $.ajax({
            url: actionUrl, 
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
                } else if (Array.isArray(response)) {
                    response.forEach(function(item) {
                        listaItensMaior.push(item);
                    });
                    
                    // Desenha a tabela com os dados
                    atualizarTabelaMaior();
                    alert("Leitura concluída! " + response.length + " registros carregados na tabela.");
                    
                    inputArquivo.val('');
                    $('#btnRemoverMaior').hide();
                } else {
                    alert("Erro: Resposta inválida do servidor.");
                }
            },
            error: function(xhr) {
                $('body').css('cursor', 'default');
                btnUpload.prop('disabled', false).html('<i class="bi bi-cloud-upload"></i> Processar Arquivo');
                console.error("Erro Upload Maior:", xhr);
                alert("Erro ao processar arquivo. Verifique o console para detalhes.");
            }
        });
    });

    // --- 3. SALVAR E ENVIAR O LOTE FINAL (Gravação no Banco) ---
    $('#btnFinalizarLoteMaior').click(function(e) {
        e.preventDefault();
        
        if (listaItensMaior.length === 0) {
            alert("A lista está vazia. Faça o upload de uma planilha primeiro.");
            return;
        }
        
        // Validação obrigatória da UPE na hora de salvar
        var selectUpe = $('#selectUpe').val();
        if (!selectUpe) {
            alert('Por favor, selecione acima se pertence à Diretoria UPE antes de salvar.');
            $('#selectUpe').focus(); 
            return; 
        }
        
        var isUpe = (selectUpe === 'sim');
        
        $('body').css('cursor', 'wait');

        $.ajax({
            type: "POST",
            url: "../ficha11/maior", 
            data: JSON.stringify({
                "tipo-requisicao": "salvar-lote",
                "itens": listaItensMaior,
                "flagUpe": isUpe
            }),
            contentType: "application/json",
            success: function(response) {
                $('body').css('cursor', 'default');
                alert("Ficha 11 Maior enviada com sucesso!");
                window.location.href = "../views/ficha11.jsp?msg=SucessoMaior";
            },
            error: function(xhr) {
                $('body').css('cursor', 'default');
                var msg = xhr.responseText || "Erro desconhecido";
                console.error(xhr);
                alert("Erro ao salvar: " + msg);
            }
        });
    });
});

// --- FUNÇÕES AUXILIARES ---

function atualizarTabelaMaior() {
    var tbody = $('#tabelaItensMaior tbody');
    tbody.empty();
    
    // Verifica em tempo real qual opção está selecionada no select
    var isUpe = $('#selectUpe').val() === 'sim';

    function f(val) {
        if (val === null || val === undefined || val === '' || val === "null") {
            return "-";
        }
        return parseFloat(val).toLocaleString('pt-BR', {minimumFractionDigits: 2, maximumFractionDigits: 2});
    }
    
    function b(val) {
        if (val === null || val === undefined || val === '') return "-";
        if (typeof val === 'string') return val.toUpperCase();
        return (val === true || val === 1) ? "SIM" : "NÃO";
    }

    listaItensMaior.forEach(function(item, index) {
        
        // Se for UPE, exibe mensagem cinza. Senão, mostra o valor normal.
        var valEmpresa = isUpe ? "<span style='color:#888; font-style:italic;'>Pela COGER</span>" : f(item.valor_empresa);
        var patrimTotal = isUpe ? "<span style='color:#888; font-style:italic;'>Pela COGER</span>" : f(item.patrimonio_total);
        var ativo = isUpe ? "<span style='color:#888; font-style:italic;'>Pela COGER</span>" : f(item.ativo_database);
        var passivo = isUpe ? "<span style='color:#888; font-style:italic;'>Pela COGER</span>" : f(item.passivo_exigivel);
        var lucro = isUpe ? "<span style='color:#888; font-style:italic;'>Pela COGER</span>" : f(item.valor_total_lucro_preju_liquido);
        var itensNR = isUpe ? "<span style='color:#888; font-style:italic;'>Pela COGER</span>" : f(item.result_liq_itens_nao_recorrentes);
        var reaval = isUpe ? "<span style='color:#888; font-style:italic;'>Pela COGER</span>" : f(item.result_liq_reavaliacoes);
        var varCamb = isUpe ? "<span style='color:#888; font-style:italic;'>Pela COGER</span>" : f(item.result_liq_variacao_cambial);
        var lucroDist = isUpe ? "<span style='color:#888; font-style:italic;'>Pela COGER</span>" : f(item.lucro_distribuido);

        var tr = `<tr>
            <td style="text-align: left; white-space: nowrap;">${item.nome_empresa || ''}</td>
            <td>${b(item.possui_cotacao_em_bolsa)}</td>
            <td>${item.nome_moeda || ''}</td>
            <td style="white-space: nowrap;">${item.metodo_valoracao || ''}</td>
            <td>${b(item.controla_empresas)}</td>
            <td>${valEmpresa}</td>
            <td>${patrimTotal}</td>
            <td>${f(item.participacao_capital_social)}%</td>
            <td>${f(item.porcento_poder_voto)}%</td>
            <td>${ativo}</td>
            <td>${passivo}</td>
            <td>${lucro}</td>
            <td>${itensNR}</td>
            <td>${reaval}</td>
            <td>${varCamb}</td>
            <td>${lucroDist}</td>
            
            <td style="text-align:center; position: sticky; right: 0; background: #fff; box-shadow: -2px 0 5px rgba(0,0,0,0.05);">
                <span onclick="removerItemMaior(${index})" style="cursor:pointer; color:red; font-weight:bold; font-size: 1.2em;" title="Remover item">X</span>
            </td>
        </tr>`;
        tbody.append(tr);
    });

    if (listaItensMaior.length > 0) {
        $('#areaBotaoFinalMaior').show();
        $('#tabelaItensMaior').parent().css('overflow-x', 'auto');
    } else {
        $('#areaBotaoFinalMaior').hide();
    }
}

function removerItemMaior(index) {
    if(confirm("Deseja realmente remover este item da lista?")) {
        listaItensMaior.splice(index, 1);
        atualizarTabelaMaior();
    }
}