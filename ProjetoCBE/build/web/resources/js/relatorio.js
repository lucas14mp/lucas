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

// Função para abrir o modal, injetar texto e SALVAR o ID e o CONTEXTO da ficha
function verJustificativa(textoJustificativa, idFicha, nomeServlet) {
    var modal = document.getElementById('modalVisualizarJustificativa');
    var containerTexto = document.getElementById('conteudoJustificativaTexto');
    var inputId = document.getElementById('modalFichaId');
    var inputServlet = document.getElementById('modalFichaServlet');

    if (modal && containerTexto) {
        containerTexto.innerText = textoJustificativa;
        // Salva os dados nos inputs hidden para usar no botão "Recusar"
        inputId.value = idFicha;
        inputServlet.value = nomeServlet; // Ex: 'ficha16', 'ficha18'
        
        modal.style.display = 'block';
    }
}

function fecharModalJustificativa() {
    document.getElementById('modalVisualizarJustificativa').style.display = 'none';
    document.getElementById('conteudoJustificativaTexto').innerText = "";
}

// Função AJAX para recusar a justificativa
function recusarJustificativa() {
    var idFicha = document.getElementById('modalFichaId').value;
    var nomeServlet = document.getElementById('modalFichaServlet').value; // Ex: "ficha16"

    if (!idFicha || !nomeServlet) {
        alert("Erro ao identificar a ficha.");
        return;
    }

    if (!confirm("Tem certeza que deseja recusar esta justificativa? O status voltará para 'Não Validado'.")) {
        return;
    }

    // Envia requisição para o Controller específico (Ex: ./ficha16)
    // Usamos fetch ou jQuery. Vou usar jQuery pois você já usa no projeto
    $.ajax({
        type: "POST",
        url: nomeServlet, // Chama a URL do controller (ex: ficha16)
        data: {
            "tipo-requisicao": "recusar",
            "id": idFicha
        },
        success: function(response) {
            alert("Justificativa recusada com sucesso! Status alterado.");
            fecharModalJustificativa();
            location.reload(); // Recarrega a página para atualizar a tabela
        },
        error: function(xhr) {
            console.error(xhr);
            alert("Erro ao recusar justificativa.");
        }
    });
}

window.onclick = function(event) {
    var modal = document.getElementById('modalVisualizarJustificativa');
    if (event.target == modal) {
        fecharModalJustificativa();
    }
}