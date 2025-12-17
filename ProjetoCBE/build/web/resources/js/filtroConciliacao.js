const dataAtual = new Date();
const anoAtual = dataAtual.getFullYear();
const mesAtual = dataAtual.getMonth() + 1;


let trimestre;
if (mesAtual >= 1 && mesAtual <= 3) {
    trimestre = 4;
} else if (mesAtual >= 4 && mesAtual <= 6) {
    trimestre = 1;
} else if (mesAtual >= 7 && mesAtual <= 9) {
    trimestre = 2;
} else if (mesAtual >= 10 && mesAtual <= 12) {
    trimestre = 3;
}

const selectAno = document.getElementById("ano");
for (let ano = anoAtual; ano >= 2023; ano--) {
    const option = document.createElement("option");
    option.value = ano;
    option.text = ano;
    selectAno.appendChild(option);
}

const selectTri = document.getElementById("trimestre");
for (let tri = 1; tri <= trimestre; tri++) {
    const option = document.createElement("option");
    option.value = tri;
    option.text = tri + "º Trimestre";
    selectTri.appendChild(option);
}

// Função para atualizar as opções do menu suspenso de trimestres
function atualizarTrimestres() {
    const anoSelecionado = parseInt(selectAno.value);

    // Remove todas as opções atuais do menu suspenso de trimestres
    while (selectTri.firstChild) {
        selectTri.removeChild(selectTri.firstChild);
    }

    // Adiciona as opções de trimestres com base no ano selecionado
    for (let tri = 1; tri <= 4; tri++) {
        const option = document.createElement("option");
        option.value = tri;
        option.text = tri + "º Trimestre";
        selectTri.appendChild(option);
    }
    if (anoSelecionado === 2023){
        selectTri.options[0].disabled = true;
        selectTri.options[1].disabled = true;
        selectTri.options[2].disabled = true;
    }
    // Se o ano selecionado for igual ao ano atual, desabilita os trimestres futuros
    if (anoSelecionado === new Date().getFullYear()) {
        const mesAtual = new Date().getMonth() + 1;
        for (let tri = 4; tri > mesAtual / 3; tri--) {
            selectTri.options[tri-1].disabled = true;
        }
    }
}

// Adiciona o evento onchange ao elemento de seleção de ano
selectAno.addEventListener("change", atualizarTrimestres);