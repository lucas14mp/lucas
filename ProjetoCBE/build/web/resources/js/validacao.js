// nome da ficha que será passado na url do action do formulario
let nomeFichaValidacao;
var diferenca = 0;
const temDiferenca = false;
var texto;
let numero;
var info = "nada";
var numeroFicha;
var somatorioFichas = 0;
var contabilFicha = 0;
var diferenca = 0;
var conteudo = "";

const btnsValidacao = [...document.querySelectorAll(".btn-validar")];
btnsValidacao.forEach(btnValidar => {
  btnValidar.addEventListener("click", (btn) => {
    window.alert("Marque o checkbox presente nas opções das fichas que deseja validar.\n\nATENÇÃO: Uma vez validada, não será possível editar nem excluir uma informação."); 
    nomeFichaValidacao = btnValidar.dataset.ficha;
    gerarCheckboxValidacao(btn.target.id);
  });
});

function gerarCheckboxValidacao(id) {
  let celulasOpcoes;
  if (nomeFichaValidacao.length > 7) {
    if (id === "valida-menor") {
      const fichaMenor = document.querySelector("#tabelaMenor");
      bloquearTrocaFicha(id);
      celulasOpcoes = [...fichaMenor.querySelectorAll(".opcoes-col")];
    } else {
      const fichaMaior = document.querySelector("#maior");
      bloquearTrocaFicha(id);
      celulasOpcoes = [...fichaMaior.querySelectorAll(".opcoes-col")];
    }
  } else {
    celulasOpcoes = [...document.querySelectorAll(".opcoes-col")];
  }
  celulasOpcoes.forEach((celula) => {
    const btnDelete = celula.querySelector(".delete"); // pega o botao delete para pegar o id da linha
    const status = celula.previousElementSibling.innerText;
    let htmlCelula = celula.innerHTML;
    if (status === 'Validado')
      htmlCelula += "<input type='checkbox' title='Validar' checked disabled data-id='" + btnDelete.dataset.id + "'>";
    else{
      htmlCelula += "<input type='checkbox' title='Validar' data-id='" + btnDelete.dataset.id + "'>";
   }
   celula.innerHTML = htmlCelula;
  });
  document.getElementById(id).disabled = true;
  gerarBotaoEnviar();
}

function bloquearTrocaFicha(idFicha) {
  let fichaContraria;
  if (idFicha === "valida-menor") {
    fichaContraria = "maior";
  } else {
    fichaContraria = "menor";
  }
  radioBtn = document.querySelector('input[name="resposta-participacao"][value="' + fichaContraria + '"]');
  radioBtn.disabled = true;
  labelRadioBtnContrario = document.querySelector('.label-radio-' + fichaContraria);
  labelRadioBtnContrario.addEventListener('click', () => {
    window.alert("Não é possível trocar de ficha antes de salvar a validação.\n\nPor favor, clique no botão 'Enviar' antes de trocar de ficha.");
  });
}

function getTodosIdsCheckboxesMarcados() {
  const checkboxMarcadas = [... document.querySelectorAll('input[type=checkbox]:checked')];
  const idsArray = [];
  checkboxMarcadas.forEach(checkbox => {
    if (checkbox.dataset.id === "undefined"){
        console.log("indefinido");
    }
    else{
    idsArray.push(checkbox.dataset.id);
    }
  });
  return idsArray;

}

function gerarFormsValidacao() {
  const formularioValidacao = document.createElement("form");
  formularioValidacao.setAttribute("action", "/ProjetoCBE/" + nomeFichaValidacao);
  formularioValidacao.setAttribute("method", "post");
  formularioValidacao.setAttribute("id", "formsValidacao");
  formularioValidacao.style.display = "none";

  const inputTipo = document.createElement("input");
  inputTipo.setAttribute("type", "hidden");
  inputTipo.setAttribute("name", "tipo-requisicao");
  inputTipo.setAttribute("value", "validacao");
  formularioValidacao.appendChild(inputTipo);

  const idsValidados = getTodosIdsCheckboxesMarcados();
  idsValidados.forEach(id => {
    const inputId = document.createElement("input");
    inputId.setAttribute("type", "hidden");
    inputId.setAttribute("name", "idsValidados[]");
    inputId.setAttribute("value", id);
    formularioValidacao.appendChild(inputId);
  });
  document.body.appendChild(formularioValidacao);
  formularioValidacao.submit();
}

function gerarBotaoEnviar() {
  const containerBotaoEnviar = document.createElement("div");
  containerBotaoEnviar.classList.add("container-btn-enviar");
  const btnEnviar = document.createElement("input");
  btnEnviar.setAttribute("type", "button");
  btnEnviar.setAttribute("value", "Enviar");
  btnEnviar.setAttribute("class", "btn");
  containerBotaoEnviar.appendChild(btnEnviar);
  const container = document.querySelector(".view-container");
  container.appendChild(containerBotaoEnviar);
  btnEnviar.addEventListener("click", () => {
    gerarFormsValidacao();
  });
  
 }