document.addEventListener("DOMContentLoaded", function () {
        const respostaInputs = [... document.querySelectorAll('input[name="atividade-economica"]')];
        const blockDetalhamentoAtividade = document.getElementById('detalhamento-atividade-container');

        respostaInputs.forEach(input => {
          input.addEventListener('click', function () {
            if (input.value === '64 - Atividades de serviços financeiros') {
              blockDetalhamentoAtividade.style.display = 'block';
            } else {
              blockDetalhamentoAtividade.style.display = 'none';
            }
          });
        });

        const respostaChecada = document.querySelector('input[name="atividade-economica"]:checked');
        if (respostaChecada.value === '64 - Atividades de serviços financeiros') {
          blockDetalhamentoAtividade.style.display = 'block';
        } else {
          blockDetalhamentoAtividade.style.display = 'none';
        }

      });