

document.addEventListener("DOMContentLoaded", function () {
//Pegando os ids dos forms, tanto da versão copia e cola quanto da versão antiga    
  const respostaInputs = document.querySelectorAll('input[name="resposta-participacao"]');
  const perguntasFormMenor = document.getElementById('formMenor');
  const perguntasFormMaior = document.getElementById('formMaior');
  const perguntasFormMaiorTeste = document.getElementById('FormCopiaColaMaior');
  const perguntasFormMenorTeste = document.getElementById('FormCopiaColaMenor');
  const sessaoUploadMenor = document.getElementById('sessaoUploadMenor');
//identifica quando for clicado maior que 10% ou menor que 10%
  respostaInputs.forEach(input => {
    input.addEventListener('click', function () {
//    Habilita apenas a versão copia e cola    
      if (input.value === 'menor-que-10') {
//        perguntasFormMenor.style.display = 'none';
//        perguntasFormMaior.style.display = 'none';
        perguntasFormMaiorTeste.style.display = 'none';
        perguntasFormMenorTeste.style.display = 'block';
        if(sessaoUploadMenor) sessaoUploadMenor.style.display = 'block';
      } else if (input.value === 'maior-que-10') {
//        perguntasFormMenor.style.display = 'none';
//        perguntasFormMaior.style.display = 'block';
        perguntasFormMaiorTeste.style.display = 'block';
        perguntasFormMenorTeste.style.display = 'none';
        if(sessaoUploadMenor) sessaoUploadMenor.style.display = 'none';
        
      } else {
        perguntasFormMenor.style.display = 'none';
        perguntasFormMaior.style.display = 'none';
        if(sessaoUploadMenor) sessaoUploadMenor.style.display = 'none';
      }
    });
  });


//var moedaSelecionada = document.getElementById('moeda');
//var moedaSelecionadaMenor = document.getElementById('moedaMenor');
//var elementosSimboloMoedas = document.getElementsByClassName('simbolo-moedas');
//var elementosSimboloMoedasMenores = document.getElementsByClassName('simbolo-moedas-menor');
//
//var moedas = {
//    '1': 'US$',
//    '2': 'AU$',
//    '3': 'C$',
//    '4': '€',
//    '5': '$',
//    '6': 'kr',
//    '9': 'kr',
//    '11': 'kr',
//    '7': '¥',
//    '8': '¥',
//    '10': '£',
//    '12': 'Fr',
//    '13': '₲',
//    '14': 'Bs',
//    '16': 'R$'
//};

//moedaSelecionada.addEventListener('change', function () {
//    var idMoedaSelecionada = moedaSelecionada.value;
//
//    // Itera sobre todos os elementos com a classe 'simbolo-moedas'
//    for (var i = 0; i < elementosSimboloMoedas.length; i++) {
//        var elemento = elementosSimboloMoedas[i];
//        elemento.textContent = moedas[idMoedaSelecionada] || '';
//        elemento.style.display = 'block';
//    }
//});
////ALTERAR PARA QUE AS FICHAS FIQUEM IDEPENDENTES
//moedaSelecionadaMenor.addEventListener('change', function () {
//    var idMoedaSelecionadaMenor = moedaSelecionadaMenor.value;
//
//    // Itera sobre todos os elementos com a classe 'simbolo-moedas'
//    for (var i = 0; i < elementosSimboloMoedasMenores.length; i++) {
//        var elemento = elementosSimboloMoedasMenores[i];
//        elemento.textContent = moedas[idMoedaSelecionadaMenor] || '';
//        elemento.style.display = 'block';
//    }
//});

});

    
let areaInputMaior = document.getElementById("areaInput11Maior");

let divFormularioMaior = document.getElementById("formulario11Maior");
let btnEnviarMaior = document.getElementById("controlaMaior");


let cabecalhoCoger = ["Empresa", "Esta empresa possui cotação em bolsa de valores no exterior?", "Moeda do país da empresa no exterior", "Método Valoração",
    "Valor da empresa na data-base", "Patrimônio líquido total na data-base", "Percentual de participação no capital social", 
    "Percentual de poder de voto", "Ativo na data-base", "Passível exigível na data-base", "Valor total do lucro ou prejuízo líquidos da empresa" + 
    " no exterior", "Resultado líquido de itens não recorrentes", "Resultado Líquido de reavaliações (ex. impairment):", "Resultado distribuído" +
    "no período-base", "Lucro distribuído no período-base", "A empresa controla outras empresas (detém 50% ou mais do poder de voto) , SIM ou NÃO?"];
    
let namesCoger = ["empresa", "cotacao", "moeda", "valoracao", "valorDataBase", "patrimonioLiquido", "porcentagemSocial",
    "porcentagemVoto", "ativoDataBase", "passivoExigivel", "valorTotal",   "resultadoLiquidoItens",
    "resultadoLiquidoReavaliacoes", "resultadoLiquidoCambial", "lucroDistribuido", "controla"];

let cabecalhoUpe = ["Empresa", "Esta empresa possui cotação em bolsa de valores no exterior?", "Moeda do país da empresa no exterior", 
    "Método Valoração", "Valor da empresa na data-base", "Patrimônio líquido total na data-base", "Percentual de participação no capital social", "Percentual de poder de voto",
    "Ativo na data-base", "Passível exigível na data-base", "Valor total do lucro ou prejuízo líquidos da empresa" + 
    " no exterior", "Resultado líquido de itens não recorrentes",  "Resultado Líquido de reavaliações (ex. impairment):", "Resultado distribuído" +
    "no período-base",
    "Lucro distribuído no período-base", "A empresa controla outras empresas (detém 50% ou mais do poder de voto) , SIM ou NÃO?"];
    
    
let namesUpe = ["empresa", "cotacao", "moeda", "valoracao", "valorDataBase", "patrimonioLiquido", "porcentagemSocial", "porcentagemVoto", "ativoDataBase", "passivoExigivel",
    "valorTotal", "resultadoLiquidoItens", "resultadoLiquidoReavaliacoes", "resultadoLiquidoCambial", "lucroDistribuido", "controla"];



if (divFormularioMaior.contains(btnEnviarMaior)) {
    // Retorna true apenas se o parentNode do "child" for o "parent"
   console.log("É FILHO");
}
console.log("NÃO É FILHO");

// Descobre quem é o pai
let pai = btnEnviarMaior.parentNode; // ou minhaDiv.parentElement

// Exibe o pai no console
console.log("O elemento pai é:", pai); 

//Função quando você cola as informações para maior que 10%
function clipMaior(event, diretoria) {
//  Habilita o botão de enviar  
    document.getElementById('submitButtonMaior').disabled = false;
    console.log(diretoria);
//  Se a diretoria for coger ele vai pegar o names e cabecalho coger, se não ele vai pegar names e cabecalho upe e vai trocar
//  o divFormulario para se adequar na função de mostrar a tabela depois de colar
    if (diretoria === "COGER"){
        names = namesCoger;
        cabecalho = cabecalhoCoger;
    }
    else{
        names = namesUpe;
        cabecalho = cabecalhoUpe;
        divFormularioMaior = document.getElementById("formulario11Maior");
//        divFormularioMaior = document.getElementById("formulario11Maior");
    }
//  Tira o text area para inserir a tabela no lugar  
    areaInputMaior.style.display = "none";
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

    divFormularioMaior.insertBefore(newTable, btnEnviarMaior);
    
    
}

function copiarTextoMaiorCoger() {

    let textoCopiar = "Empresa	Moeda do país da empresa no exterior"+
            "	Patrimônio                           líquido total na data-base	Percentual de participação no capital social	Percentual"+
            "                               de poder de voto	Ativo                             na"+
            "                                        data-base	Passível                          "+
            "exigível na data-base	Resultado líquido de itens não recorrentes	"+
            "Resultado Líquido de reavaliações (ex. impairment):	Resultado distribuídono período-base	"+
            "A empresa controla outras empresas (detém 50% ou mais do poder de voto) , SIM ou NÃO?";

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

function copiarTextoMaiorUpe() {

    let textoCopiar = "Empresa	Esta empresa possui cotação em bolsa de valores no exterior?	Método Valoração	Valor da"+
            "                                                    empresa na data-base	Resultado líquido de itens não recorrentes"+
            "	Lucro distribuído no período-base	A empresa controla outras empresas (detém 50% ou mais do poder de voto) , SIM ou NÃO?";

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

function copiarTextoMenor() {

    let textoCopiar = "País da Empresa no exterior	Moeda do país da empresa no exterior	Método Valoração	"+
            "Valor de Participação na Empresa na Data-Base	Lucro Distribuído ao Declarante";

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

function sendAllDataMaior(diretoria) {
//    const carrega = document.getElementById('gifCarregando');
//    carrega.style.display = 'flex';
    console.log(diretoria);
    let tipoRequisicao = document.getElementById('tipozada').value;
    console.log(tipoRequisicao);
    var contextPath = document.getElementById('contextPath').value;
    console.log(contextPath);
    var controla = false;
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
    data.push({"diretoria": diretoria});
    console.log(controla);
    $.ajax({
        url: contextPath + '/ficha11/maior',
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


let areaInputMenor = document.getElementById("areaInput11Menor");
let divFormularioMenor = document.querySelector(".formulario11Menor");
let btnEnviarMenor = document.getElementById("controlaMenor");
let cabecalhoMenor = ["País da Empresa no exterior", "Moeda do país da empresa no exterior", 
    "Método Valoração", "Valor de Participação na Empresa na Data-Base", 
    "Lucro Distribuído ao Declarante"];

let namesMenor = ["pais", "moeda", "valoracao", "valorParticipacao",
    "lucroDistribuido"];


function clipMenor(event) {
    document.getElementById('submitButtonMenor').disabled = false;
    names = namesMenor;
    cabecalho = cabecalhoMenor;
    areaInputMenor.style.display = "none";
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

    divFormularioMenor.insertBefore(newTable, btnEnviarMenor);
}

function sendAllDataMenor() {
//    const carrega = document.getElementById('gifCarregando');
//    carrega.style.display = 'flex';
    let tipoRequisicao = document.getElementById('tipozada').value;
    console.log(tipoRequisicao);
    var contextPath = document.getElementById('contextPath').value;
    console.log(contextPath);
    var data = [];
//  Pegando os dados do copia e cola  
    for (var i = 0; i < clipRows.length; i++) {
        var obj = {};
        for (var j = 0; j < names.length; j++) {
            obj[names[j]] = clipRows[i][j] || ''; // Usa '||' para lidar com valores undefined/null
        }
//      salvando no array data  
        data.push(obj);
    }
    console.log(data);
//  salvando o tipo de requisicao em data  
    data.push({"tipo-requisicao": tipoRequisicao}); // Corrigido aqui
    $.ajax({
        url: contextPath + '/ficha11/menor',
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


function colarNovamente() {
    window.location.reload();
    return false;
}

function mostrarTabela(tabelaId) {
    console.log("MOSTRANDO");
    const tabela = document.getElementById(tabelaId);
    tabela.style.display = (tabela.style.display === "none") ? "block" : "none";
}