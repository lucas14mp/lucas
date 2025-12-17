const buttons = document.getElementsByClassName("btn");
const buttonsVoltarVisualizar = document.getElementsByClassName("btn voltar");
const buttonsSalvar = document.getElementsByClassName("btn salvar");
const buttonFichasSelecionado = document.getElementsByClassName("btn-exibicao-fichas equipe");
const buttonFichas = document.getElementsByClassName("btn-exibicao-fichas todas");
const buttonsTabela = document.getElementsByClassName("option-btn");
const tituloTabela = document.querySelectorAll('th');
const linhaTabela = document.querySelectorAll('tr');


function mudaTema() {
    const dataAtual = new Date();
    const mesAtual = dataAtual.getMonth(); //Comentar essa linha para testar a cor das tabelas e botões.
//    const mesAtual = 9; // Descomentar essa linha para testar a cor das tabelas e botões.

    if (mesAtual === 9) {
        console.log("Mês atual: " + mesAtual);
        for (const button of buttonsVoltarVisualizar) {
            console.log("Mês atual index: " + mesAtual);
            button.classList.add('btn-outubro');
            button.addEventListener('mouseover', function () {
                button.style.backgroundColor = '#f75773';
            });
            button.addEventListener('mouseout', function () {
                button.style.backgroundColor = '#fc8b9f';
            });
        }
        for (const buttonSalvar of buttonsSalvar) {
            buttonSalvar.classList.add('btn-outubro');
            buttonSalvar.addEventListener('mouseover', function () {
                buttonSalvar.style.backgroundColor = '#f75773';
            });
            buttonSalvar.addEventListener('mouseout', function () {
                buttonSalvar.style.backgroundColor = '#fc8b9f';
            });

        }
        for (const options of buttonsTabela) {
            options.style.backgroundColor = '#fc8b9f';
            options.addEventListener('mouseover', function () {
                options.style.backgroundColor = '#f75773';
            });
            options.addEventListener('mouseout', function () {
                options.style.backgroundColor = '#fc8b9f';
            });
        }


        for (const button of buttons) {

            button.style.backgroundColor = '#fc8b9f';
            button.style.border = 'none';
            button.addEventListener('mouseover', function () {
                button.style.backgroundColor = '#f75773';
            });
            button.addEventListener('mouseout', function () {
                button.style.backgroundColor = '#fc8b9f';
            });
        }

        for (const th of tituloTabela) {
            th.style.backgroundColor = '#fc8b9f';
        }

        linhaTabela.forEach((linha, index) => {
            if (index % 2 === 0) {
                linha.style.backgroundColor = '#facad2';
            } else {
                linha.style.backgroundColor = '#fae1e5';
            }
        });

//        for (const selecionado of buttonFichasSelecionado){
//            selecionado.classList.add('btn-exibicao-fichas-outubro');
//            selecionado.addEventListener('click', () => {
//            selecionado.style.backgroundColor = 'pink';
//            });
//        }
//        for (const naoSelecionado of buttonFichas){
//            naoSelecionado.classList.add('btn-exibicao-fichas-outubro');
//        }
    }

}
;
mudaTema();