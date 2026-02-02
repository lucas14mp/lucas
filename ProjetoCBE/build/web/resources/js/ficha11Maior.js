var listaItensMaior = [];

$(document).ready(function() {
    console.log(">>> Ficha 11 Maior JS Iniciado");

    // 1. Controle Visual do Input
    $(document).on('change', '#arquivoExcelMaior', function() {
        if ($(this).val()) $('#btnRemoverMaior').show();
        else $('#btnRemoverMaior').hide();
    });

    $(document).on('click', '#btnRemoverMaior', function() {
        $('#arquivoExcelMaior').val('');
        $(this).hide();
    });

    // 2. Upload AJAX
    $(document).on('submit', '#formUploadExcelMaior', function(e) {
        e.preventDefault();
        
        var input = $('#arquivoExcelMaior');
        if (!input.val()) { alert('Selecione um arquivo.'); return; }

        var formData = new FormData(this);
        var btn = $(this).find('button[type="submit"]');
        
        btn.prop('disabled', true).text('Enviando...');
        $('body').css('cursor', 'wait');

        $.ajax({
            url: $(this).attr('action'),
            type: 'POST',
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            dataType: 'json',
            success: function(response) {
                $('body').css('cursor', 'default');
                btn.prop('disabled', false).html('<i class="bi bi-cloud-upload"></i> Processar');
                
                if (response.erro) {
                    alert("Erro: " + response.erro);
                } else {
                    response.forEach(function(item) { listaItensMaior.push(item); });
                    atualizarTabelaMaior();
                    alert("Sucesso! Verifique a tabela.");
                    input.val('');
                    $('#btnRemoverMaior').hide();
                }
            },
            error: function(xhr) {
                $('body').css('cursor', 'default');
                btn.prop('disabled', false);
                alert("Erro ao processar arquivo.");
            }
        });
    });

    // 3. Salvar Lote
    $('#btnFinalizarLoteMaior').click(function(e) {
        e.preventDefault();
        if (listaItensMaior.length === 0) { alert("Lista vazia."); return; }
        
        var isUpe = $('#selectUpe').val() === 'sim';
        
        $('body').css('cursor', 'wait');
        $.ajax({
            type: "POST",
            url: "../ficha11/maior", // Ajuste se seu servlet mapear diferente
            data: JSON.stringify({
                "tipo-requisicao": "salvar-lote",
                "itens": listaItensMaior,
                "flagUpe": isUpe
            }),
            contentType: "application/json",
            success: function() {
                $('body').css('cursor', 'default');
                alert("Ficha 11 Maior salva com sucesso!");
                window.location.reload();
            },
            error: function(xhr) {
                $('body').css('cursor', 'default');
                alert("Erro ao salvar: " + xhr.responseText);
            }
        });
    });
});

function atualizarTabelaMaior() {
    var tbody = $('#tabelaItensMaior tbody');
    tbody.empty();
    listaItensMaior.forEach(function(item, index) {
        var tr = `<tr>
            <td>${item.nome_empresa}</td>
            <td>${item.nome_moeda}</td>
            <td>${item.patrimonio_liquido}</td>
            <td>${item.percentual_capital}%</td>
            <td>${item.ativo}</td>
            <td style="text-align:center;"><span onclick="removerItemMaior(${index})" style="cursor:pointer;color:red;">X</span></td>
        </tr>`;
        tbody.append(tr);
    });
    if(listaItensMaior.length > 0) $('#areaBotaoFinalMaior').show();
}

function removerItemMaior(index) {
    listaItensMaior.splice(index, 1);
    atualizarTabelaMaior();
}