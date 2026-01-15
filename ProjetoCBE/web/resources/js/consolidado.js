/* ============================ C O N S O L I D A D O ============================ */
/* Arquivo: /ProjetoCBE/resources/js/consolidado.js */
(function () {
  'use strict';

  /* ============================ U T I L I T Á R I O S ============================ */
  const fmtNumber = new Intl.NumberFormat('pt-BR', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  });
  const fmtCurrency = new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  });

  const toNumber = (v) => {
    if(!v) return 0;
    if(typeof v === 'number') return v;
    let clean = v.toString().replace('R$', '').trim();
    if(clean.includes(',') && clean.includes('.')) { 
        clean = clean.replace(/\./g, '').replace(',', '.');
    } else if (clean.includes(',')) {
        clean = clean.replace(',', '.');
    }
    return Number(clean);
  };
  
  const isFiniteNumber = (v) => Number.isFinite(toNumber(v));

  function formatMoney(v) {
    return isFiniteNumber(v) ? fmtCurrency.format(toNumber(v)) : 'R$ 0,00';
  }
  function formatPercent(v) {
    return isFiniteNumber(v) ? `${fmtNumber.format(toNumber(v))}%` : '0,00%';
  }

  /* ================================ C O S I F =================================== */
  /** * Formata COSIF no padrão solicitado: #.#.#.##.##.##
   * CORREÇÃO: Usa padEnd(9, '0') para garantir que números curtos vindos do banco
   * (ex: 1154) virem o formato correto (115400000 -> 1.1.5.40.00.00)
   */


function formatCosifNoDv(raw) {
  if (raw == null) return '';

  const s = String(raw).trim();

  // 1) Se começa com dígitos/pontos, pega tudo até o primeiro caractere que não seja dígito nem ponto.
  const headWithDots = s.match(/^([\d.]+)/)?.[1] ?? '';

  // Remove pontos do cabeçalho (fica só dígitos)
  let core = headWithDots.replace(/\D/g, '');

  // 2) Fallback: se não achou nada no começo, tenta achar um bloco de 7–9 dígitos em qualquer lugar (COSIF base)
  if (!core) {
    const m = s.match(/\b\d{7,9}\b/);
    core = m?.[0] ?? '';
  }

  // 3) Se ainda não há dígitos, não dá para formatar
  if (!core) return '';

  // 4) Garante exatamente 9 dígitos (corta DV/subconta/excesso e completa faltantes à direita)
  core = core.slice(0, 9).padEnd(9, '0');

  // 5) Monta 1-1-1-2-2-2
  const p1 = core.slice(0, 1);
  const p2 = core.slice(1, 2);
  const p3 = core.slice(2, 3);
  const p4 = core.slice(3, 5);
  const p5 = core.slice(5, 7);
  const p6 = core.slice(7, 9);

  return `${p1}.${p2}.${p3}.${p4}.${p5}.${p6}`;
}



  function renderCosifCell(cosifRaw, nomeCosif) {
    const formatted = formatCosifNoDv(cosifRaw);
    // Limpa hífens extras se o nome já vier com eles
    let nome = (nomeCosif ?? '').trim();
    if(nome.startsWith('-') || nome.startsWith('–')) {
        nome = nome.substring(1).trim();
    }
    return nome ? `${formatted} - ${nome}` : formatted;
  }

  function hydrateCosifCells(root = document) {
    const scope = (root && typeof root.querySelectorAll === 'function') ? root : document;
    scope.querySelectorAll('td.cosif-cell[data-cosif]').forEach((td) => {
      if (td.dataset.hydrated === '1') return;
      const cosif = td.getAttribute('data-cosif');
      const nome  = td.getAttribute('data-nome');
      td.textContent = renderCosifCell(cosif, nome);
      td.dataset.hydrated = '1';
    });
  }

  /* ========================== V A L O R  C O N T Á B I L ======================== */
  function hydrateValorContabil(root = document) {
    const scope = (root && typeof root.querySelectorAll === 'function') ? root : document;
    scope.querySelectorAll('td.valor-contabil[data-value]').forEach((td) => {
      if (td.dataset.hydrated === '1') return;
      const v = td.getAttribute('data-value');
      td.textContent = formatMoney(v);
      td.dataset.hydrated = '1';
    });
  }

  /* ======================= G E S T O R / D I F F / % ======================== */
  function hydrateGestorDiffPct(root = document) {
    const scope = (root && typeof root.querySelectorAll === 'function') ? root : document;
    const gruposProcessados = new Set();

    scope.querySelectorAll('tr[data-group-index]').forEach((tr) => {
      const fichaRaw = tr.getAttribute('data-ficha'); 
      const rowCount = Number(tr.getAttribute('data-rowcount') ?? '1');

      const tdGestor = tr.querySelector('td.valor-gestor');
      const tdDiff   = tr.querySelector('td.diferenca');
      const tdPct    = tr.querySelector('td.porcentagem');
      if (!tdGestor || !tdDiff || !tdPct) return;

      const setPctAlert = (p) => {
        const n = Number(p);
        if (isFinite(n) && Math.abs(n) > 0.5) tdPct.classList.add('alerta');
        else tdPct.classList.remove('alerta');
      };

      const isFicha8     = fichaRaw === '8' || Number(fichaRaw) === 8;
      const isSubFicha11 = typeof fichaRaw === 'string' && fichaRaw.startsWith('11.');

      if (isFicha8 || isSubFicha11) {
        const vAgg = tr.getAttribute('data-valor-gestor-agg');
        const dAgg = tr.getAttribute('data-diferenca-agg');
        const pAgg = tr.getAttribute('data-porcentagem-agg');

        // Usa a ficha completa (ex: 11.1) para controlar o agrupamento
        if (!gruposProcessados.has(fichaRaw)) {
          tdGestor.textContent = formatMoney(vAgg);
          tdDiff.textContent   = formatMoney(dAgg);
          tdPct.textContent    = formatPercent(pAgg);
          setPctAlert(pAgg);

          tdGestor.rowSpan = rowCount;
          tdDiff.rowSpan   = rowCount;
          tdPct.rowSpan    = rowCount;
          gruposProcessados.add(fichaRaw);
        } else {
          tdGestor.remove();
          tdDiff.remove();
          tdPct.remove();
        }
      } else {
        const v = tr.getAttribute('data-valor-ficha');
        const d = tr.getAttribute('data-diferenca');
        const p = tr.getAttribute('data-porcentagem');

        tdGestor.textContent = formatMoney(v);
        tdDiff.textContent   = formatMoney(d);
        tdPct.textContent    = formatPercent(p);
        setPctAlert(p);
      }
    });
  }

  /* ===================== F I C H A  (coluna agrupada) =================== */
  function hydrateColunaFicha(root = document) {
    const scope = (root && typeof root.querySelectorAll === 'function') ? root : document;
    scope.querySelectorAll('td.col-ficha[data-ficha]').forEach(td => {
      if (td.dataset.hydrated === '1') return;

      const fichaRaw = String(td.getAttribute('data-ficha') || '').trim();
      const nomeRaw  = String(td.getAttribute('data-nome-ficha') || '').trim();
      
      if(fichaRaw === '0') {
         td.textContent = '0 - Sem Ficha';
         td.dataset.hydrated = '1';
         return;
      }

      const fichaLabel = fichaRaw.includes('.') ? fichaRaw.split('.')[0] : fichaRaw;
      const nome = nomeRaw.replace(/^[0-9.\s–-]+/g, ''); 

      td.textContent = `${fichaLabel} – ${nome}`;
      td.style.verticalAlign = 'middle';
      td.dataset.hydrated = '1';
    });
  }

  /* ============================ Z E B R A  INTELIGENTE ==================== */
  /** * CORREÇÃO: Agrupa visualmente usando a ficha COMPLETA como chave.
   * Assim 11.1 terá uma cor, e 11.2 terá outra (alternada).
   */
  function applyZebraPorGrupo(root = document) {
    const scope = (root && typeof root.querySelectorAll === 'function') ? root : document;
    let corIndex = 0;
    let ultimaFichaPai = null;

    scope.querySelectorAll('tbody tr[data-ficha]').forEach((tr) => {
      const ficha = tr.getAttribute('data-ficha');
      
      // CORREÇÃO: Usa 'ficha' inteira (ex: "11.1") em vez de dividir pelo ponto
      const fichaAtual = ficha;

      if (ultimaFichaPai !== null && fichaAtual !== ultimaFichaPai) {
        corIndex++;
      }
      
      const isPar = (corIndex % 2 === 0);
      tr.classList.remove('grupo-par', 'grupo-impar');
      tr.classList.add(isPar ? 'grupo-par' : 'grupo-impar');

      ultimaFichaPai = fichaAtual;
    });
  }

  /* ============================ T O T A L  G E R A L ======================== */
  function renderTotalRow() {
    const tbody = document.querySelector('.table-lista-fichas tbody');
    if (!tbody) return;

    let totalContabil = 0;
    let totalGestor = 0;
    const gruposSomados = new Set();

    tbody.querySelectorAll('tr').forEach(tr => {
       const ficha = tr.getAttribute('data-ficha');
       const tdContabil = tr.querySelector('td.valor-contabil');
       const valContabil = tdContabil ? parseFloat(tdContabil.getAttribute('data-value') || '0') : 0;
       totalContabil += valContabil;

       const isAgregado = (ficha === '8' || String(ficha).startsWith('11.'));
       
       if (isAgregado) {
          if (!gruposSomados.has(ficha)) {
              const valGestor = parseFloat(tr.getAttribute('data-valor-gestor-agg') || '0');
              totalGestor += valGestor;
              gruposSomados.add(ficha);
          }
       } else {
           const valGestor = parseFloat(tr.getAttribute('data-valor-ficha') || '0');
           totalGestor += valGestor;
       }
    });

    const diff = totalContabil - totalGestor;
    const pct = totalContabil !== 0 ? ((diff / totalContabil) * 100) : 0;

    const tfoot = document.getElementById('tfoot-total');
    if(tfoot) {
        tfoot.innerHTML = '';
        const tr = document.createElement('tr');
        tr.style.backgroundColor = '#0038a8';
        tr.style.color = '#ffffff';
        tr.style.fontWeight = 'bold';

        const tdLabel = document.createElement('td');
        tdLabel.colSpan = 2;
        tdLabel.textContent = 'TOTAL';
        tdLabel.style.textAlign = 'center';
        tr.appendChild(tdLabel);

        const tdCont = document.createElement('td');
        tdCont.textContent = formatMoney(totalContabil);
        tr.appendChild(tdCont);

        const tdGest = document.createElement('td');
        tdGest.textContent = formatMoney(totalGestor);
        tr.appendChild(tdGest);

        const tdDiff = document.createElement('td');
        tdDiff.textContent = formatMoney(diff);
        tr.appendChild(tdDiff);

        const tdPct = document.createElement('td');
        tdPct.textContent = formatPercent(pct);
        if(Math.abs(pct) > 0.5) tdPct.style.color = 'yellow';
        tr.appendChild(tdPct);

        tfoot.appendChild(tr);
    }
  }

  /* ============================ O R D E N A Ç Ã O ================= */
  function moverFichaZeroParaFinal() {
    const tbody = document.querySelector('.table-lista-fichas tbody');
    if(!tbody) return;
    
    const linhasZero = Array.from(tbody.querySelectorAll('tr[data-ficha="0"]'));
    if(linhasZero.length > 0) {
        linhasZero.forEach(tr => tbody.appendChild(tr));
    }
  }

  /* =========================== H I D R A T A Ç Ã O  G E R A L =================== */
  function hydrateAll(root = document) {
    moverFichaZeroParaFinal();
    applyZebraPorGrupo(root);
    hydrateCosifCells(root);
    hydrateValorContabil(root);
    hydrateGestorDiffPct(root);
    hydrateColunaFicha(root);
    renderTotalRow();
  }

  document.addEventListener('DOMContentLoaded', () => {
    hydrateAll();
  });

})();

 /* =========================== B A C E N =================== */
 
document.addEventListener('DOMContentLoaded', function() {

    function formatarMoeda(valor) {
        return valor.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
    }

    function formatarPorcentagem(valor) {
        return valor.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 }) + '%';
    }

    // Transforma texto BR (1.000,50) em Float JS (1000.50) para cálculos
    function parseValor(valorStr) {
        if (!valorStr) return 0.0;
        if (typeof valorStr === 'number') return valorStr;
        
        // Mantém apenas números e vírgula
        // Remove os pontos de milhar para não atrapalhar
        let limpo = valorStr.toString().replace(/\./g, ''); 
        
        // Troca a vírgula decimal por ponto para o JS entender
        limpo = limpo.replace(',', '.');
        
        return parseFloat(limpo) || 0.0;
    }

    function atualizarTotais() {
        let totalFicha = 0.0;
        let totalBacen = 0.0;
        let totalDiff = 0.0;

        // Soma coluna Ficha
        document.querySelectorAll('.val-ficha').forEach(td => {
            totalFicha += parseFloat(td.getAttribute('data-valor')) || 0;
        });

        // Soma coluna Bacen (Input)
        document.querySelectorAll('.input-bacen').forEach(input => {
            totalBacen += parseValor(input.value);
        });

        totalDiff = totalFicha - totalBacen;
        
        // Calcula Porcentagem Total
        let totalPorcent = 0.0;
        if (totalFicha !== 0) {
            totalPorcent = (totalDiff / totalFicha) * 100;
        }

        // Seleciona elementos do rodapé
        const elTotalFicha = document.getElementById('total-ficha');
        const elTotalBacen = document.getElementById('total-bacen');
        const elTotalDiff = document.getElementById('total-diferenca');
        const elTotalPorc = document.getElementById('total-porcentagem');

        // Atualiza Textos
        if(elTotalFicha) elTotalFicha.innerText = formatarMoeda(totalFicha);
        if(elTotalBacen) elTotalBacen.innerText = formatarMoeda(totalBacen);
        if(elTotalDiff) elTotalDiff.innerText = formatarMoeda(totalDiff);
        
        // Lógica da Porcentagem e Cor
        if(elTotalPorc) {
            elTotalPorc.innerText = formatarPorcentagem(totalPorcent);
            if (Math.abs(totalPorcent) > 0.5) {
                elTotalPorc.style.color = "yellow"; // Amarelo destaque no fundo azul
                // Se o fundo fosse claro, amarelo seria ruim de ler, mas no fundo azul #0038a8 fica ótimo.
            } else {
                elTotalPorc.style.color = "#ffffff"; // Branco padrão
            }
        }
    }

    window.calcularLinha = function(input) {
        let row = input.closest('tr');
        
        let valFicha = parseFloat(row.querySelector('.val-ficha').getAttribute('data-valor'));
        let valBacen = parseValor(input.value);
        
        let diferenca = valFicha - valBacen;
        let porcentagem = (valFicha !== 0) ? (diferenca / valFicha) * 100 : 0.0;

        // Atualiza a tela
        row.querySelector('.val-diferenca').innerText = formatarMoeda(diferenca);
        
        let cellPorc = row.querySelector('.val-porcentagem');
        cellPorc.innerText = formatarPorcentagem(porcentagem);

        // Regra de cor > 0.5%
        if (Math.abs(porcentagem) > 0.5) {
            cellPorc.style.color = "red";
            cellPorc.style.fontWeight = "bold";
        } else {
            cellPorc.style.color = "inherit";
        }

        atualizarTotais();
    };

    // --- 3. SALVAR (CORRIGIDO PARA NÃO QUEBRAR O NÚMERO) ---
    window.salvarValoresBacen = function() {
        let listaSalvar = [];
        
        document.querySelectorAll('.input-bacen').forEach(input => {
            // Pega o valor digitado (Ex: "46.466.935,70")
            // A função parseValor já limpa os pontos e arruma a virgula para ponto (Ex: 46466935.70)
            let valorDouble = parseValor(input.value); 
            
            // Convertemos para String formato US simples para enviar ao Java sem erro
            // Ex: "46466935.70"
            let valorParaEnviar = valorDouble.toFixed(2); 

            let item = {
                trimestre: trimestreAtual,
                ano: anoAtual,
                ficha: input.getAttribute('data-ficha-nome'),
                valor: valorParaEnviar // Envia limpo, sem pontos de milhar
            };
            listaSalvar.push(item);
        });

        // Envia
        fetch(contextPath + '/ConsolidadoController', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(listaSalvar)
        })
        .then(response => {
            if (response.ok) {
                alert('Valores salvos com sucesso!');
                // Opcional: Recarregar para garantir formato vindo do banco
                // location.reload(); 
            } else {
                alert('Erro ao salvar.');
            }
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro de conexão.');
        });
    };

    // --- 4. ESTILO ---
    function estilizarTabelaConsolidado() {
        const data = new Date();
        const mesAtual = data.getMonth(); 
        const tabelas = document.querySelectorAll('.table-lista-fichas');
        if (tabelas.length === 0) return;
        if (mesAtual === 9) { 
            tabelas.forEach(tabela => {
                tabela.classList.add('table-outubro-consolidado');
                const cabecalho = tabela.querySelectorAll('th');
                cabecalho.forEach(th => { th.style.backgroundColor = '#fc8b9f'; });
            });
        }
    }

    estilizarTabelaConsolidado();
    
    // Inicializa cálculos
    document.querySelectorAll('.input-bacen').forEach(input => {
        calcularLinha(input);
    });
});