
document.addEventListener("DOMContentLoaded", function () {
    const respostaInputs = document.querySelectorAll('input[name="resposta-participacao"]');
    const perguntasFormMenor = document.getElementById('formMenor');
    const perguntasFormMaior = document.getElementById('formMaior');

    respostaInputs.forEach(input => {
        input.addEventListener('click', function () {
            if (input.value === 'menor-que-10') {
                perguntasFormMenor.style.display = 'block';
                perguntasFormMaior.style.display = 'none';
            } else if (input.value === 'maior-que-10') {
                perguntasFormMenor.style.display = 'none';
                perguntasFormMaior.style.display = 'block';
            } else {
                perguntasFormMenor.style.display = 'none';
                perguntasFormMaior.style.display = 'none';
            }
        });
    });

    const btnRedireciona = document.getElementById('btnRedireciona');
    const btnSalvar = document.getElementById('btnSalvar');

    const respostaFundoInputs = document.querySelectorAll('input[name="controla"]');

    respostaFundoInputs.forEach(input => {
        input.addEventListener('click', function () {
            if (input.value === 'true') {
                btnRedireciona.style.display = 'block';
                btnSalvar.style.display = 'none';
            } else {
                btnRedireciona.style.display = 'none';
                btnSalvar.style.display = 'block';
            }
        });
    });

    var moedaSelecionada = document.getElementById('moedaMaior');
    var moedaSelecionadaMenor = document.getElementById('moedaMenor');
    var elementosSimboloMoedas = document.getElementsByClassName('simbolo-moedas');
    var elementosSimboloMoedasMenores = document.getElementsByClassName('simbolo-moedas-menor');

    var moedas = {
        '1': 'US$',
        '2': 'AU$',
        '3': 'C$',
        '4': '€',
        '5': '$',
        '6': 'kr',
        '9': 'kr',
        '11': 'kr',
        '7': '¥',
        '8': '¥',
        '10': '£',
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
//ALTERAR PARA QUE AS FICHAS FIQUEM IDEPENDENTES
    moedaSelecionadaMenor.addEventListener('change', function () {
        var idMoedaSelecionadaMenor = moedaSelecionadaMenor.value;

        // Itera sobre todos os elementos com a classe 'simbolo-moedas'
        for (var i = 0; i < elementosSimboloMoedasMenores.length; i++) {
            var elemento = elementosSimboloMoedasMenores[i];
            elemento.textContent = moedas[idMoedaSelecionadaMenor] || '';
            elemento.style.display = 'block';
        }
    });

});
 