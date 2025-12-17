//            SE ACHAR FUNÇÃO MAIS PRÁTICA, TROCAR.
function verificarConteudo(moeda) {
    const idMoeda = document.getElementById(moeda);

    if (idMoeda.value === '') {
        fichaVazia();
    } else if (idMoeda.value !== '') {
        fichaCheia();
    }
    console.log("chegou aqui");
}

verificarConteudo('moeda');

function fichaVazia() {
    var moedaSelecionada = document.getElementById('moeda');
    var elementosSimboloMoedas = document.getElementsByClassName('simbolo-moedas');

    var moedas = {
        '1': 'US$',
        '2': 'AU$',
        '3': 'C$',
        '4': '€',
        '5': '$',
        '6': 'kr',
        '7': '¥',
        '8': '¥',
        '9': 'kr',
        '10': '£',
        '11': 'kr',
        '12': 'Fr',
        '13': '₲',
        '14': 'Bs',
        '16': 'R$'
    };

    moedaSelecionada.addEventListener('change', function () {
        var idMoedaSelecionada = moedaSelecionada.value;

        // Itera sobre todos os elementos com a classe 'simbolo-moedas'
        for (var i = 0; i < elementosSimboloMoedas.length; i++) {
            var elemento = elementosSimboloMoedas[i];
            elemento.textContent = moedas[idMoedaSelecionada] || '';
            elemento.style.display = 'block';
        }
    });
}

function fichaCheia(){
document.addEventListener('DOMContentLoaded', function () {
    const moeda = document.getElementById('moeda');
    var idMoeda = moeda.options[moeda.selectedIndex].value;
    var elementosSimboloMoedas = document.getElementsByClassName('simbolo-moedas');
    var moedas = {
        '1': 'US$',
        '2': 'AU$',
        '3': 'C$',
        '4': '€',
        '5': '$',
        '6': 'kr',
        '7': '¥',
        '8': '¥',
        '9': 'kr',
        '10': '£',
        '11': 'kr',
        '12': 'Fr',
        '13': '₲',
        '14': 'Bs',
        '16': 'R$'
    };
    console.log(idMoeda);
    for (var i = 0; i < elementosSimboloMoedas.length; i++) {
        var elemento = elementosSimboloMoedas[i];
        elemento.textContent = moedas[idMoeda];
        elemento.style.display = 'block';
    }
    fichaVazia();
});
}