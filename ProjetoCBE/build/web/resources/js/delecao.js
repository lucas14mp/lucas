// nome da ficha que será passado na url do action do formulario
let nomeFichaDelecao;
let idFichaMaior;
const deleteBtns = [...document.querySelectorAll(".delete")];
deleteBtns.forEach(btn => {
  btn.addEventListener("click", () => {
    document.querySelector(".sobreposicao-tela-preta").style.display = "flex";
    nomeFichaDelecao = btn.dataset.ficha;
    idFichaMaior = btn.dataset.maior;
    gerarEventoDoModal(btn.dataset.id, idFichaMaior);
  });
});

function gerarEventoDoModal(id, idFichaMaior) {
  const btnCancela = document.querySelector(".cancela");
  btnCancela.addEventListener("click", () => {
    document.querySelector(".sobreposicao-tela-preta").style.display = "none";
    return;
  });
  const btnExcluir = document.querySelector(".exclui");
  btnExcluir.addEventListener("click", () => {
    criarFormularioDelecao(id, idFichaMaior);
  });

}

function criarFormularioDelecao(id, idFichaMaior) {
  const formularioDelecao = document.createElement("form");
  formularioDelecao.setAttribute("action", "/ProjetoCBE/" + nomeFichaDelecao);
  formularioDelecao.setAttribute("method", "post");

  const inputId = document.createElement("input");
  inputId.setAttribute("type", "hidden");
  inputId.setAttribute("name", "id");
  inputId.setAttribute("value", id);

  const inputTipo = document.createElement("input");
  inputTipo.setAttribute("type", "hidden");
  inputTipo.setAttribute("name", "tipo-requisicao");
  inputTipo.setAttribute("value", "delete");
    
  if (idFichaMaior) {
        const inputIdMaior = document.createElement("input");
        inputIdMaior.setAttribute("type", "hidden");
        inputIdMaior.setAttribute("name", "idFichaMaior");
        inputIdMaior.setAttribute("value", idFichaMaior);
        formularioDelecao.appendChild(inputIdMaior);
  }
  formularioDelecao.appendChild(inputId);
  formularioDelecao.appendChild(inputTipo);
  document.body.appendChild(formularioDelecao);
  formularioDelecao.submit();
}
