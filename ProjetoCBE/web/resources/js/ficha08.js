var rowCount = 1;
function adicionarLinha() {
  var table = document.querySelector("table");
  var lastRow = table.rows[table.rows.length - 1];
  if (lastRow) {
    var inputs = lastRow.getElementsByTagName("input");
    var inputValid = true;

    for (var i = 0; i < inputs.length; i++) {
      if (inputs[i].value === "") {
        inputValid = false;
        break;
      }
    }

    if (inputValid) {
      var newRow = table.insertRow(table.rows.length);
      var cell1 = newRow.insertCell(0);
      var cell2 = newRow.insertCell(1);
      var cell3 = newRow.insertCell(2);
      var cell4 = newRow.insertCell(3);
      var cell5 = newRow.insertCell(4);
      cell1.innerHTML = "<input type='text' placeholder='Informe aqui!' >";
      cell2.innerHTML = "<input type='text' placeholder='Informe aqui!' >";
      cell3.innerHTML = "<input type='number' step='0.01' placeholder='Informe aqui!' >";
      cell4.innerHTML = "<input type='number' step='0.01' placeholder='Informe aqui!' >";
      cell5.innerHTML = "<button type= 'button' class='btn-excluir' onclick='removerLinha(this)'>X</button>";
      rowCount++;
      if (rowCount % 2 === 0) {
        cell1.className = "row-dark";
        cell2.className = "row-dark";
        cell3.className = "row-dark";
        cell4.className = "row-dark";
        cell5.className = "transparent-col";
      } else {
        cell1.className = "row-light";
        cell2.className = "row-light";
        cell3.className = "row-light";
        cell4.className = "row-light";
        cell5.className = "transparent-col";
      }
    } else {
      alert("Preencha a linha atual antes de adicionar uma nova.");
    }
  }
}

function removerLinha(button) {
  var row = button.parentNode.parentNode;
  row.parentNode.removeChild(row);
}

document.getElementById("myForm").addEventListener("submit", function (event) {
  var table = document.querySelector("table");
  var rows = table.rows;
  var isValid = true;

  for (var i = 1; i < rows.length; i++) { // Começa em 1 para ignorar a linha de cabeçalho
    var inputs = rows[i].getElementsByTagName("input");

    for (var j = 0; j < inputs.length; j++) {
      if (inputs[j].value === "") {
        isValid = false;
        break;
      }
    }
  }

  if (!isValid) {
    alert("Preencha todas as linhas da tabela antes de enviar o formulário.");
    event.preventDefault();
  }
});