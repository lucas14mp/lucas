document.addEventListener("DOMContentLoaded", function () {
  const respostaQuitadoInputs = [... document.querySelectorAll('input[name="quitado"]')];
  const devedorInput = document.getElementById('devedor');
  console.log(respostaQuitadoInputs);
  console.log(devedorInput);

  respostaQuitadoInputs.forEach(input => {
    input.addEventListener('click', function () {
      if (input.value === 'true') {
        devedorInput.value = 0;
        console.log(input.value);
        devedorInput.setAttribute('disabled', 'true');
      } else if (input.value === 'false') {
        console.log(input.value);
        devedorInput.value = '';
        devedorInput.removeAttribute('disabled');
      }
    });
  });

  const respostaChecada = document.querySelector('input[name="quitado"]:checked');
  if (respostaChecada.value === 'true') {
    devedorInput.value = 0;
    console.log(respostaChecada.value);
    devedorInput.setAttribute('disabled', 'true');
  } else if (respostaChecada.value === 'false') {
    console.log(respostaChecada.value);
    devedorInput.removeAttribute('disabled');
  }

});