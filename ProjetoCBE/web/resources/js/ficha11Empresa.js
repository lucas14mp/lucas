let areaInput = document.getElementById("areaInput11");
let divFormulario = document.querySelector(".formulario11");
let btnEnviar = document.getElementById("controla");
let cabecalho = ["Nome da empresa controlada", "País da empresa controlada", "Atividade econômica principal", 
    "Percentual de participação no capital social", "Moeda", "Patrimônio líquido total na data-base", "Valor de mercado na data-base", "A empresa está ao final da cadeia de controle?"];
let names = ["nome", "pais", "atividade", 
    "porcentagemSocial", "moeda", "patrimonioLiquido", "valorMercado", "cadeia"];


function clip(event) {
    document.getElementById('submitButton').disabled = false;
    areaInput.style.display = "none";
    // get the clipboard text
    var dadosClipboard = event.clipboardData || window.clipboardData;
    var clipText = dadosClipboard.getData('Text');
    var newTable = document.createElement("table");

    // split into rows
    clipRows = clipText.split(/\r?\n/).filter(function (entry) {
        return entry.trim() !== '';
    });

    // split rows into columns
    for (var i = 0; i < clipRows.length; i++) {
        clipRows[i] = clipRows[i].replace(/\n/g, '').split("\t");
    }

    // write out in a table
    newTable.classList.add("view11");
    // Adiciona o cabeçalho à tabela
    var headerRow = newTable.insertRow();
    for (var i = 0; i < cabecalho.length; i++) {
        var headerCell = headerRow.insertCell();
        headerCell.innerText = cabecalho[i];
        headerCell.classList.add("cabecalho");
    }

    // Adiciona as linhas de dados à tabela
    for (var i = 0; i < clipRows.length; i++) {
        var newRow = newTable.insertRow();
        for (var j = 0; j < clipRows[i].length; j++) {
            var newCell = newRow.insertCell();
            newCell.setAttribute('name', names[j] + i);  // define um atributo 'name' para a célula
            if (clipRows[i][j].length === 0) {
                newCell.innerText = names[j] === "valoracao" ? 'Não informado' : 'Não informado';
            } else {
                newCell.innerText = clipRows[i][j];
            }
        }
        if (i % 2 === 0) {
            newRow.style.backgroundColor = "#F0F8FF";
        } else {
            newRow.style.backgroundColor = "#B0E0E6";
        }
    }

    newTable.style.borderCollapse = "collapse";
    newTable.style.border = "none";
    newTable.style.width = "100%";
    var cells = newTable.getElementsByTagName("td");
    for (var i = 0; i < cells.length; i++) {
        cells[i].style.border = "none";
    }

    divFormulario.insertBefore(newTable, btnEnviar);
    
    
}

function colarNovamente() {
    window.location.reload();
    return false;
}

function mostrarTabela(tabelaId) {
    const tabela = document.getElementById(tabelaId);
    tabela.style.display = (tabela.style.display === "none") ? "block" : "none";
}

function copiarTexto() {

    let textoCopiar = "Nome	País da empresa controlada	Atividade econômica principal	Percentual de participação no capital social	Moeda	Patrimônio líquido total na data-base	Valor de mercado na data-base	A empresa está ao final da cadeia de controle?";

    // Cria um elemento de texto temporário
    var tempElement = document.createElement("textarea");
    tempElement.value = textoCopiar;
    document.body.appendChild(tempElement);
    
    // Seleciona o texto no elemento temporário
    tempElement.select();
    tempElement.setSelectionRange(0, 99999); // Para dispositivos móveis

    // Copia o texto para a área de transferência
    document.execCommand("copy");

    // Remove o elemento temporário
    document.body.removeChild(tempElement);

    var mensagemDiv = document.getElementById("copiarConteudo11");

    // Adiciona a classe fade-out para iniciar a animação de fade out
    mensagemDiv.classList.add("fade-out");

    // Aguarda a duração da animação de fade out (0.5s) antes de mudar a mensagem
    setTimeout(function() {
        mensagemDiv.textContent = "Conteúdo copiado";
        
        // Remove a classe fade-out e adiciona a classe fade-in para iniciar a animação de fade in
        mensagemDiv.classList.remove("fade-out");
        mensagemDiv.classList.add("fade-in");
    }, 500);

    mensagemDiv.classList.remove("fade-in");
    
}

function sendAllData() {
//    const carrega = document.getElementById('gifCarregando');
//    carrega.style.display = 'flex';
    let tipoRequisicao = document.getElementById('tipozada').value;
    console.log(tipoRequisicao);
    var contextPath = document.getElementById('contextPath').value;
    console.log(contextPath);
    var idControladora = document.getElementById('idControlada').value;
    console.log(idControladora);
    var controla = false;
    const radios = document.getElementsByName('controla');
        for (const radio of radios) {
            if (radio.checked) {
                controla = radio.value;
                console.log("ESTÁ CONTROLANDO: " + controla);
                break;
            }
        }
//    if (controla === "Sim"){
//        controla = true;
//    }
//    else if (controla === "Não"){
//        controla = false;
//    }
//    else{
//        controla = false;
//    } 
    var data = [];
    for (var i = 0; i < clipRows.length; i++) {
        var obj = {};
        for (var j = 0; j < names.length; j++) {
            obj[names[j]] = clipRows[i][j] || ''; // Usa '||' para lidar com valores undefined/null
        }
        data.push(obj);
    }
    console.log(data);
    data.push({"tipo-requisicao": tipoRequisicao}); // Corrigido aqui
    data.push({"id-controladora": idControladora});
//    data.push({"controla": controla});
//        data.push({"diretoria": diretoria});
//    console.log(controla);
    // data.push({"idHeader": idHeader});
    $.ajax({
        url: contextPath + '/ficha11Empresa',
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (response) {
    //        console.log('Success: ', response);
            if (response.redirectUrl) {
                window.location.href = response.redirectUrl;
            } else {
                alert("Erro ao redirecionar. URL de redirecionamento não fornecida.");
            }
        },
        error: function (error) {
    //        carrega.style.display = 'none';
            console.error('Erro:', error);
            alert("Erro ao enviar as informações. Por favor, verifique os dados e envie novamente.");
        }
    });
       
}

