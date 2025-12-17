document.addEventListener("DOMContentLoaded", function () {

    const respostaControleInputs = document.querySelectorAll('input[name="resposta-controle"]');
    const btnRedireciona = document.getElementById('btnSalvar');
    let respostaSim = false;

    respostaControleInputs.forEach(input => {
        input.addEventListener('click', function () {
            if (input.value === 'sim') {
                respostaSim = true;
            } else {
                respostaSim = false;
            }
        });
    });

    const respostaEmpresaInputs = document.querySelectorAll('input[name="resposta-empresa"]');
    let respostaEmpresa = false;

    respostaEmpresaInputs.forEach(input => {
        input.addEventListener('click', function () {
            if (input.value === 'sim' || input.value === 'nao') {
                respostaEmpresa = true;
            } else {
                respostaEmpresa = false;
            }
        });
    });


    btnRedireciona.addEventListener('click', function (e) {
        if (respostaSim === true && respostaEmpresa === true) {
            e.preventDefault();
            window.location.href = 'ficha14Empresa.jsp';
        }
    });

});