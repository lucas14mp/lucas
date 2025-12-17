const dataAtual = new Date();
const mesAtual = dataAtual.getMonth(); // Comentar essa linha para testar a cor das tabelas e botões.
const btnsExibicao = document.querySelectorAll(".btn-exibicao-fichas");
const tableTodas = document.querySelector(".table-todas");
const tableEquipe = document.querySelector(".table-equipe");
const buttons = document.getElementsByClassName("btn");
//const mesAtual = 9; // Descomentar essa linha para testar a cor das tabelas e botões.

if (mesAtual === 9) {
    btnsExibicao.forEach(btn => {
        btn.classList.remove('btn-exibicao-fichas');
        btn.classList.add('btn-exibicao-fichas-outubro');
        btn.addEventListener('click', () => {
            if (btn.classList.contains("todas")) {
                window.localStorage.setItem("opcaoIndex", "todas");
                btn.classList.add("selecionado-outubro");
                btnsExibicao[1].classList.remove("selecionado-outubro");
                tableTodas.style.display = "block";
                tableEquipe.style.display = "none";
            } else {
                window.localStorage.setItem("opcaoIndex", "equipe");
                btn.classList.add("selecionado-outubro");
                btnsExibicao[0].classList.remove("selecionado-outubro");
                tableTodas.style.display = "none";
                tableEquipe.style.display = "block";
            }
        });
    });

    document.addEventListener("DOMContentLoaded", () => {
        if (window.localStorage.getItem("opcaoIndex") === "equipe") {
            btnsExibicao[1].classList.add("selecionado-outubro");
            btnsExibicao[0].classList.remove("selecionado-outubro");
            tableTodas.style.display = "none";
            tableEquipe.style.display = "block";
        } else {
            btnsExibicao[0].classList.add("selecionado-outubro");
            btnsExibicao[1].classList.remove("selecionado-outubro");
            tableTodas.style.display = "block";
            tableEquipe.style.display = "none";
        }
    });

    btnsExibicao.forEach(btn => {
        btn.addEventListener('click', () => {
            if (btn.classList.contains("todas")) {
                window.localStorage.setItem("opcaoIndex", "todas");
                btn.classList.add("selecionado");
                btnsExibicao[1].classList.remove("selecionado");
                tableTodas.style.display = "block";
                tableEquipe.style.display = "none";
            } else {
                window.localStorage.setItem("opcaoIndex", "equipe");
                btn.classList.add("selecionado");
                btnsExibicao[0].classList.remove("selecionado");
                tableTodas.style.display = "none";
                tableEquipe.style.display = "block";
            }
        });
    });

    document.addEventListener("DOMContentLoaded", () => {
        if (window.localStorage.getItem("opcaoIndex") === "equipe") {
            btnsExibicao[1].classList.add("selecionado");
            btnsExibicao[0].classList.remove("selecionado");
            tableTodas.style.display = "none";
            tableEquipe.style.display = "block";
        } else {
            btnsExibicao[0].classList.add("selecionado");
            btnsExibicao[1].classList.remove("selecionado");
            tableTodas.style.display = "block";
            tableEquipe.style.display = "none";
        }
    });

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
} else {
    btnsExibicao.forEach(btn => {
        btn.addEventListener('click', () => {
            if (btn.classList.contains("todas")) {
                window.localStorage.setItem("opcaoIndex", "todas");
                btn.classList.add("selecionado");
                btnsExibicao[1].classList.remove("selecionado");
                tableTodas.style.display = "block";
                tableEquipe.style.display = "none";
            } else {
                window.localStorage.setItem("opcaoIndex", "equipe");
                btn.classList.add("selecionado");
                btnsExibicao[0].classList.remove("selecionado");
                tableTodas.style.display = "none";
                tableEquipe.style.display = "block";
            }
        });
    });

    document.addEventListener("DOMContentLoaded", () => {
        if (window.localStorage.getItem("opcaoIndex") === "equipe") {
            btnsExibicao[1].classList.add("selecionado");
            btnsExibicao[0].classList.remove("selecionado");
            tableTodas.style.display = "none";
            tableEquipe.style.display = "block";
        } else {
            btnsExibicao[0].classList.add("selecionado");
            btnsExibicao[1].classList.remove("selecionado");
            tableTodas.style.display = "block";
            tableEquipe.style.display = "none";
        }
    });
}