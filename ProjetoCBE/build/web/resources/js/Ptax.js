let areaInput = document.getElementById("areaInput");
let divFormulario = document.querySelector(".formulario");
let btnEnviar = document.getElementById("controla");
let cabecalho = ["Sigla ", "Compra", "Venda"];
let names = ["sigla", "compra", "venda"];



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

    let textoCopiar = "Sigla	Moeda 	Compra 	Venda";

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
    setTimeout(function () {
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
//    data.push({"controla": controla});
//        data.push({"diretoria": diretoria});
//    console.log(controla);
    // data.push({"idHeader": idHeader});
    $.ajax({
        url: contextPath + '/Ptax',
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


    (function () {
        const cards = document.querySelectorAll('.option-card');

        cards.forEach(card => {
            // Foco via teclado = mesmo visual do hover
            card.addEventListener('focus', () => card.classList.add('is-hover'));
            card.addEventListener('blur', () => card.classList.remove('is-hover'));

            // Ripple suave no clique
            card.style.position = 'relative';
            card.style.overflow = 'hidden';

            card.addEventListener('click', function (e) {
                const ripple = document.createElement('span');
                const rect = this.getBoundingClientRect();
                const size = Math.max(rect.width, rect.height);
                const x = e.clientX - rect.left - size / 2;
                const y = e.clientY - rect.top - size / 2;

                ripple.style.position = 'absolute';
                ripple.style.left = x + 'px';
                ripple.style.top = y + 'px';
                ripple.style.width = ripple.style.height = size + 'px';
                ripple.style.borderRadius = '50%';
                ripple.style.background = 'rgba(31,77,216,0.15)';
                ripple.style.transform = 'scale(0)';
                ripple.style.transition = 'transform 420ms ease, opacity 520ms ease';
                ripple.style.pointerEvents = 'none';
                this.appendChild(ripple);

                requestAnimationFrame(() => {
                    ripple.style.transform = 'scale(1.2)';
                    ripple.style.opacity = '0';
                });

                setTimeout(() => ripple.remove(), 600);
            }, {passive: true});
        });
    })();

    // Variável global para armazenar os dados convertidos do JSON
    var taxasData = [];

// Executa quando a página termina de carregar
    document.addEventListener("DOMContentLoaded", function () {
        inicializarPtax();
    });

    function inicializarPtax() {
        console.log("Iniciando scripts PTAX...");

        // 1. Tenta ler a variável global 'taxasJson' que foi definida no JSP
        if (typeof taxasJson !== 'undefined' && taxasJson) {
            try {
                taxasData = JSON.parse(taxasJson);
            } catch (e) {
                console.error("Erro ao ler JSON de taxas", e);
            }
        } else {
            console.warn("Variável 'taxasJson' vazia ou não definida no JSP.");
        }

        // 2. Calcula as conversões na tela
        calcularConversoes();
    }

    function calcularConversoes() {
        console.log("Calculando conversões com base no período selecionado...");

        var linhas = document.querySelectorAll('.linha-ficha');

        linhas.forEach(function (linha) {
            var moedaSigla = linha.getAttribute('data-moeda');
            var saldoTexto = linha.getAttribute('data-saldo');

            var tdTaxa = linha.querySelector('.taxa');
            var tdConversao = linha.querySelector('.conversao');

            // Proteção caso a tabela não tenha essas colunas
            if (!tdTaxa || !tdConversao)
                return;

            // Lógica Especial para BRL (Real)
            if (moedaSigla === "BRL" || moedaSigla === "R$") {
                tdTaxa.innerText = "1,0000";
                tdConversao.innerText = saldoTexto;
                return;
            }

            // Busca a taxa na lista filtrada (taxasData)
            // O '.find' procura o objeto cuja sigla da moeda bate com a da linha
            var taxaObj = taxasData.find(t => t.moeda && t.moeda.sigla === moedaSigla);

            if (taxaObj) {
                var valorTaxa = taxaObj.compra; // Valor Double

                // Exibe a taxa formatada (troca ponto por vírgula)
                tdTaxa.innerText = valorTaxa.toFixed(4).replace('.', ',');

                // Calcula a conversão
                var saldoFloat = formatarParaFloat(saldoTexto);
                var resultado = saldoFloat * valorTaxa;

                // Exibe a conversão formatada
                tdConversao.innerText = aplicarMascaraMoeda(resultado);
            } else {
                // Caso não encontre a taxa (Moeda sem PTAX cadastrada no período)
                tdTaxa.innerText = "N/D";
                tdConversao.innerText = "S/ Cotação";
                tdConversao.style.color = "red";
                tdConversao.style.fontWeight = "bold";
            }
        });
    }

    function formatarParaFloat(numeroStr) {
        if (!numeroStr)
            return 0;
        // Converte string "1.000,50" para float JS 1000.50
        // Remove pontos de milhar e troca vírgula decimal por ponto
        return parseFloat(numeroStr.toString().replace(/\./g, '').replace(',', '.'));
    }

    function aplicarMascaraMoeda(valor) {
        if (valor === null || valor === undefined)
            return "0,00";
        // Usa o formatador nativo do navegador para moeda brasileira
        return valor.toLocaleString('pt-BR', {minimumFractionDigits: 2, maximumFractionDigits: 2});
    }

// Função para filtrar as abas/tabelas das fichas
    function filtrarFichas() {
        var select = document.getElementById("selectFicha");
        if (!select)
            return;

        var opcaoSelecionada = select.value;
        var containers = document.getElementsByClassName("ficha-container");

        for (var i = 0; i < containers.length; i++) {
            var container = containers[i];
            if (opcaoSelecionada === "todas") {
                container.style.display = "block";
            } else {
                // Verifica se o ID corresponde (ex: container-ficha01)
                if (container.id === "container-" + opcaoSelecionada) {
                    container.style.display = "block";
                } else {
                    container.style.display = "none";
                }
            }
        }
    }

}