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
    //TESTANDO O QUE VEM DAS FICHAS (APAGAR DEPOIS)    
    var fichasteste = PegarFichas();
    console.log("FICHAS");
    console.log(fichasteste);
    ////////////////////////////
    nomeFichaValidacao = btnValidar.dataset.ficha;
//    criarJustificativa();
    var dataFicha = btnValidar.getAttribute("data-ficha");
    numeroFicha = dataFicha;
    //tratamento ficha11
    if (dataFicha.includes("/")){
        var partes = dataFicha.split("/");
      
        info = partes[1];
        
        numeroFicha = partes[0];
        
        console.log("NUMERO FICHA: ", numeroFicha);
        
        if (numeroFicha === "ficha11"){
            numero = ficha11Cal(info);//chamando calculo
        }
        else if(numeroFicha === "ficha14"){
            numero = ficha14Cal(info);
        }
    }
    else{
        info = dataFicha;
        numeroFicha = dataFicha;
        //TODOS MENOS A FICHA11    
        switch(info){
            case "ficha03":
           console.log("PASSOU FICHA 03");
             diferenca = ficha3Cal(info);
             break;
             
            case "ficha08":
                console.log("PASSOU FICHA 08");
                diferenca = ficha8Cal01(info);//chamando calculo
              break;
              
                 case "ficha11":
                  console.log("PASSOU FICHA 11");
                   diferenca =  ficha3Cal(info);
                   break;
                   
                case "ficha16":
                    console.log("PASSOU FICHA 16");
                diferença = ficha16Ca1(info);
                break;
                
                case "ficha18":
                    console.log("PASSOU FICHA 18");
                    diferença = ficha18Ca1(info);
                break;
                
                
             default:
                console.log("Opção inválida.");
//    case "ficha11" 
        }              
        }
    console.log("PEGANDO PARA O FORM");
    somatorioFichas = getSomatorioFichas(numeroFicha, info);
    contabilFicha = getContabilByFicha(numeroFicha, info);
//CASO QUEIRA TESTAR ALTERE A DIFERENÇA AQUI
    console.log(diferenca);
    diferenca = 10;
    conteudo = GerarForm(diferenca, somatorioFichas, contabilFicha.saldo);
    gerarCheckboxValidacao(btn.target.id);
    if (diferenca > 0){
        $.confirm({
            animation: 'top',
            closeAnimation: 'scale',
            columnClass: 'col-md-6',
            title: 'Diferença encontrada na validação!',
            type: 'red',
            typeAnimated: true,
            content: conteudo,
            buttons: {               
            formSubmit: {
                    text: 'Enviar',
                    btnClass: 'btn-red',
                    action: function () {
                        var justificativa = this.$content.find('#justificativa').val();
                        if (!justificativa) {
                            $.alert('Preencha a Justificativa para poder enviar!!');
                            return false;
                        }
                        PegarDados(somatorioFichas, contabilFicha.saldo, diferenca, dataFicha);    
                    }
                },
                cancel: {
                    text: 'Cancelar',
                        function () {
                        window.location.reload();     
                        close;
                        
                    }
                }
            },
            onContentReady: function () {
                // bind to events
                var jc = this;
                this.$content.find('form').on('submit', function (e) {
                    // if the user submits the form by pressing enter in the field.
                    e.preventDefault();
                    jc.$$formSubmit.trigger('click'); // reference the button and click it
                });
            }
        });
    }
    else{
        $.confirm({
        title: 'Validação!',
        content: conteudo,
        buttons: {
            confirm: {
                text: 'Enviar',
                btnClass: 'btn-green',
                action: function (){
                    $.alert('Validado!');
                    Teste();
                    PegarDados(somatorioFichas, contabilFicha.saldo, diferenca, dataFicha);
                }
            },
            cancel: {
                text: 'Cancelar',
                btnClass: 'btn-red',
                action: function (){
                    $.alert('Cancelado!');
                    window.location.reload(); 
                    close;
                }
            }
        }
    });
    }

      
//    window.alert(texto);

//    gerarCheckboxValidacao(btn.target.id);
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
//    if (status === 'Validado')
      htmlCelula += "<input type='checkbox' title='Validar' checked disabled data-id='" + btnDelete.dataset.id + "'>";
//    else
//      htmlCelula += "<input type='checkbox' title='Validar' data-id='" + btnDelete.dataset.id + "'>";
    celula.innerHTML = htmlCelula;
  });
  document.getElementById(id).disabled = true;
//  gerarBotaoEnviar();
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
  if(diferenca > 0){
      btnEnviar.disabled = true;
  }
  
  const justificativa = document.getElementById("justificativa");
  
  justificativa.addEventListener("input", function(){
     if (justificativa.value.trim() !== ""){
         console.log("DIGITOU ALELUIA");
         btnEnviar.disabled = false;
     }
     else{
         console.log("NÃO PODE TIRAR O TEXTO");
         btnEnviar.disabled = true;
     }
  });
  
  btnEnviar.addEventListener("click", () => {
    gerarFormsValidacao();
  });
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

function HabilitarTextArea(){
    const justificativa = document.getElementById("justificativa");
}

function criarJustificativa(){
        const container = document.querySelector(".view-container");
        const Labelcontainer = document.createElement("div");
        const textAreaContainer = document.createElement("div");
        const textoJustificativa = document.createElement("label");
        const justificativa = document.createElement("textarea");
        Labelcontainer.classList.add("just-container");
        Labelcontainer.setAttribute("id", "label-justificativa");
        justificativa.classList.add("justificativa");
        justificativa.setAttribute("id", "justificativa");
        textoJustificativa.textContent = "TESTANDO UM TEXTINHO";
        container.appendChild(Labelcontainer);
        Labelcontainer.appendChild(textoJustificativa);
        textAreaContainer.appendChild(justificativa);
        Labelcontainer.appendChild(textAreaContainer);
        document.body.appendChild(container);
        document.getElementById("label-justificativa").style.display = "none";
//        document.getElementById("justificativa").style.display = "none"; // Define o display como none após adicionar ao DOM
}

function HabilitarJustificativa(){
    document.getElementById("label-justificativa").style.display = "block";
}

function PegarDados(somatorio, contabil, diferenca, Ficha){
//    var numero = PegarNumeroFicha();
//    console.log(ficha);
    var tipoRequisicao = "validacaoBatch";
    var just = $("#justificativa").val();
    const idsFicha = getTodosIdsCheckboxesMarcados();
    console.log(just);
    var data = [];
    data.push({"tipo-requisicao": tipoRequisicao});
    data.push({"justificativa": just});
    data.push({"somatorio": somatorio});
    data.push({"contabil": contabil});
    data.push({"diferenca": diferenca});
    data.push({"fichas": idsFicha});
    data.push({"numeroFicha": Ficha});
    console.log(data);
    console.log(Ficha);
    EnviarValidacao(Ficha, data);
}


function EnviarValidacao(Ficha, data){
    var contextPath = document.getElementById('contextPath').value;
    $.ajax({
        url: contextPath + '/' + Ficha,
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (response) {
    //        console.log('Success: ', response);
            if (response.redirectUrl) {
                window.location.href = response.redirectUrl;
            } else {
                alert("Erro ao redirecionar. URL de redirecionamento não fornecida.");
            }
        },
        error: function (error) {
    //        carrega.style.display = 'none';
            console.error('Erro:', error);
            alert("Erro ao enviar as informações. Por favor, verifique os dados e envie novamente.");
        }
    });
}

function GerarForm(diferenca, somatorioFichas, contabilFicha){
    var form;
    
    if (diferenca > 0 ){
        console.log("MAIOR QUE ZERO");
            form = '' +
            '<div class="form-group">' +
            '<label>Todas as fichas preenchidas serão validadas, deseja prosseguir com a validação?</label>' +
            '<br><br><br>' +
            '<div id="tabelaMaior" class="container-maior" style="overflow-x: hidden;">' +
            '<table class="table-lista-fichas maior" id="table-just">' +
            '<tr>'+
            '<th>Fichas</th> <th>Contábil</th> <th>Difrenca</th>' +
            '</tr>' +
            '<tr>'+
            '<td>' + somatorioFichas + '</td> <td>' + contabilFicha + '</td> <td>' + diferenca + '</td>' +
            '</tr>' +
            '</table>' +    
            '<br><br>' +
            '' +
            '</div>' +
            '' +
            '' +
            '<textarea id="justificativa" type="text" placeholder="justificativa" class="name form-control" style="resize: both;" required></textarea>';
    }
    else{
        //NTD = NÃO TEM DIFERENÇA
        form = '' +
            '<div class="form-group">' +
            '<label>Todas as fichas preenchidas serão validadas, deseja prosseguir com a validação?</label>' +
            '<textarea id="justificativa" type="text" placeholder="justificativa" class="name form-control" style="resize: both; display: none;">NTD</textarea>';
    }
    
    return form;
}

function Teste(){
    var testando = $("#justificativa").val();
    console.log("TESTANDO = ", testando);
}


//function PegarNumeroFicha(ficha){
//    var numeroFicha = null;
//    if (ficha.includes("/")){
//        var partes = ficha.split("/");
//        
//        var info = partes[0];
//        
//        numeroFicha = info.replace("ficha", "");
//    }
//    else{
//        numeroFicha = ficha.replace("ficha", "");
//    }
//    
//    return numeroFicha;
//}