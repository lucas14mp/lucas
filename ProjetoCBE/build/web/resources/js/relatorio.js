const arrowsImagem = document.querySelectorAll(".border-img-expandir");
const arrows = document.querySelectorAll(".img-expandir");
const containerTabelas = document.querySelectorAll(".view-relatorio");
const tabelas = document.querySelectorAll(".table-lista-fichas-relatorio");
const tableWrapper11 = document.getElementById("twFicha11");
const tableWrapper11Empresa = document.getElementById("twFicha11Empresa");
const imprimir = document.getElementById("imprimir");
console.log("Ano: ", ano);
console.log("Trimestre", trimestre);

tabelas.forEach((table, index) => {
    if (table.rows.length === 1) {
        containerTabelas[index].style.display = 'none';
    }                           
});

arrowsImagem.forEach((a, index) => {
    a.addEventListener("click", () => {
        console.log('abriu');
        tabelas[index].classList.toggle("relatorio-ativo");
        if (tabelas[index].classList.contains("relatorio-ativo")) {
            expande(index);
        } else {
            naoExpande(index);
        }
    });
});

function expande(index) {
    arrows[index].style.rotate = "90deg";
    tabelas[index].style.display = 'block';
    console.log("tabela-index: " + index);
    if (index === 10) {
        tableWrapper11.style.overflowX = 'auto';
        tableWrapper11.style.overflowY = 'auto';
    } else if (index === 11) {
        tableWrapper11Empresa.style.overflowX = 'auto';
        tableWrapper11Empresa.style.overflowY = 'auto';
    }
}

function naoExpande(index) {
    arrows[index].style.rotate = "0deg";
    tabelas[index].style.display = 'none';
    if (index === 10) {
        tableWrapper11.style.overflowX = 'hidden';
        tableWrapper11.style.overflowY = 'hidden';
    } else if (index === 11) {
        tableWrapper11Empresa.style.overflowX = 'hidden';
        tableWrapper11Empresa.style.overflowY = 'hidden';
    }
}

imprimir.addEventListener("click", () => {
    tabelas.forEach((tabela, index) => {
        expande(index);
    });
    window.print();
});
